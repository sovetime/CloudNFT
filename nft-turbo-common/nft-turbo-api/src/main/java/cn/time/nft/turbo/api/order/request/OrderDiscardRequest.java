package cn.time.nft.turbo.api.order.request;

import cn.time.nft.turbo.api.order.constant.TradeOrderEvent;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class OrderDiscardRequest extends BaseOrderUpdateRequest {

    @Override
    public TradeOrderEvent getOrderEvent() {
        return TradeOrderEvent.DISCARD;
    }
}

