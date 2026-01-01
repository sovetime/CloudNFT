package cn.time.nft.turbo.pay.domain.entity;

import cn.time.nft.turbo.datasource.domain.entity.BaseEntity;
import cn.time.nft.turbo.pay.domain.constant.PayCheckMismatchType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Date;


//支付核对不一致的记录
@ToString
@Getter
@Setter
public class PayCheckMismatchDetail extends BaseEntity {

    //支付单号
    private String payOrderId;

    //渠道流水号
    private String channelStreamId;

    //核对时间
    private Date checkTime;

    //支付单状态
    private String payOrderState;

    //渠道流水状态
    private String channelStreamState;

    //支付单金额
    private BigDecimal payOrderAmount;

    //渠道流水金额
    private BigDecimal channelStreamAmount;

    //支付单时间
    private Date payOrderTime;

    //渠道流水时间
    private Date channelStreamTime;

    //不一致类型
    private PayCheckMismatchType mismatchType;

    //是否是日切相关数据
    private Boolean isDailyCut;

    //状态
    //INIT 初始状态
    //SUSPEND 挂起状态
    //FINISH 完成状态
    private String status;

    public static PayCheckMismatchDetail build(PayOrder payOrder, WechatTransaction wechatTransaction, PayCheckMismatchType mismatchType) {
        PayCheckMismatchDetail payCheckMismatchDetail = new PayCheckMismatchDetail();
        payCheckMismatchDetail.payOrderId = payOrder.getPayOrderId();
        payCheckMismatchDetail.channelStreamId = wechatTransaction.getMchOrderNo();
        payCheckMismatchDetail.checkTime = new Date();
        payCheckMismatchDetail.payOrderState = payOrder.getOrderState().name();
        payCheckMismatchDetail.channelStreamState = wechatTransaction.getStatus();
        payCheckMismatchDetail.payOrderAmount = payOrder.getPaidAmount();
        payCheckMismatchDetail.channelStreamAmount = wechatTransaction.getAmount();
        payCheckMismatchDetail.payOrderTime = payOrder.getPaySucceedTime();
        payCheckMismatchDetail.channelStreamTime = wechatTransaction.getDate();
        payCheckMismatchDetail.mismatchType = mismatchType;
        payCheckMismatchDetail.status = "INIT";
        return payCheckMismatchDetail;
    }

    public static PayCheckMismatchDetail build(PayOrder payOrder) {
        PayCheckMismatchDetail payCheckMismatchDetail = new PayCheckMismatchDetail();
        payCheckMismatchDetail.payOrderId = payOrder.getPayOrderId();
        payCheckMismatchDetail.checkTime = new Date();
        payCheckMismatchDetail.payOrderState = payOrder.getOrderState().name();
        payCheckMismatchDetail.payOrderAmount = payOrder.getPaidAmount();
        payCheckMismatchDetail.payOrderTime = payOrder.getPaySucceedTime();
        payCheckMismatchDetail.mismatchType = PayCheckMismatchType.CHANNEL_TRANSACTION_NOT_EXIST;
        payCheckMismatchDetail.status = "INIT";
        return payCheckMismatchDetail;
    }

    public static PayCheckMismatchDetail build(WechatTransaction wechatTransaction) {
        PayCheckMismatchDetail payCheckMismatchDetail = new PayCheckMismatchDetail();
        payCheckMismatchDetail.channelStreamId = wechatTransaction.getMchOrderNo();
        payCheckMismatchDetail.checkTime = new Date();
        payCheckMismatchDetail.channelStreamState = wechatTransaction.getStatus();
        payCheckMismatchDetail.channelStreamAmount = wechatTransaction.getAmount();
        payCheckMismatchDetail.channelStreamTime = wechatTransaction.getDate();
        payCheckMismatchDetail.mismatchType = PayCheckMismatchType.PAY_ORDER_NOT_EXIST;
        payCheckMismatchDetail.status = "INIT";
        return payCheckMismatchDetail;
    }
}
