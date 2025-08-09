package cn.hollis.nft.turbo.goods.service;

import cn.hollis.nft.turbo.api.goods.constant.GoodsType;
import cn.hollis.nft.turbo.api.goods.model.GoodsBookVO;
import cn.hollis.nft.turbo.api.goods.request.GoodsBookRequest;
import cn.hollis.nft.turbo.api.goods.response.GoodsBookResponse;
import cn.hollis.nft.turbo.base.exception.BizException;
import cn.hollis.nft.turbo.base.exception.RepoErrorCode;
import cn.hollis.nft.turbo.cache.constant.CacheConstant;
import cn.hollis.nft.turbo.goods.entity.GoodsBook;
import cn.hollis.nft.turbo.goods.entity.convertor.GoodsBookConvertor;
import cn.hollis.nft.turbo.goods.infrastructure.mapper.GoodsBookMapper;
import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RBitSet;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static cn.hollis.nft.turbo.goods.service.HotGoodsService.HOT_GOODS_BOOK_COUNT;

/**
 * 预约服务
 *
 * @author Hollis
 */
@Service
@Slf4j
public class GoodsBookService extends ServiceImpl<GoodsBookMapper, GoodsBook> {

    @Autowired
    private GoodsBookMapper goodsBookMapper;

    @Autowired
    private RedissonClient redissonClient;

    @Autowired
    private HotGoodsService hotGoodsService;

    private static final String BOOK_KEY = "goods:book:";

    /**
     * 商品预约
     * 先更新缓存，再更新数据库。优先保证缓存，如果出现不一致，以缓存为主
     *
     * @param request
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public GoodsBookResponse book(GoodsBookRequest request) {
        // 因为用户id都是不重复的，并且可以转换成integer，所以这里可以使用BitSet来存储预约信息，减少存储量
        RBitSet bookedUsers = redissonClient.getBitSet(BOOK_KEY + request.getGoodsType() + CacheConstant.CACHE_KEY_SEPARATOR + request.getGoodsId());
        // 不报错则成功
        bookedUsers.set(Integer.parseInt(request.getBuyerId()));

        GoodsBook existBook = goodsBookMapper.selectByGoodsIdAndBuyerId(request.getGoodsId(), request.getGoodsType().name(), request.getBuyerId());
        if (existBook != null) {
            return new GoodsBookResponse.GoodsBookResponseBuilder().bookId(existBook.getId()).buildSuccess();
        }
        GoodsBook goodsBook = GoodsBook.createBook(request);
        boolean result = save(goodsBook);
        Assert.isTrue(result, () -> new BizException(RepoErrorCode.INSERT_FAILED));

        //异步为热门商品添加缓存，失败不影响业务
        Thread.ofVirtual().start(() -> {
            // 检查是否为热门商品
            long bookedCount = bookedUsers.cardinality();
            if (bookedCount > HOT_GOODS_BOOK_COUNT) {
                hotGoodsService.addHotGoods(request.getGoodsId(), request.getGoodsType().name());
            }
        });

        return new GoodsBookResponse.GoodsBookResponseBuilder().bookId(goodsBook.getId()).buildSuccess();
    }

    public boolean isBooked(String goodsId, GoodsType goodsType, String buyerId) {
        RBitSet bookedUsers = redissonClient.getBitSet(BOOK_KEY + goodsType + CacheConstant.CACHE_KEY_SEPARATOR + goodsId);
        return bookedUsers.get(Integer.parseInt(buyerId));
    }

    public GoodsBookVO selectByGoodsIdAndBuyerId(String goodsId, String goodsType, String buyerId) {
        GoodsBook goodsBook = goodsBookMapper.selectByGoodsIdAndBuyerId(goodsId, goodsType, buyerId);
        if (null == goodsBook) {
            return null;
        }
        return GoodsBookConvertor.INSTANCE.mapToVo(goodsBook);
    }


}
