package cn.hollis.nft.turbo.api.order.request;

import cn.hollis.nft.turbo.api.goods.constant.GoodsType;
import cn.hollis.nft.turbo.api.order.constant.TradeOrderEvent;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class OrderConfirmRequest extends BaseOrderUpdateRequest {

    //买家Id
    private String buyerId;

    //商品Id
    private String goodsId;

    //商品类型
    private GoodsType goodsType;

    //数量
    private Integer itemCount;

    @Override
    public TradeOrderEvent getOrderEvent() {
        return TradeOrderEvent.CONFIRM;
    }
}

