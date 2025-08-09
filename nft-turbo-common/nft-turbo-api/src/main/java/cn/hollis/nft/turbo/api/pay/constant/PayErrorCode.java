package cn.hollis.nft.turbo.api.pay.constant;

import cn.hollis.nft.turbo.base.exception.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

//支付错误code
@AllArgsConstructor
@Getter
public enum PayErrorCode implements ErrorCode {

    PAY_ORDER_CREATE_FAILED("PAY_ORDER_CREATE_FAILED", "支付单创建失败"),

    ORDER_IS_ALREADY_PAID("ORDER_IS_ALREADY_PAID", "订单已支付过"),

    PAY_SUCCESS_NOTICE_FAILED("PAY_SUCCESS_NOTICE_FAILED", "支付成功回调处理失败"),

    REFUND_CREATE_FAILED("REFUND_CREATE_FAILED", "退款创建失败"),

    PAY_ORDER_STATUS_CHECK_FAILED("PAY_ORDER_STATUS_CHECK_FAILED", "支付单状态校验失败"),

    REFUND_SUCCESS_NOTICE_FAILED("REFUND_SUCCESS_NOTICE_FAILED", "退款成功回调处理失败");


    private String code;

    private String message;

}
