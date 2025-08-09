package cn.hollis.nft.turbo.pay.domain.entity;

import cn.hollis.nft.turbo.datasource.domain.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author Hollis
 * 微信支付交易流水
 */
@ToString
@Getter
@Setter
public class WechatTransaction extends BaseEntity {
    /**
     * 交易时间
     */
    private Date date;
    /**
     * 公众账号ID
     */
    private String appId;
    /**
     * 商户号
     */
    private String mchId;
    /**
     * 子商户号 特约商户号
     */
    private String subMchId;
    /**
     * 设备号
     */
    private String deviceInfo;
    /**
     * 微信订单号
     */
    private String wechatOrderNo;
    /**
     * 商户订单号
     */
    private String mchOrderNo;
    /**
     * 用户标识
     */
    private String userId;
    /**
     * 交易类型
     */
    private String type;
    /**
     * 交易状态
     */
    private String status;
    /**
     * 付款银行
     */
    private String bank;
    /**
     * 货币种类
     */
    private String currency;
    /**
     * 总金额
     */
    private BigDecimal amount;
    /**
     * 企业红包金额 代金券金额
     */
    private BigDecimal envelopeAmount;
    /**
     * 商品名称
     */
    private String name;
    /**
     * 商户数据包
     */
    private String packet;
    /**
     * 手续费
     */
    private String poundage;
    /**
     * 费率
     */
    private String rate;
    /**
     * 订单金额
     */
    private BigDecimal orderAmount;

    /**
     * 退款申请时间
     */
    private Date refundApplyTime;

    /**
     * 退款成功时间
     */
    private Date refundSuccessTime;

    /**
     * 微信退款单号
     */
    private String wxRefundOrderNo;

    /**
     * 商户退款单号
     */
    private String mchRefundOrderNo;

    /**
     * 退款金额
     */
    private BigDecimal refundAmount;

    /**
     * 充值券退款金额
     */
    private BigDecimal envelopeRefundAmount;

    /**
     * 退款类型
     */
    private String refundType;

    /**
     * 退款状态
     */
    private String refundStatus;

    /**
     * 申请退款金额
     */
    private BigDecimal applyRefundAmount;
}