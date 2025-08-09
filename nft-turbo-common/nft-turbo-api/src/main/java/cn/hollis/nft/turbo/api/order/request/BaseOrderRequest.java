package cn.hollis.nft.turbo.api.order.request;

import cn.hollis.nft.turbo.api.order.constant.TradeOrderEvent;
import cn.hollis.nft.turbo.base.request.BaseRequest;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public abstract class BaseOrderRequest extends BaseRequest {

    @NotNull(message = "操作幂等号不能为空")
    private String identifier;

    //获取订单事件
    public abstract TradeOrderEvent getOrderEvent();
}
