package cn.hollis.nft.turbo.pay.infrastructure.channel.wechat.constant;

public enum WxBillType {

    /**
     * 返回当日成功支付的订单（不含充值退款订单）
     */
    SUCCESS,

    /**
     * 返回当日退款订单（不含充值退款订单）
     */
    REFUND,

    /**
     * 返回当日所有订单信息（不含充值退款订单）
     */
    ALL
}
