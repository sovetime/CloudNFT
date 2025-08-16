package cn.hollis.nft.turbo.api.pay.request;

import cn.hollis.nft.turbo.api.common.constant.BizOrderType;
import cn.hollis.nft.turbo.api.pay.constant.PayChannel;
import cn.hollis.nft.turbo.api.user.constant.UserType;
import cn.hollis.nft.turbo.base.request.BaseRequest;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;


@Getter
@Setter
public class PayCreateRequest extends BaseRequest {

    @NotNull(message = "付款方id不能为null")
    private String payerId;

    @NotNull(message = "付款方类型不能为null")
    private UserType payerType;

    @NotNull(message = "收款方id不能为null")
    private String payeeId;

    @NotNull(message = "收款方类型不能为null")
    private UserType payeeType;

    @NotNull(message = "业务单号不能为null")
    private String bizNo;

    @NotNull(message = "业务单号类型不能为null")
    private BizOrderType bizType;

    @NotNull(message = "订单金额不能为null")
    private BigDecimal orderAmount;

    @NotNull(message = "支付渠道不能为null")
    private PayChannel payChannel;

    @NotNull(message = "支付备注不能为null")
    private String memo;

}
