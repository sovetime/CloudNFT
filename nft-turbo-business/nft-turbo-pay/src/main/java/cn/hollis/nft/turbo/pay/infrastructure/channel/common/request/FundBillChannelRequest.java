package cn.hollis.nft.turbo.pay.infrastructure.channel.common.request;

import cn.hollis.nft.turbo.base.request.BaseRequest;
import lombok.*;

/**
 * 资金账单参数
 * @author Hollis
 * @date 2025/07/01
 */

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class FundBillChannelRequest extends BaseRequest {

    /**
     * 账单日期，格式yyyy-MM-DD，仅支持三个月内的账单下载申请
     */
    private String billDate;
    /**
     * 资金账户类型，不填默认是BASIC
     * 可选取值
     * BASIC: 基本账户
     * OPERATION: 运营账户
     * FEES: 手续费账户
     */
    private String accountType;
}
