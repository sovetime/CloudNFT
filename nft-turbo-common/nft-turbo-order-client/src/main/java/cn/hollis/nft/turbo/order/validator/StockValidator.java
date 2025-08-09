package cn.hollis.nft.turbo.order.validator;

import cn.hollis.nft.turbo.api.inventory.request.InventoryRequest;
import cn.hollis.nft.turbo.api.inventory.service.InventoryFacadeService;
import cn.hollis.nft.turbo.api.order.request.OrderCreateRequest;
import cn.hollis.nft.turbo.base.response.SingleResponse;
import cn.hollis.nft.turbo.order.OrderException;

import static cn.hollis.nft.turbo.api.order.constant.OrderErrorCode.INVENTORY_NOT_ENOUGH;

/**
 * 库存校验器
 *
 * @author hollis
 */
public class StockValidator extends BaseOrderCreateValidator {

    private InventoryFacadeService inventoryFacadeService;

    @Override
    public void doValidate(OrderCreateRequest request) throws OrderException {

        InventoryRequest inventoryRequest = new InventoryRequest();
        inventoryRequest.setGoodsId(request.getGoodsId());
        inventoryRequest.setGoodsType(request.getGoodsType());
        inventoryRequest.setIdentifier(request.getIdentifier());
        inventoryRequest.setInventory(request.getItemCount());

        SingleResponse<Integer> response = inventoryFacadeService.queryInventory(inventoryRequest);

        if (!response.getSuccess()) {
            throw new OrderException(INVENTORY_NOT_ENOUGH);
        }

        Integer inventory = response.getData();

        if (inventory == 0) {
            throw new OrderException(INVENTORY_NOT_ENOUGH);
        }

        if (inventory < request.getItemCount()) {
            throw new OrderException(INVENTORY_NOT_ENOUGH);
        }
    }

    public StockValidator(InventoryFacadeService inventoryFacadeService) {
        this.inventoryFacadeService = inventoryFacadeService;
    }

    public StockValidator() {
    }
}
