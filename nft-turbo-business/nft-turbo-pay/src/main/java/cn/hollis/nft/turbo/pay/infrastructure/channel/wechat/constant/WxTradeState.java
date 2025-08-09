package cn.hollis.nft.turbo.pay.infrastructure.channel.wechat.constant;

/**
 * 微信支付交易状态
 * https://pay.weixin.qq.com/wiki/doc/apiv3/apis/chapter3_1_5.shtml
 * @author Hollis
 */
public enum WxTradeState {
    /**
     * 支付成功
     */
    SUCCESS("SUCCESS", "支付成功"),
    /**
     * 转入退款
     */
    REFUND("REFUND", "转入退款"),
    /**
     * 未支付
     */
    NOTPAY("NOTPAY", "未支付"),
    /**
     * 已关闭
     */
    CLOSED("CLOSED", "已关闭"),
    /**
     * 已撤销（刷卡支付）
     */
    REVOKED("REVOKED", "已撤销（刷卡支付）"),
    /**
     * 支付中
     */
    USERPAYING("USERPAYING", "用户支付中"),
    /**
     * 支付失败
     */
    PAYERROR("PAYERROR", "支付失败(其他原因，如银行返回失败)");

    private String code;
    private String message;

    WxTradeState(String code, String message) {}
}
