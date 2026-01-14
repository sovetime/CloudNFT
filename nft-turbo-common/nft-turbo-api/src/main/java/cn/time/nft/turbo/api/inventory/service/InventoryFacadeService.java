package cn.time.nft.turbo.api.inventory.service;

import cn.time.nft.turbo.api.inventory.request.InventoryRequest;
import cn.time.nft.turbo.base.response.MultiResponse;
import cn.time.nft.turbo.base.response.SingleResponse;


//库存服务
public interface InventoryFacadeService {

    //库存初始化
    public SingleResponse<Boolean> init(InventoryRequest inventoryRequest);

    //库存扣减(redis)
    public SingleResponse<Boolean> decrease(InventoryRequest inventoryRequest);

    //库存增加（redis库存）
    public SingleResponse<Boolean> increase(InventoryRequest inventoryRequest);

    //库存失效
    public SingleResponse<Void> invalid(InventoryRequest inventoryRequest);

    //获取库存扣减流水
    public SingleResponse<String> getInventoryDecreaseLog(InventoryRequest inventoryRequest);

    //获取库存增加流水
    public SingleResponse<String> getInventoryIncreaseLog(InventoryRequest inventoryRequest);

    //批量获取库存扣减流水
    public MultiResponse<String> getInventoryDecreaseLogs(InventoryRequest inventoryRequest);

    //从redis中获取商品库存
    public SingleResponse<Integer> queryInventory(InventoryRequest inventoryRequest);

    //移除流水
    public SingleResponse<Long> removeInventoryDecreaseLog(InventoryRequest inventoryRequest);


}
