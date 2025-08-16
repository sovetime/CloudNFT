package cn.hollis.nft.turbo.pay.infrastructure.channel.common.service.impl;

import cn.hollis.nft.turbo.api.pay.constant.PayChannel;
import cn.hollis.nft.turbo.base.utils.MoneyUtils;
import cn.hollis.nft.turbo.pay.application.service.PayApplicationService;
import cn.hollis.nft.turbo.pay.domain.event.PaySuccessEvent;
import cn.hollis.nft.turbo.pay.domain.event.RefundSuccessEvent;
import cn.hollis.nft.turbo.pay.infrastructure.channel.common.request.*;
import cn.hollis.nft.turbo.pay.infrastructure.channel.common.response.BillChannelResponse;
import cn.hollis.nft.turbo.pay.infrastructure.channel.common.response.BillDownloadChannelResponse;
import cn.hollis.nft.turbo.pay.infrastructure.channel.common.response.PayChannelResponse;
import cn.hollis.nft.turbo.pay.infrastructure.channel.common.response.RefundChannelResponse;
import cn.hollis.nft.turbo.pay.infrastructure.channel.common.service.PayChannelService;
import com.alibaba.ttl.TransmittableThreadLocal;
import com.alibaba.ttl.threadpool.TtlExecutors;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;


// mock支付渠道
@Service("mockPayChannelService")
@Slf4j
@Lazy
public class MockPayChannelServiceImpl implements PayChannelService {
    @Autowired
    private PayApplicationService payApplicationService;

    //线程本地变量，用于在异步线程间传递上下文参数
    //使用TransmittableThreadLocal确保在线程池中也能正确传递
    public static TransmittableThreadLocal<Map> context = new TransmittableThreadLocal<>();

    //创建线程工厂
    private static ThreadFactory chainResultProcessFactory = new ThreadFactoryBuilder()
            .setNameFormat("pay-process-pool-%d").build();

    //定时线程池执行器，用于延迟执行支付/退款回调
    //使用TTL包装确保TransmittableThreadLocal正常工作，在支付的时候需要设置context.set(params)
    //异步线程执行回调需要获取context.get()
    ScheduledExecutorService scheduler = TtlExecutors.getTtlScheduledExecutorService(
            new ScheduledThreadPoolExecutor(10, chainResultProcessFactory));

    // 支付
    @Override
    public PayChannelResponse pay(PayChannelRequest payChannelRequest) {
        //响应构造
        PayChannelResponse payChannelResponse = new PayChannelResponse();
        payChannelResponse.setSuccess(true);
        payChannelResponse.setPayUrl("https://github.com/sovetime/CloudNFT");

        Map<String, Serializable> params = new HashMap<>(12);
        params.put("payOrderId", payChannelRequest.getOrderId());
        params.put("paidAmount", payChannelRequest.getAmount());
        context.set(params);

        //异步线程延迟3秒钟之后调用 notify 方法
        scheduler.schedule(() -> {
            this.notify(null, null);
        }, 3, TimeUnit.SECONDS);

        return payChannelResponse;
    }

    //结果回调
    @Override
    public boolean notify(HttpServletRequest request, HttpServletResponse response) {
        try {
            // 构造支付成功事件对象
            PaySuccessEvent paySuccessEvent = new PaySuccessEvent();
            // 生成唯一的渠道流水号
            paySuccessEvent.setChannelStreamId(UUID.randomUUID().toString());
            // 从线程本地变量中获取之前存储的参数
            Map<String, Serializable> params = (Map<String, Serializable>) context.get();
            //参数设置
            paySuccessEvent.setPaidAmount(MoneyUtils.centToYuan((Long) params.get("paidAmount")));
            paySuccessEvent.setPayOrderId((String) params.get("payOrderId"));
            paySuccessEvent.setPaySucceedTime(new Date());
            paySuccessEvent.setPayChannel(PayChannel.MOCK);

            // 调用订单支付逻辑
            boolean paySuccessResult = payApplicationService.paySuccess(paySuccessEvent);
        } catch (Exception e) {
            log.error("nofity error", e);
            return false;
        }
        return true;
    }

    // 退款
    @Override
    public RefundChannelResponse refund(RefundChannelRequest refundChannelRequest) {
        RefundChannelResponse refundChannelResponse = new RefundChannelResponse();
        refundChannelResponse.setSuccess(true);
        Map<String, Serializable> params = new HashMap<>(12);
        params.put("payOrderId", refundChannelRequest.getPayOrderId());
        params.put("refundOrderId", refundChannelRequest.getRefundOrderId());
        params.put("refundedAmount", refundChannelRequest.getRefundAmount());
        context.set(params);

        //异步线程延迟3秒钟之后调用 notify 方法
        scheduler.schedule(() -> {
            this.refundNotify(null, null);
        }, 3, TimeUnit.SECONDS);

        return refundChannelResponse;
    }

    //退款结果回调
    @Override
    public boolean refundNotify(HttpServletRequest request, HttpServletResponse response) {
        try {
            RefundSuccessEvent refundSuccessEvent = new RefundSuccessEvent();
            refundSuccessEvent.setChannelStreamId(UUID.randomUUID().toString());
            Map<String, Serializable> params = (Map<String, Serializable>) context.get();
            refundSuccessEvent.setRefundOrderId((String) params.get("refundOrderId"));
            refundSuccessEvent.setPayOrderId((String) params.get("payOrderId"));
            refundSuccessEvent.setRefundedTime(new Date());
            refundSuccessEvent.setRefundChannel(PayChannel.MOCK);
            refundSuccessEvent.setRefundedAmount(MoneyUtils.centToYuan((Long) params.get("refundedAmount")));

            payApplicationService.refundSuccess(refundSuccessEvent);
        } catch (Exception e) {
            log.error("nofity error", e);
            return false;
        }
        return true;
    }

    //交易账单
    @Override
    public BillChannelResponse tradeBill(TradeBillChannelRequest billChannelRequest) {
        return null;
    }

    //资金账单
    @Override
    public BillChannelResponse fundBill(FundBillChannelRequest billChannelRequest) {
        return null;
    }

    //下载账单
    @Override
    public BillDownloadChannelResponse downloadBill(DownloadBillChannelRequest request) {
        return null;
    }
}
