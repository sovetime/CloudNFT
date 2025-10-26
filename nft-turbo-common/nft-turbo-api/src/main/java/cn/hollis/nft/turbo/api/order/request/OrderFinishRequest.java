package cn.hollis.nft.turbo.api.order.request;

import cn.hollis.nft.turbo.api.order.constant.TradeOrderEvent;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class OrderFinishRequest extends BaseOrderUpdateRequest {

    @Override
    public TradeOrderEvent getOrderEvent() {
        return TradeOrderEvent.FINISH;
    }
}

