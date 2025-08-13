package cn.hollis.nft.turbo.inventory.facade.service;

import cn.hollis.nft.turbo.api.goods.constant.GoodsType;
import cn.hollis.nft.turbo.api.inventory.request.InventoryRequest;
import cn.hollis.nft.turbo.api.inventory.service.InventoryFacadeService;
import cn.hollis.nft.turbo.base.response.MultiResponse;
import cn.hollis.nft.turbo.base.response.SingleResponse;
import cn.hollis.nft.turbo.inventory.domain.response.InventoryResponse;
import cn.hollis.nft.turbo.inventory.domain.service.impl.BlindBoxInventoryRedisService;
import cn.hollis.nft.turbo.inventory.domain.service.impl.CollectionInventoryRedisService;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import static cn.hollis.nft.turbo.api.common.constant.CommonConstant.SEPARATOR;
import static cn.hollis.nft.turbo.inventory.domain.service.impl.AbstractInventoryRedisService.ERROR_CODE_INVENTORY_IS_ZERO;
import static cn.hollis.nft.turbo.inventory.domain.service.impl.AbstractInventoryRedisService.ERROR_CODE_INVENTORY_NOT_ENOUGH;


//库存门面服务
@DubboService(version = "1.0.0")
@Slf4j
public class InventoryFacadeServiceImpl implements InventoryFacadeService {

    private static final String ERROR_CODE_UNSUPPORTED_GOODS_TYPE = "UNSUPPORTED_GOODS_TYPE";

    @Autowired
    private CollectionInventoryRedisService collectionInventoryRedisService;

    @Autowired
    private BlindBoxInventoryRedisService blindBoxInventoryRedisService;

    //商品缓存
    private Cache<String, Boolean> soldOutGoodsLocalCache;

    //初始化本地缓存
    @PostConstruct
    public void init() {
        soldOutGoodsLocalCache = Caffeine.newBuilder()
                .expireAfterWrite(1, TimeUnit.MINUTES)//写入缓存后1分钟过期
                .maximumSize(3000) //最多缓存数据量
                .build();
    }


    //库存初始化
    @Override
    public SingleResponse<Boolean> init(InventoryRequest inventoryRequest) {
        //获取商品类型
        GoodsType goodsType = inventoryRequest.getGoodsType();

        InventoryResponse inventoryResponse = switch (goodsType) {
            //藏品
            case COLLECTION -> collectionInventoryRedisService.init(inventoryRequest);

            //盲盒
            case BLIND_BOX -> blindBoxInventoryRedisService.init(inventoryRequest);

            default -> throw new UnsupportedOperationException(ERROR_CODE_UNSUPPORTED_GOODS_TYPE);
        };

        if (inventoryResponse.getSuccess()) {
            return SingleResponse.of(true);
        }

        return SingleResponse.fail(inventoryResponse.getResponseCode(), inventoryResponse.getResponseMessage());
    }

    //库存扣减
    @Override
    public SingleResponse<Boolean> decrease(InventoryRequest inventoryRequest) {
        //获取商品类型
        GoodsType goodsType = inventoryRequest.getGoodsType();

        //优化方案，引入本地缓存进行过滤
        //根据 商品类型_商品ID -> goodstype_goodsId 从本地缓存中获取库存
        if (soldOutGoodsLocalCache.getIfPresent(goodsType + SEPARATOR + inventoryRequest.getGoodsId()) != null) {
            return SingleResponse.fail(ERROR_CODE_INVENTORY_NOT_ENOUGH, "库存不足");
        }

        //返回响应信息
        InventoryResponse inventoryResponse = switch (goodsType) {
            //藏品
            case COLLECTION -> collectionInventoryRedisService.decrease(inventoryRequest);

            //盲盒
            case BLIND_BOX -> blindBoxInventoryRedisService.decrease(inventoryRequest);

            default -> throw new UnsupportedOperationException(ERROR_CODE_UNSUPPORTED_GOODS_TYPE);
        };

        //1、如果库存为0，则在本地缓存记录，用于对售罄商品快速决策
        //2、当前库存已经是0了，本次扣减失败的情况
        if (isSoldOut(inventoryResponse)) {
            soldOutGoodsLocalCache.put(goodsType + SEPARATOR + inventoryRequest.getGoodsId(), true);
        }

        if (!inventoryResponse.getSuccess()) {
            return SingleResponse.fail(inventoryResponse.getResponseCode(), inventoryResponse.getResponseMessage());
        }

        return SingleResponse.of(true);
    }

    // 判断是否售罄
    private static boolean isSoldOut(InventoryResponse inventoryResponse) {
        if(inventoryResponse.getSuccess() && inventoryResponse.getInventory() == 0){
            //这部分代码没有实际功能作用，仅用于日志埋点，方便压测时判断延时，详见压测相关视频
            log.warn("debug:soldOut ...");
        }
        return inventoryResponse.getSuccess() && inventoryResponse.getInventory() == 0
                || !inventoryResponse.getSuccess() && inventoryResponse.getResponseCode().equals(ERROR_CODE_INVENTORY_IS_ZERO);
    }

    //库存增加
    @Override
    public SingleResponse<Boolean> increase(InventoryRequest inventoryRequest) {
        GoodsType goodsType = inventoryRequest.getGoodsType();
        InventoryResponse inventoryResponse = switch (goodsType) {
            case COLLECTION -> collectionInventoryRedisService.increase(inventoryRequest);

            case BLIND_BOX -> blindBoxInventoryRedisService.increase(inventoryRequest);

            default -> throw new UnsupportedOperationException(ERROR_CODE_UNSUPPORTED_GOODS_TYPE);
        };

        if (inventoryResponse.getSuccess()) {

            //如果库存大于0，则清除本地缓存中的商品售罄标记
            //但是因为是本地缓存，所以无法保证一致性，极端情况下，会存在一分钟的数据不一致的延迟。但是在高并发秒杀场景下，一般是不允许修改库存，所以这种不一致业务上可接受
            if (inventoryResponse.getInventory() > 0) {
                soldOutGoodsLocalCache.invalidate(goodsType + SEPARATOR + inventoryRequest.getGoodsId());
            }

            return SingleResponse.of(true);
        }

        return SingleResponse.fail(inventoryResponse.getResponseCode(), inventoryResponse.getResponseMessage());
    }

    @Override
    public SingleResponse<Void> invalid(InventoryRequest inventoryRequest) {
        GoodsType goodsType = inventoryRequest.getGoodsType();
        switch (goodsType) {
            case COLLECTION -> collectionInventoryRedisService.invalid(inventoryRequest);

            case BLIND_BOX -> blindBoxInventoryRedisService.invalid(inventoryRequest);

            default -> throw new UnsupportedOperationException(ERROR_CODE_UNSUPPORTED_GOODS_TYPE);
        }

        soldOutGoodsLocalCache.invalidate(goodsType + SEPARATOR + inventoryRequest.getGoodsId());

        return SingleResponse.of(null);
    }

    @Override
    public SingleResponse<String> getInventoryDecreaseLog(InventoryRequest inventoryRequest) {
        GoodsType goodsType = inventoryRequest.getGoodsType();
        String inventoryResponse = switch (goodsType) {
            //藏品
            case COLLECTION -> collectionInventoryRedisService.getInventoryDecreaseLog(inventoryRequest);

            //盲盒
            case BLIND_BOX -> blindBoxInventoryRedisService.getInventoryDecreaseLog(inventoryRequest);

            default -> throw new UnsupportedOperationException(ERROR_CODE_UNSUPPORTED_GOODS_TYPE);
        };

        return SingleResponse.of(inventoryResponse);
    }

    @Override
    public MultiResponse<String> getInventoryDecreaseLogs(InventoryRequest inventoryRequest) {
        GoodsType goodsType = inventoryRequest.getGoodsType();
        List<String> inventoryResponse = switch (goodsType) {
            case COLLECTION -> collectionInventoryRedisService.getInventoryDecreaseLogs(inventoryRequest);

            case BLIND_BOX -> blindBoxInventoryRedisService.getInventoryDecreaseLogs(inventoryRequest);

            default -> throw new UnsupportedOperationException(ERROR_CODE_UNSUPPORTED_GOODS_TYPE);
        };

        return MultiResponse.of(Objects.requireNonNullElse(inventoryResponse, Collections.emptyList()));
    }


    //移除流水
    @Override
    public SingleResponse<Long> removeInventoryDecreaseLog(InventoryRequest inventoryRequest) {
        GoodsType goodsType = inventoryRequest.getGoodsType();
        Long inventoryResponse = switch (goodsType) {
            case COLLECTION -> collectionInventoryRedisService.removeInventoryDecreaseLog(inventoryRequest);

            case BLIND_BOX -> blindBoxInventoryRedisService.removeInventoryDecreaseLog(inventoryRequest);

            default -> throw new UnsupportedOperationException(ERROR_CODE_UNSUPPORTED_GOODS_TYPE);
        };

        return SingleResponse.of(inventoryResponse);
    }

    @Override
    public SingleResponse<Integer> queryInventory(InventoryRequest inventoryRequest) {

        GoodsType goodsType = inventoryRequest.getGoodsType();

        if (soldOutGoodsLocalCache.getIfPresent(goodsType + SEPARATOR + inventoryRequest.getGoodsId()) != null) {
            return SingleResponse.of(0);
        }

        Integer inventory = switch (goodsType) {
            case COLLECTION -> collectionInventoryRedisService.getInventory(inventoryRequest);
            case BLIND_BOX -> blindBoxInventoryRedisService.getInventory(inventoryRequest);
            default -> throw new UnsupportedOperationException(ERROR_CODE_UNSUPPORTED_GOODS_TYPE);
        };

        return SingleResponse.of(inventory);
    }
}
