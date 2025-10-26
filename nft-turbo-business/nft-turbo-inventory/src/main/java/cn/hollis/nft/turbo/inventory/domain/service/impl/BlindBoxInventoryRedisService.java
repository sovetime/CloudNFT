package cn.hollis.nft.turbo.inventory.domain.service.impl;

import cn.hollis.nft.turbo.api.inventory.request.InventoryRequest;
import org.springframework.stereotype.Service;


@Service
public class BlindBoxInventoryRedisService extends AbstractInventoryRedisService {

    private static final String INVENTORY_KEY = "blb:inventory:";

    private static final String INVENTORY_STREAM_KEY = "blb:inventory:stream:";

    //获取库存缓存的key
    @Override
    protected String getCacheKey(InventoryRequest request) {
        return INVENTORY_KEY + request.getGoodsId();
    }

    //获取库存流水缓存的key
    @Override
    protected String getCacheStreamKey(InventoryRequest request) {
        return INVENTORY_STREAM_KEY + request.getGoodsId();
    }
}
