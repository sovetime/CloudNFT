package cn.hollis.nft.turbo.api.inventory.request;

import cn.hollis.nft.turbo.api.collection.request.CollectionAirDropRequest;
import cn.hollis.nft.turbo.api.goods.constant.GoodsType;
import cn.hollis.nft.turbo.api.order.model.TradeOrderVO;
import cn.hollis.nft.turbo.api.order.request.OrderCreateRequest;
import cn.hollis.nft.turbo.base.request.BaseRequest;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
public class InventoryRequest extends BaseRequest {

    @NotNull(message = "商品ID不能为null")
    private String goodsId;

    @NotNull(message = "商品类型不能为null")
    private GoodsType goodsType;

    //唯一标识
    private String identifier;

    //需要扣减的库存数量
    private Integer inventory;

    public InventoryRequest(OrderCreateRequest orderCreateRequest) {
        this.goodsId = orderCreateRequest.getGoodsId();
        this.goodsType = orderCreateRequest.getGoodsType();
        this.identifier = orderCreateRequest.getOrderId();
        this.inventory = orderCreateRequest.getItemCount();
    }

    public InventoryRequest(CollectionAirDropRequest request) {
        this.goodsId = request.getCollectionId().toString();
        this.goodsType = GoodsType.COLLECTION;
        this.identifier = request.getIdentifier();
        this.inventory = request.getQuantity();
    }

    public InventoryRequest(TradeOrderVO tradeOrderVO) {
        this.setGoodsId(tradeOrderVO.getGoodsId());
        this.setInventory(tradeOrderVO.getItemCount());
        this.setIdentifier(tradeOrderVO.getOrderId());
        this.setGoodsType(tradeOrderVO.getGoodsType());
    }
}
