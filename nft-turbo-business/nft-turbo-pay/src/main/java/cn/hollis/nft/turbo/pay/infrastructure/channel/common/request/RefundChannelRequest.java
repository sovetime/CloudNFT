package cn.hollis.nft.turbo.pay.infrastructure.channel.common.request;

import cn.hollis.nft.turbo.base.request.BaseRequest;
import lombok.*;

/**
 * 退款参数
 *
 * @author wswyb001
 * @date 2024/02/14
 */

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class RefundChannelRequest extends BaseRequest {

    /**
     * 支付单号
     */
    private String payOrderId;

    /**
     * 外部支付流水号
     */
    private String payChannelStreamId;

    /**
     * 退款单号
     */
    private String refundOrderId;

    /**
     * 原支付金额
     * 单位：分
     */
    private Long paidAmount;

    /**
     * 退款金额
     * 单位：分
     */
    private Long refundAmount;

    /**
     * 退款原因
     */
    private String refundReason;

}
