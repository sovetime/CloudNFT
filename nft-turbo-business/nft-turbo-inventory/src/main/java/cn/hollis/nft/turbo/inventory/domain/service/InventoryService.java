package cn.hollis.nft.turbo.inventory.domain.service;

import cn.hollis.nft.turbo.api.inventory.request.InventoryRequest;
import cn.hollis.nft.turbo.inventory.domain.response.InventoryResponse;
import org.springframework.stereotype.Service;

import java.util.List;


// 库存服务
@Service
public interface InventoryService {

    // 初始化藏品库存
    public InventoryResponse init(InventoryRequest request);

    // 扣减藏品库存
    public InventoryResponse decrease(InventoryRequest request);

    // 增加藏品库存
    public InventoryResponse increase(InventoryRequest request);

    // 失效藏品库存
    public void invalid(InventoryRequest request);

    // 获取藏品库存
    public Integer getInventory(InventoryRequest request);

    // 获取藏品库存扣减日志
    public String getInventoryDecreaseLog(InventoryRequest request);

    // 获取藏品全部日志
    public List<String> getInventoryDecreaseLogs(InventoryRequest request);

    // 移除库存操作日志
    public Long removeInventoryDecreaseLog(InventoryRequest request);

}
