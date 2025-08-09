package cn.hollis.nft.turbo.inventory.domain.service.impl;

import cn.hollis.nft.turbo.api.inventory.request.InventoryRequest;
import org.springframework.stereotype.Service;

/**
 * @author Hollis
 */
@Service
public class BlindBoxInventoryRedisService extends AbstractInventoryRedisService {

    private static final String INVENTORY_KEY = "blb:inventory:";

    private static final String INVENTORY_STREAM_KEY = "blb:inventory:stream:";

    @Override
    protected String getCacheKey(InventoryRequest request) {
        return INVENTORY_KEY + request.getGoodsId();
    }

    @Override
    protected String getCacheStreamKey(InventoryRequest request) {
        return INVENTORY_STREAM_KEY + request.getGoodsId();
    }
}
