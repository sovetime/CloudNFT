package cn.hollis.nft.turbo.pay.infrastructure.channel.common.request;

import cn.hollis.nft.turbo.base.request.BaseRequest;
import lombok.*;

/**
 * 交易账单参数
 * @author Hollis
 * @date 2025/07/01
 */

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class TradeBillChannelRequest extends BaseRequest {

    /**
     * 账单日期，格式yyyy-MM-DD，仅支持三个月内的账单下载申请
     */
    private String billDate;
    /**
     * 账单类型
     * ALL: 返回当日所有订单信息（不含充值退款订单）
     * SUCCESS: 返回当日成功支付的订单（不含充值退款订单）
     * REFUND: 返回当日退款订单（不含充值退款订单）
     */
    private String billType;

    /**
     * 压缩类型
     * 不填则以不压缩的方式返回账单文件流
     * GZIP: 下载账单时返回.gzip格式的压缩文件流
     */
    private String tarType;
}
