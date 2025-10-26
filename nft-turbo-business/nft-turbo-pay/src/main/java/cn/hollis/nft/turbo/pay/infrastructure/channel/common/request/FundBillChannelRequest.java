package cn.hollis.nft.turbo.pay.infrastructure.channel.common.request;

import cn.hollis.nft.turbo.base.request.BaseRequest;
import lombok.*;


//资金账单参数
@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class FundBillChannelRequest extends BaseRequest {

    //账单日期，格式yyyy-MM-DD，仅支持三个月内的账单下载申请
    private String billDate;

    //资金账户类型，BASIC(默认):基本账户，OPERATION:运营账户，FEES:手续费账户
    private String accountType;
}
