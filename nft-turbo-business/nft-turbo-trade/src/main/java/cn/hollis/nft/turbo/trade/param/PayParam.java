package cn.hollis.nft.turbo.trade.param;

import cn.hollis.nft.turbo.api.pay.constant.PayChannel;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class PayParam {

    @NotNull(message = "订单id不能为null")
    private String orderId;

    @NotNull(message = "支付渠道不能为null")
    private PayChannel payChannel;

}
