package cn.hollis.nft.turbo.api.order.request;

import cn.hollis.nft.turbo.api.order.constant.TradeOrderEvent;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class OrderTimeoutRequest extends BaseOrderUpdateRequest {

    @Override
    public TradeOrderEvent getOrderEvent() {
        return TradeOrderEvent.TIME_OUT;
    }
}

