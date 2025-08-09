package cn.hollis.nft.turbo.api.inventory.service;

import cn.hollis.nft.turbo.api.inventory.request.InventoryRequest;
import cn.hollis.nft.turbo.base.response.MultiResponse;
import cn.hollis.nft.turbo.base.response.SingleResponse;


//库存服务
public interface InventoryFacadeService {

    //库存初始化
    public SingleResponse<Boolean> init(InventoryRequest inventoryRequest);

    //库存扣减
    public SingleResponse<Boolean> decrease(InventoryRequest inventoryRequest);

    //库存增加
    public SingleResponse<Boolean> increase(InventoryRequest inventoryRequest);

    //库存失效
    public SingleResponse<Void> invalid(InventoryRequest inventoryRequest);

    //查询库存操作流水
    public SingleResponse<String> getInventoryDecreaseLog(InventoryRequest inventoryRequest);

    //批量查询库存流水
    public MultiResponse<String> getInventoryDecreaseLogs(InventoryRequest inventoryRequest);

    // 查询库存
    public SingleResponse<Integer> queryInventory(InventoryRequest inventoryRequest);

    //移除流水
    public SingleResponse<Long> removeInventoryDecreaseLog(InventoryRequest inventoryRequest);


}
