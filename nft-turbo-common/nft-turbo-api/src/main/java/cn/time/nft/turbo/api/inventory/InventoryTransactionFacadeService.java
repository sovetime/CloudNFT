package cn.time.nft.turbo.api.inventory;

import cn.time.nft.turbo.api.inventory.request.InventoryRequest;


//库存服务
public interface InventoryTransactionFacadeService {

    //库存扣减-try,try成功进行redis库存预扣减
    public Boolean tryDecrease(InventoryRequest inventoryRequest);

    //库存扣减-confirm
    public Boolean confirmDecrease(InventoryRequest inventoryRequest);

    //库存扣减-cancel
    public Boolean cancelDecrease(InventoryRequest inventoryRequest);
}
