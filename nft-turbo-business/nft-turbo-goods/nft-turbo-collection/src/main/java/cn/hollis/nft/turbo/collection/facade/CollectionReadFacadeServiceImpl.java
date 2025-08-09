package cn.hollis.nft.turbo.collection.facade;

import cn.hollis.nft.turbo.api.collection.model.AirDropStreamVO;
import cn.hollis.nft.turbo.api.collection.model.CollectionVO;
import cn.hollis.nft.turbo.api.collection.model.HeldCollectionVO;
import cn.hollis.nft.turbo.api.collection.request.AirDropPageQueryRequest;
import cn.hollis.nft.turbo.api.collection.request.CollectionPageQueryRequest;
import cn.hollis.nft.turbo.api.collection.request.HeldCollectionPageQueryRequest;
import cn.hollis.nft.turbo.api.collection.service.CollectionReadFacadeService;
import cn.hollis.nft.turbo.api.goods.constant.GoodsType;
import cn.hollis.nft.turbo.api.goods.service.GoodsFacadeService;
import cn.hollis.nft.turbo.api.inventory.request.InventoryRequest;
import cn.hollis.nft.turbo.api.inventory.service.InventoryFacadeService;
import cn.hollis.nft.turbo.base.response.PageResponse;
import cn.hollis.nft.turbo.base.response.SingleResponse;
import cn.hollis.nft.turbo.collection.domain.entity.Collection;
import cn.hollis.nft.turbo.collection.domain.entity.CollectionAirdropStream;
import cn.hollis.nft.turbo.collection.domain.entity.HeldCollection;
import cn.hollis.nft.turbo.collection.domain.entity.convertor.CollectionAirdropStreamConvertor;
import cn.hollis.nft.turbo.collection.domain.entity.convertor.CollectionConvertor;
import cn.hollis.nft.turbo.collection.domain.entity.convertor.HeldCollectionConvertor;
import cn.hollis.nft.turbo.collection.domain.service.CollectionService;
import cn.hollis.nft.turbo.collection.domain.service.impl.HeldCollectionService;
import cn.hollis.nft.turbo.collection.infrastructure.mapper.CollectionAirdropStreamMapper;
import cn.hollis.nft.turbo.rpc.facade.Facade;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.dubbo.config.annotation.DubboService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 藏品服务
 *
 * @author hollis
 */
@DubboService(version = "1.0.0")
public class CollectionReadFacadeServiceImpl implements CollectionReadFacadeService {

    private static final Logger logger = LoggerFactory.getLogger(CollectionReadFacadeServiceImpl.class);

    @Autowired
    private CollectionService collectionService;

    @Autowired
    private HeldCollectionService heldCollectionService;

    @Autowired
    private InventoryFacadeService inventoryFacadeService;

    @Autowired
    private GoodsFacadeService goodsFacadeService;

    @Autowired
    private CollectionAirdropStreamMapper collectionAirdropStreamMapper;

    @Override
    public SingleResponse<CollectionVO> queryById(Long collectionId) {
        Collection collection = collectionService.queryById(collectionId);

        InventoryRequest request = new InventoryRequest();
        request.setGoodsId(collectionId.toString());
        request.setGoodsType(GoodsType.COLLECTION);
        SingleResponse<Integer> response = inventoryFacadeService.queryInventory(request);

        //没查到的情况下，默认用数据库里面的库存做兜底
        Integer inventory = collection.getSaleableInventory().intValue();
        if (response.getSuccess()) {
            inventory = response.getData();
        }

        CollectionVO collectionVO = CollectionConvertor.INSTANCE.mapToVo(collection);
        collectionVO.setInventory(inventory.longValue());
        collectionVO.setState(collection.getState(), collection.getSaleTime(), inventory.longValue());
     
        return SingleResponse.of(collectionVO);
    }

    @Override
    public PageResponse<CollectionVO> pageQuery(CollectionPageQueryRequest request) {
        PageResponse<Collection> colletionPage = collectionService.pageQueryByState(request.getKeyword(), request.getState(), request.getCurrentPage(), request.getPageSize());
        return PageResponse.of(CollectionConvertor.INSTANCE.mapToVo(colletionPage.getDatas()), colletionPage.getTotal(), colletionPage.getPageSize(), request.getCurrentPage());
    }

    @Override
    public PageResponse<HeldCollectionVO> pageQueryHeldCollection(HeldCollectionPageQueryRequest request) {
        PageResponse<HeldCollection> colletionPage = heldCollectionService.pageQueryByState(request);
        return PageResponse.of(HeldCollectionConvertor.INSTANCE.mapToVo(colletionPage.getDatas()), colletionPage.getTotal(), request.getPageSize(), request.getCurrentPage());
    }

    @Override
    public PageResponse<AirDropStreamVO> pageQueryAirDropList(AirDropPageQueryRequest request) {
        Page<CollectionAirdropStream> page = new Page<>(request.getCurrentPage(), request.getPageSize());
        QueryWrapper<CollectionAirdropStream> wrapper = new QueryWrapper<>();
        if (request.getCollectionId() != null) {
            wrapper.eq("collection_id", request.getCollectionId());
        }

        if (request.getUserId() != null) {
            wrapper.eq("recipient_user_id", request.getUserId());
        }
        wrapper.orderByDesc("gmt_create");
        Page<CollectionAirdropStream> collectionAirdropStreamPage = collectionAirdropStreamMapper.selectPage(page, wrapper);
        return PageResponse.of(CollectionAirdropStreamConvertor.INSTANCE.mapToVo(collectionAirdropStreamPage.getRecords()), (int) collectionAirdropStreamPage.getTotal(), request.getPageSize(), request.getCurrentPage());
    }

    @Override
    public SingleResponse<Long> queryHeldCollectionCount(String userId) {
        return SingleResponse.of(heldCollectionService.queryHeldCollectionCount(userId));
    }

    @Override
    public SingleResponse<HeldCollectionVO> queryHeldCollectionById(Long heldCollectionId) {
        HeldCollection transferCollection = heldCollectionService.queryById(heldCollectionId);
        return SingleResponse.of(HeldCollectionConvertor.INSTANCE.mapToVo(transferCollection));
    }
}
