package cn.hollis.nft.turbo.collection.domain.service.impl;

import cn.hollis.nft.turbo.api.collection.constant.GoodsSaleBizType;
import cn.hollis.nft.turbo.api.collection.constant.HeldCollectionState;
import cn.hollis.nft.turbo.api.collection.model.HeldCollectionDTO;
import cn.hollis.nft.turbo.api.collection.request.HeldCollectionPageQueryRequest;
import cn.hollis.nft.turbo.base.response.PageResponse;
import cn.hollis.nft.turbo.cache.constant.CacheConstant;
import cn.hollis.nft.turbo.collection.domain.constant.HeldCollectionEventType;
import cn.hollis.nft.turbo.collection.domain.entity.HeldCollection;
import cn.hollis.nft.turbo.collection.domain.entity.HeldCollectionStream;
import cn.hollis.nft.turbo.collection.domain.entity.convertor.HeldCollectionConvertor;
import cn.hollis.nft.turbo.collection.domain.request.HeldCollectionActiveRequest;
import cn.hollis.nft.turbo.collection.domain.request.HeldCollectionCreateRequest;
import cn.hollis.nft.turbo.collection.domain.request.HeldCollectionDestroyRequest;
import cn.hollis.nft.turbo.collection.domain.request.HeldCollectionTransferRequest;
import cn.hollis.nft.turbo.collection.exception.CollectionErrorCode;
import cn.hollis.nft.turbo.collection.exception.CollectionException;
import cn.hollis.nft.turbo.collection.infrastructure.mapper.HeldCollectionMapper;
import cn.hollis.turbo.stream.producer.StreamProducer;
import cn.hutool.core.lang.Assert;
import com.alibaba.fastjson.JSON;
import com.alicp.jetcache.anno.CacheRefresh;
import com.alicp.jetcache.anno.CacheType;
import com.alicp.jetcache.anno.Cached;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static cn.hollis.nft.turbo.collection.exception.CollectionErrorCode.*;


//持有的藏品服务
@Service
public class HeldCollectionService extends ServiceImpl<HeldCollectionMapper, HeldCollection> {
    @Autowired
    private HeldCollectionMapper heldCollectionMapper;

    @Autowired
    private StreamProducer streamProducer;

    @Autowired
    private HeldCollectionStreamService heldCollectionStreamService;

    @Autowired
    private TransactionTemplate transactionTemplate;

    @Autowired
    private RedissonClient redissonClient;

    private static final String HELD_COLLECTION_BIND_BOX_PREFIX = "HC:SALES:";

    @Transactional(rollbackFor = Exception.class)
    public List<HeldCollection> batchCreate(List<HeldCollectionCreateRequest> heldCollectionCreateRequests) {

        List<HeldCollection> heldCollections = new ArrayList<>();
        List<HeldCollectionStream> heldCollectionStreams = new ArrayList<>();
        for (HeldCollectionCreateRequest request : heldCollectionCreateRequests) {
            HeldCollection heldCollection = new HeldCollection();
            Long serialNo = redissonClient.getAtomicLong(HELD_COLLECTION_BIND_BOX_PREFIX + request.getGoodsType() + CacheConstant.CACHE_KEY_SEPARATOR + request.getSerialNoBaseId()).incrementAndGet();
            heldCollection.init(request, serialNo.toString());
            heldCollections.add(heldCollection);
        }

        //this调用会使saveBatch中的事务失效，需要在本方法外增加事务
        boolean result = this.saveBatch(heldCollections);
        Assert.isTrue(result, () -> new CollectionException(HELD_COLLECTION_SAVE_FAILED));

        for (HeldCollection heldCollection : heldCollections) {
            HeldCollectionStream heldCollectionStream = new HeldCollectionStream().generateForCreate(heldCollection.getId(), heldCollection.getId().toString());
            heldCollectionStreams.add(heldCollectionStream);
        }

        result = heldCollectionStreamService.saveBatch(heldCollectionStreams);
        Assert.isTrue(result, () -> new CollectionException(HELD_COLLECTION_STREAM_SAVE_FAILED));
        return heldCollections;
    }

    @Transactional(rollbackFor = Exception.class)
    public HeldCollection create(HeldCollectionCreateRequest request) {
        HeldCollection existHeldCollection = queryByCollectionIdAndBizNo(request.getGoodsId(), request.getBizNo());
        if (existHeldCollection != null) {
            return existHeldCollection;
        }

        //HC:SALES:COLLECTION:1234 or HC:SALES:BIND_BOX:1234
        HeldCollection heldCollection = new HeldCollection();
        Long serialNo = redissonClient.getAtomicLong(HELD_COLLECTION_BIND_BOX_PREFIX + request.getGoodsType() + CacheConstant.CACHE_KEY_SEPARATOR + request.getSerialNoBaseId()).incrementAndGet();

        try {
            heldCollection.init(request, serialNo.toString());
            var saveResult = this.save(heldCollection);
            if (!saveResult) {
                throw new CollectionException(HELD_COLLECTION_SAVE_FAILED);
            }

            HeldCollectionStream heldCollectionStream = new HeldCollectionStream().generateForCreate(heldCollection.getId(), request.getIdentifier());
            saveResult = heldCollectionStreamService.save(heldCollectionStream);
            Assert.isTrue(saveResult, () -> new CollectionException(HELD_COLLECTION_STREAM_SAVE_FAILED));

            return heldCollection;
        } catch (Throwable throwable) {
            //如果抛了异常，并且数据库未更新成功过，则回滚销量
            heldCollection = queryByCollectionIdAndBizNo(request.getGoodsId(), request.getBizNo());
            if (heldCollection == null) {
                redissonClient.getAtomicLong(HELD_COLLECTION_BIND_BOX_PREFIX + request.getGoodsType() + CacheConstant.CACHE_KEY_SEPARATOR + request.getSerialNoBaseId()).decrementAndGet();
                return null;
            }
            return heldCollection;
        }
    }

    public Boolean active(HeldCollectionActiveRequest request) {
        HeldCollection heldCollection = getById(request.getHeldCollectionId());
        if (null == heldCollection) {
            throw new CollectionException(HELD_COLLECTION_QUERY_FAIL);
        }

        if (heldCollection.getState().equals(HeldCollectionState.ACTIVED)) {
            return true;
        }

        heldCollection.actived(request.getNftId(), request.getTxHash());
        HeldCollectionStream heldCollectionStream = new HeldCollectionStream().generateForActive(heldCollection.getId(), request.getIdentifier());

        //用编程式事务代替声明式事务，避免后面MQ发送超时，导致事务回滚
        transactionTemplate.executeWithoutResult(status -> {
            boolean result = updateById(heldCollection);
            Assert.isTrue(result, () -> new CollectionException(HELD_COLLECTION_SAVE_FAILED));
            boolean saveResult = heldCollectionStreamService.save(heldCollectionStream);
            Assert.isTrue(saveResult, () -> new CollectionException(HELD_COLLECTION_STREAM_SAVE_FAILED));
        });

        if (heldCollection.getBizType() != GoodsSaleBizType.AIR_DROP) {
            sendMsg(heldCollection, request.getEventType());
        }
        return true;
    }

    @Transactional(rollbackFor = Exception.class)
    public HeldCollection transfer(HeldCollectionTransferRequest request) {
        HeldCollection oldHeldCollection = this.getById(request.getHeldCollectionId());
        preCheckForTransfer(request, oldHeldCollection);

        //原持有藏品失效
        var inActiveRes = this.updateById(oldHeldCollection.inActived());
        Assert.isTrue(inActiveRes, () -> new CollectionException(CollectionErrorCode.HELD_COLLECTION_SAVE_FAILED));
        HeldCollectionStream transferOutStream = new HeldCollectionStream().generateForTransferOut(oldHeldCollection.getId(), request.getIdentifier(), oldHeldCollection.getUserId());
        var saveResult = heldCollectionStreamService.save(transferOutStream);
        Assert.isTrue(saveResult, () -> new CollectionException(HELD_COLLECTION_STREAM_SAVE_FAILED));

        //新持有藏品生成
        HeldCollection newHeldCollection = new HeldCollection().transfer(oldHeldCollection, request.getRecipientUserId());
        var newHeldSaveResult = this.save(newHeldCollection);
        Assert.isTrue(newHeldSaveResult, () -> new CollectionException(CollectionErrorCode.HELD_COLLECTION_SAVE_FAILED));

        HeldCollectionStream transferInStream = new HeldCollectionStream().generateForTransferIn(newHeldCollection.getId(), request.getIdentifier(), oldHeldCollection.getUserId());
        saveResult = heldCollectionStreamService.save(transferInStream);
        Assert.isTrue(saveResult, () -> new CollectionException(HELD_COLLECTION_STREAM_SAVE_FAILED));

        return newHeldCollection;
    }

    private static void preCheckForTransfer(HeldCollectionTransferRequest request, HeldCollection oldHeldCollection) {
        if (oldHeldCollection == null) {
            throw new CollectionException(CollectionErrorCode.HELD_COLLECTION_QUERY_FAIL);
        }

        if (oldHeldCollection.getState() != HeldCollectionState.ACTIVED) {
            throw new CollectionException(CollectionErrorCode.HELD_COLLECTION_STATE_CHECK_ERROR);
        }

        if (!oldHeldCollection.getUserId().equals(request.getOperatorId())) {
            throw new CollectionException(CollectionErrorCode.HELD_COLLECTION_OWNER_CHECK_ERROR);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public HeldCollection destroy(HeldCollectionDestroyRequest request) {
        HeldCollection heldCollection = this.getById(request.getHeldCollectionId());
        preCheckForDestroy(request, heldCollection);

        if (heldCollection.getState() == HeldCollectionState.DESTROYING || heldCollection.getState() == HeldCollectionState.DESTROYED) {
            return heldCollection;
        }

        heldCollection.destroying();
        HeldCollectionStream heldCollectionStream = new HeldCollectionStream().generateForDestroy(heldCollection.getId(), request.getIdentifier(), request.getOperatorId());

        boolean result = this.updateById(heldCollection);
        Assert.isTrue(result, () -> new CollectionException(HELD_COLLECTION_SAVE_FAILED));
        boolean saveResult = heldCollectionStreamService.save(heldCollectionStream);
        Assert.isTrue(saveResult, () -> new CollectionException(HELD_COLLECTION_STREAM_SAVE_FAILED));

        return heldCollection;
    }

    private static void preCheckForDestroy(HeldCollectionDestroyRequest request, HeldCollection oldHeldCollection) {
        if (oldHeldCollection == null) {
            throw new CollectionException(CollectionErrorCode.HELD_COLLECTION_QUERY_FAIL);
        }

        if (!oldHeldCollection.getUserId().equals(request.getOperatorId())) {
            throw new CollectionException(CollectionErrorCode.HELD_COLLECTION_OWNER_CHECK_ERROR);
        }
    }

    @Cached(name = ":held_collection:cache:id:", expire = 60, localExpire = 10, timeUnit = TimeUnit.MINUTES, cacheType = CacheType.BOTH, key = "#heldCollectionId", cacheNullValue = true)
    @CacheRefresh(refresh = 50, timeUnit = TimeUnit.MINUTES)
    public HeldCollection queryById(Long heldCollectionId) {
        return getById(heldCollectionId);
    }

    public HeldCollection queryByCollectionIdAndBizNo(Long collectionId, String bizNo) {
        QueryWrapper<HeldCollection> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("collection_id", collectionId);
        queryWrapper.eq("biz_no", bizNo);
        List<HeldCollection> retList = list(queryWrapper);
        if (CollectionUtils.isEmpty(retList)) {
            return null;
        }
        return retList.get(0);
    }

    public HeldCollection queryByCollectionIdAndSerialNo(Long collectionId, String serialNo) {
        QueryWrapper<HeldCollection> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("collection_id", collectionId);
        queryWrapper.eq("serial_no", serialNo);
        List<HeldCollection> retList = list(queryWrapper);
        if (CollectionUtils.isEmpty(retList)) {
            return null;
        }
        return retList.get(0);
    }

    public long queryHeldCollectionCount(String userId) {
        QueryWrapper wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId);
        return this.count(wrapper);
    }

    public PageResponse<HeldCollection> pageQueryByState(HeldCollectionPageQueryRequest request) {
        Page<HeldCollection> page = new Page<>(request.getCurrentPage(), request.getPageSize());
        QueryWrapper<HeldCollection> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", request.getUserId());
        wrapper.like("name", request.getKeyword());

        if (request.getState() != null) {
            wrapper.eq("state", request.getState());
        }
        wrapper.orderBy(true, false, "gmt_create");

        Page<HeldCollection> collectionPage = this.page(page, wrapper);
        return PageResponse.of(collectionPage.getRecords(), (int) collectionPage.getTotal(), request.getPageSize(), request.getCurrentPage());
    }


    private boolean sendMsg(HeldCollection heldCollection, HeldCollectionEventType eventType) {
        HeldCollectionDTO heldCollectionDTO = HeldCollectionConvertor.INSTANCE.mapToDto(heldCollection);
        //消息监听：HeldCollectionMsgListener
        return streamProducer.send("heldCollection-out-0", eventType.name(), JSON.toJSONString(heldCollectionDTO));
    }

    public List<HeldCollection> pageQueryForChainMint(int pageSize, Long minId) {
        QueryWrapper<HeldCollection> wrapper = new QueryWrapper<>();
        wrapper.in("state", HeldCollectionState.INIT);
        wrapper.isNull("nft_id");
        wrapper.isNull("tx_hash");
        wrapper.isNull("sync_chain_time");
        wrapper.ge("id", minId);
        wrapper.last("limit " + pageSize);
        wrapper.orderBy(true, true, "gmt_create");

        return this.list(wrapper);
    }

    public Long queryMinIdForMint() {
        return heldCollectionMapper.queryMinIdForMint();
    }
}
