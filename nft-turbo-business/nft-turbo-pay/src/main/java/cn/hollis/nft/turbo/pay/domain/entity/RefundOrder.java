package cn.hollis.nft.turbo.pay.domain.entity;

import cn.hollis.nft.turbo.api.pay.constant.PayChannel;
import cn.hollis.nft.turbo.api.pay.constant.PayRefundOrderState;
import cn.hollis.nft.turbo.api.pay.request.RefundCreateRequest;
import cn.hollis.nft.turbo.api.user.constant.UserType;
import cn.hollis.nft.turbo.datasource.domain.entity.BaseEntity;
import cn.hollis.nft.turbo.api.common.constant.BusinessCode;
import cn.hollis.nft.turbo.pay.domain.event.RefundSuccessEvent;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.IdUtil;
import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 支付退款单
 *
 * @author Hollis
 */
@Setter
@Getter
public class RefundOrder extends BaseEntity {

    /**
     * 支付单号
     */
    private String payOrderId;

    /**
     * 支付的外部流水号，冗余用于退款执行
     */
    private String payChannelStreamId;

    /**
     * 支付的金额，冗余用于退款执行
     */
    private BigDecimal paidAmount;

    /**
     * 退款单号
     */
    private String refundOrderId;

    /**
     * 退款申请幂等号
     */
    private String identifier;

    /**
     * 付款方id
     */
    private String payerId;

    /**
     * 付款方id类型
     */
    private UserType payerType;

    /**
     * 收款方id
     */
    private String payeeId;

    /**
     * 收款方id类型
     */
    private UserType payeeType;

    /**
     * 需要退款的金额
     */
    private BigDecimal applyRefundAmount;

    /**
     * 已退款金额
     */
    private BigDecimal refundedAmount;

    /**
     * 退款的外部支付流水号
     */
    private String refundChannelStreamId;

    /**
     * 退款渠道
     */
    private PayChannel refundChannel;

    /**
     * 退款备注
     */
    private String memo;

    /**
     * 订单状态
     */
    private PayRefundOrderState refundOrderState;

    /**
     * 退款成功时间
     */
    private Date refundSucceedTime;

    @JSONField(serialize = false)
    public boolean isRefunded() {
        return refundedAmount != null && refundedAmount.compareTo(BigDecimal.ZERO) > 0 && refundOrderState == PayRefundOrderState.REFUNDED && refundChannelStreamId != null && refundSucceedTime != null;
    }

    public static RefundOrder create(RefundCreateRequest refundCreateRequest,PayOrder payOrder) {
        RefundOrder refundOrder = new RefundOrder();
        refundOrder.setRefundOrderId(String.valueOf(IdUtil.getSnowflake(BusinessCode.REFUND_ORDER.code()).nextId()));
        refundOrder.setApplyRefundAmount(refundCreateRequest.getRefundAmount());
        refundOrder.setIdentifier(refundCreateRequest.getIdentifier());
        refundOrder.setMemo(refundCreateRequest.getMemo());
        refundOrder.setPayOrderId(payOrder.getPayOrderId());
        refundOrder.setRefundChannel(refundCreateRequest.getRefundChannel());
        refundOrder.setRefundOrderState(PayRefundOrderState.TO_REFUND);
        // 退款是支付的逆向操作
        refundOrder.setPayeeId(payOrder.getPayerId());
        refundOrder.setPayeeType(payOrder.getPayerType());
        refundOrder.setPayerId(payOrder.getPayeeId());
        refundOrder.setPayerType(payOrder.getPayeeType());
        refundOrder.setPaidAmount(payOrder.getPaidAmount());
        refundOrder.setPayChannelStreamId(payOrder.getChannelStreamId());

        return refundOrder;
    }

    public RefundOrder refunding(){
        Assert.equals(this.getRefundOrderState(), PayRefundOrderState.TO_REFUND);
        this.setRefundOrderState(PayRefundOrderState.REFUNDING);
        return this;
    }

    public RefundOrder refundSuccess(RefundSuccessEvent refundSuccessEvent) {
        Assert.equals(this.getRefundOrderState(), PayRefundOrderState.REFUNDING);
        this.setRefundOrderState(PayRefundOrderState.REFUNDED);
        this.refundChannelStreamId = refundSuccessEvent.getChannelStreamId();
        this.refundSucceedTime = refundSuccessEvent.getRefundedTime();
        this.refundedAmount = refundSuccessEvent.getRefundedAmount();
        return this;
    }
}
