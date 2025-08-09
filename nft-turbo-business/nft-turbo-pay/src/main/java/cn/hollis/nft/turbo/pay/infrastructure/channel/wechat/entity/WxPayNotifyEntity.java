package cn.hollis.nft.turbo.pay.infrastructure.channel.wechat.entity;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Hollis
 */
@Getter
@Setter
public class WxPayNotifyEntity {
    
    @JSONField(name = "transaction_id")
    private String transactionId;

    @JSONField(name = "amount")
    private WxNotifyResourceEntityAmountEntity amount;

    @JSONField(name = "mchid")
    private String mchid;

    @JSONField(name = "trade_state")
    private String tradeState;

    @JSONField(name = "bank_type")
    private String bankType;

    @JSONField(name = "success_time")
    private String successTime;

    @JSONField(name = "payer")
    private WxNotifyResourceEntityPayerEntity payer;

    @JSONField(name = "out_trade_no")
    private String outTradeNo;

    @JSONField(name = "appid")
    private String appid;

    @JSONField(name = "trade_state_desc")
    private String tradeStateDesc;

    @JSONField(name = "trade_type")
    private String tradeType;

    @JSONField(name = "attach")
    private String attach;

    @JSONField(name = "scene_info")
    private WxNotifyResourceEntitySceneInfoEntity sceneInfo;

}
