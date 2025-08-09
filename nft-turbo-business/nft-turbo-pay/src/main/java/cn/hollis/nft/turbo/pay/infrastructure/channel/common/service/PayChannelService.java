package cn.hollis.nft.turbo.pay.infrastructure.channel.common.service;

import cn.hollis.nft.turbo.pay.infrastructure.channel.common.request.*;
import cn.hollis.nft.turbo.pay.infrastructure.channel.common.response.BillChannelResponse;
import cn.hollis.nft.turbo.pay.infrastructure.channel.common.response.BillDownloadChannelResponse;
import cn.hollis.nft.turbo.pay.infrastructure.channel.common.response.PayChannelResponse;
import cn.hollis.nft.turbo.pay.infrastructure.channel.common.response.RefundChannelResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * 支付渠道服务
 *
 * @author Hollis
 */
public interface PayChannelService {
    /**
     * 支付
     *
     * @param payChannelRequest
     * @return
     */
    PayChannelResponse pay(PayChannelRequest payChannelRequest);

    /**
     * 支付结果回调
     *
     * @param request
     * @param response
     * @return 通知结果
     */
    boolean notify(HttpServletRequest request, HttpServletResponse response);

    /**
     * 退款
     *
     * @param refundChannelRequest
     * @return
     */
    RefundChannelResponse refund(RefundChannelRequest refundChannelRequest);

    /**
     * 退款结果回调
     *
     * @param request
     * @param response
     * @return
     */
    boolean refundNotify(HttpServletRequest request, HttpServletResponse response);

    /**
     * 交易账单
     *
     * @param billChannelRequest
     * @return
     */
    BillChannelResponse tradeBill(TradeBillChannelRequest billChannelRequest);

    /**
     * 资金账单
     *
     * @param billChannelRequest
     * @return
     */
    BillChannelResponse fundBill(FundBillChannelRequest billChannelRequest);

    /**
     * 下载账单
     *
     * @param request
     * @return
     */
    BillDownloadChannelResponse downloadBill(DownloadBillChannelRequest request);
}
