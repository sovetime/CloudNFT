package cn.hollis.nft.turbo.inventory.domain.service.impl;

import cn.hollis.nft.turbo.api.inventory.request.InventoryRequest;
import org.springframework.stereotype.Service;


@Service
public class CollectionInventoryRedisService extends AbstractInventoryRedisService {

    private static final String INVENTORY_KEY = "clc:inventory:";

    private static final String INVENTORY_STREAM_KEY = "clc:inventory:stream:";

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
