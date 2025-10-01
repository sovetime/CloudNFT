package cn.hollis.nft.turbo.goods.service;

import cn.hollis.nft.turbo.cache.constant.CacheConstant;
import cn.hollis.nft.turbo.goods.entity.HotGoods;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.redisson.api.RSet;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;


//热点商品服务
@Service
public class HotGoodsService {

    @Autowired
    private RedissonClient redissonClient;

    //热门商品本地缓存
    private Cache<String, Boolean> hotGoodsLocalCache;

    private static final String HOT_GOODS_SET_KEY = "goods:hot:set";

    private static final String HOT_GOODS_KEY = "goods:hot:";

    public static final Integer HOT_GOODS_BOOK_COUNT = 2000;

    @PostConstruct
    public void init() {
        hotGoodsLocalCache = Caffeine.newBuilder()
                .expireAfterWrite(24, TimeUnit.HOURS)
                .maximumSize(3000)
                .build();
    }

    //添加热点商品
    public void addHotGoods(String goodsId, String goodsType) {
        if (!isHotGoods(goodsId, goodsType)) {
            //goods:hot:商品类型:商品id
            String hotGoodsKey = HOT_GOODS_KEY + goodsType + CacheConstant.CACHE_KEY_SEPARATOR + goodsId;
            hotGoodsLocalCache.put(hotGoodsKey, true);
            //存储到redis中，使用Set 进行存储
            redissonClient.getSet(HOT_GOODS_SET_KEY).add(hotGoodsKey);
        }
    }

    //判断是否是热点商品
    public Boolean isHotGoods(String goodsId, String goodsType) {
        String hotGoodsKey = HOT_GOODS_KEY + goodsType + CacheConstant.CACHE_KEY_SEPARATOR + goodsId;
        Boolean isHot = hotGoodsLocalCache.getIfPresent(hotGoodsKey);

        if (isHot == null) {
            RSet<String> hotGoodsSet = redissonClient.getSet(HOT_GOODS_SET_KEY);
            isHot = hotGoodsSet.contains(hotGoodsKey);
            if (isHot) {
                hotGoodsLocalCache.put(hotGoodsKey, true);
            }
        }
        return isHot;
    }

    //获取热门商品id列表
    public List<String> getHotGoods(String goodsType) {
        List<String> hotGoods = new ArrayList<>();
        Set<String> hotKeys = redissonClient.getSet(HOT_GOODS_SET_KEY);

        for (String hotKey : hotKeys) {
            if (!hotKey.contains(goodsType)) {
                hotGoods.add(hotKey.substring(hotKey.lastIndexOf(CacheConstant.CACHE_KEY_SEPARATOR)) + 1);
            }
        }
        return hotGoods;
    }

    //获取所有热点商品
    public Set<HotGoods> getAllHotGoods() {
        RSet<String> hotGoodsSet = redissonClient.getSet(HOT_GOODS_SET_KEY);
        Set<String> hotKeys = hotGoodsSet.readAll();

        Set<HotGoods> hotGoods = new HashSet<>();
        for (String hotKey : hotKeys) {
            String[] hotKeyArr = hotKey.split(CacheConstant.CACHE_KEY_SEPARATOR);
            HotGoods hotGood = new HotGoods();
            hotGood.setGoodsId(hotKeyArr[3]);
            hotGood.setGoodsType(hotKeyArr[2]);
            hotGoods.add(hotGood);
        }
        return hotGoods;
    }
}
