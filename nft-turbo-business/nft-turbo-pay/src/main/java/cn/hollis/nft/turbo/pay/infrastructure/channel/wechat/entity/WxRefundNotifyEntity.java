package cn.hollis.nft.turbo.pay.infrastructure.channel.wechat.entity;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Hollis
 */
@Getter
@Setter
public class WxRefundNotifyEntity {

    @JSONField(name = "transaction_id")
    private String transactionId;

    @JSONField(name = "out_trade_no")
    private String outTradeNo;

    @JSONField(name = "refund_id")
    private String refundId;

    @JSONField(name = "out_refund_no")
    private String outRefundNo;

    @JSONField(name = "total_fee")
    private Integer totalFee;

    @JSONField(name = "refund_fee")
    private Integer refundFee;

    @JSONField(name = "settlement_refund_fee")
    private Integer settlementRefundFee;

    @JSONField(name = "refund_status")
    private String refundStatus;

    @JSONField(name = "success_time")
    private String successTime;

}
