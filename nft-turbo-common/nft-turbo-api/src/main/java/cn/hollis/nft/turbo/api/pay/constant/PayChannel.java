package cn.hollis.nft.turbo.api.pay.constant;


import lombok.AllArgsConstructor;
import lombok.Getter;

//支付渠道
@AllArgsConstructor
@Getter
public enum PayChannel {

    ALIPAY("支付宝"),

    WECHAT("微信"),

    MOCK("MOCK");

    private String value;

}
