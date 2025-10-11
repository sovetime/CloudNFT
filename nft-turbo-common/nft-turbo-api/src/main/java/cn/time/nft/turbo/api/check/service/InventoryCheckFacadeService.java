package cn.time.nft.turbo.api.check.service;

import cn.time.nft.turbo.api.check.request.InventoryCheckRequest;
import cn.time.nft.turbo.api.check.response.InventoryCheckResponse;


public interface InventoryCheckFacadeService {

    // 库存核对
    public InventoryCheckResponse check(InventoryCheckRequest request);
}
