package cn.hollis.nft.turbo.pay.infrastructure.channel.common.request;

import cn.hollis.nft.turbo.base.request.BaseRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/**
 * 支付参数
 * @author wswyb001
 * @date 2024/02/14
 */

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class PayChannelRequest extends BaseRequest {

    /**
     * 支付单号
     */
    private String orderId;
    /**
     * 金额
     * 单位：分
     */
    private Long amount;
    /**
     * 订单描述
     */
    private String description;

    /**
     * 附加信息
     */
    private String attach;

    /**
     * 超时时间
     */
    private Date expireTime;
}
