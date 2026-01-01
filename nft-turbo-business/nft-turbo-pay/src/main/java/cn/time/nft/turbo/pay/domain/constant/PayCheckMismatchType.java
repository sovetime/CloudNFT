package cn.time.nft.turbo.pay.domain.constant;


//支付一致性检查结果类型
public enum PayCheckMismatchType {

    PAY_ORDER_NOT_EXIST("支付单不存在"),

    CHANNEL_TRANSACTION_NOT_EXIST("渠道侧交易不存在"),

    PAY_ORDER_STATUS_NOT_SUCCESS("支付单状态未成功"),

    CHANNEL_STREAM_STATUS_NOT_SUCCESS("渠道侧支付状态未成功"),

    CHANNEL_STREAM_AMOUNT_NOT_EQUAL_PAY_ORDER_AMOUNT("金额不一致");


    private String desc;

    PayCheckMismatchType(String desc) {
        this.desc = desc;
    }
}
