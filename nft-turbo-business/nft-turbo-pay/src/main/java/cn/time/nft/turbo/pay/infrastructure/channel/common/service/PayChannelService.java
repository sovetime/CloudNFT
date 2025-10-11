package cn.time.nft.turbo.pay.infrastructure.channel.common.service;

import cn.time.nft.turbo.pay.infrastructure.channel.common.request.*;
import cn.time.nft.turbo.pay.infrastructure.channel.common.response.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


//支付渠道服务
public interface PayChannelService {

    //支付
    PayChannelResponse pay(PayChannelRequest payChannelRequest);

    //查询支付结果
    PayResultQueryResponse payResultQuery(String channelStreamId);

    //支付结果回调
    boolean notify(HttpServletRequest request, HttpServletResponse response);

    // 退款
    RefundChannelResponse refund(RefundChannelRequest refundChannelRequest);

    //退款结果回调
    boolean refundNotify(HttpServletRequest request, HttpServletResponse response);

    //交易账单
    BillChannelResponse tradeBill(TradeBillChannelRequest billChannelRequest);

    //资金账单
    BillChannelResponse fundBill(FundBillChannelRequest billChannelRequest);

    //下载账单
    BillDownloadChannelResponse downloadBill(DownloadBillChannelRequest request);
}
