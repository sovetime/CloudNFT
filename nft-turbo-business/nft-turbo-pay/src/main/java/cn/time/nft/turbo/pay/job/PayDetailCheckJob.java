package cn.time.nft.turbo.pay.job;

import cn.time.nft.turbo.api.pay.constant.PayOrderState;
import cn.time.nft.turbo.base.utils.MoneyUtils;
import cn.time.nft.turbo.pay.application.service.PayApplicationService;
import cn.time.nft.turbo.pay.domain.constant.PayCheckMismatchType;
import cn.time.nft.turbo.pay.domain.entity.PayCheckMismatchDetail;
import cn.time.nft.turbo.pay.domain.entity.PayOrder;
import cn.time.nft.turbo.pay.domain.entity.WechatTransaction;
import cn.time.nft.turbo.pay.domain.event.PaySuccessEvent;
import cn.time.nft.turbo.pay.domain.service.PayCheckMismatchDetailService;
import cn.time.nft.turbo.pay.domain.service.PayOrderService;
import cn.time.nft.turbo.pay.domain.service.WechatTransactionService;
import cn.time.nft.turbo.pay.infrastructure.channel.common.response.PayResultQueryResponse;
import cn.time.nft.turbo.pay.infrastructure.channel.common.service.PayChannelServiceFactory;
import cn.time.nft.turbo.pay.infrastructure.channel.wechat.constant.WxTradeState;
import cn.time.nft.turbo.pay.infrastructure.channel.wechat.entity.WxPayNotifyEntity;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static cn.time.nft.turbo.api.common.constant.CommonConstant.COMMON_DATE_PATTERN;
import static cn.time.nft.turbo.pay.infrastructure.channel.wechat.constant.WxBillType.SUCCESS;


//支付一致性检查任务
@Component
@Slf4j
public class PayDetailCheckJob {

    @Autowired
    private PayOrderService payOrderService;

    @Autowired
    private WechatTransactionService wechatTransactionService;

    @Autowired
    private PayChannelServiceFactory payChannelServiceFactory;

    @Autowired
    private PayCheckMismatchDetailService payCheckMismatchDetailService;

    @Autowired
    private PayApplicationService payApplicationService;

    private static final int PAGE_SIZE = 300;

    //从支付订单表获取成功订单，并核对渠道账单
    @XxlJob("payDetailCheckFromPayOrderJob")
    public ReturnT<String> payDetailCheckFromPayOrderJob() {

        //从xxl-job的配置获取时间参数
        Date billDate;
        if (StringUtils.isNotBlank(XxlJobHelper.getJobParam())) {
            SimpleDateFormat sdf = new SimpleDateFormat(COMMON_DATE_PATTERN);
            //默认查询上一日的账单
            try {
                billDate = sdf.parse(XxlJobHelper.getJobParam());
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        } else {
            billDate = DateUtils.addDays(new Date(), -1);
        }

        int currentPage = 1;

        Page<PayOrder> payOrders = payOrderService.pageQuerySucceedOrders(PAGE_SIZE, currentPage, billDate);

        payOrders.getRecords().forEach(this::doExecute);

        while (payOrders.getCurrent() < payOrders.getPages()) {
            currentPage++;
            payOrders = payOrderService.pageQuerySucceedOrders(PAGE_SIZE, currentPage, billDate);
            payOrders.getRecords().forEach(this::doExecute);
        }

        return ReturnT.SUCCESS;
    }


    public void doExecute(PayOrder payOrder) {
        WechatTransaction wechatTransaction = wechatTransactionService.queryByMchOrderNo(payOrder.getPayOrderId());
        PayCheckMismatchDetail payCheckMismatchDetail = null;

        if (wechatTransaction == null || !wechatTransaction.getStatus().equals(SUCCESS.name()) || wechatTransaction.getAmount().compareTo(payOrder.getPaidAmount()) != 0) {
            //核对存在不一致的情况时，直接调渠道侧查询一把，避免出现因为时间差、日切等问题导致的误报
            //因为渠道侧提供了单个查询的接口，所以我们可以在核对不一致时去查一把，如果渠道侧不支持，或者不让我们大量查询，则这里需要按照文档中给出的二次核对的方式做处理解决日切问题
            PayResultQueryResponse response = payChannelServiceFactory.get(payOrder.getPayChannel()).payResultQuery(payOrder.getPayOrderId());
            WxPayNotifyEntity wxPayNotifyEntity = response.getWxPayNotifyEntity();
            if (response.getSuccess() && wxPayNotifyEntity != null) {
                if (WxTradeState.SUCCESS.name().equals(wxPayNotifyEntity.getTradeState()) && MoneyUtils.centToYuan(Long.valueOf(wxPayNotifyEntity.getAmount().getTotal())).compareTo(payOrder.getPaidAmount()) == 0) {
                    return;
                }
            }
        }

        //我们有，渠道侧没有
        if (wechatTransaction == null) {
            payCheckMismatchDetail = PayCheckMismatchDetail.build(payOrder);
        }
        //我们成功了，但是渠道侧状态未成功
        else if (!wechatTransaction.getStatus().equals(SUCCESS.name())) {
            payCheckMismatchDetail = PayCheckMismatchDetail.build(payOrder, wechatTransaction, PayCheckMismatchType.CHANNEL_STREAM_STATUS_NOT_SUCCESS);
        }

        //我们的金额和渠道侧不一致
        else if (wechatTransaction.getAmount().compareTo(payOrder.getPaidAmount()) != 0) {
            payCheckMismatchDetail = PayCheckMismatchDetail.build(payOrder, wechatTransaction, PayCheckMismatchType.CHANNEL_STREAM_AMOUNT_NOT_EQUAL_PAY_ORDER_AMOUNT);
        }

        if (payCheckMismatchDetail != null) {
            boolean result = false;
            try {
                //幂等，这里用的是一个简单的幂等方案，基于数据库的唯一性索引来做的。
                result = payCheckMismatchDetailService.save(payCheckMismatchDetail);
            } catch (DuplicateKeyException e) {
                PayCheckMismatchDetail existPayCheckMismatchDetail = payCheckMismatchDetailService.getOne(new QueryWrapper<PayCheckMismatchDetail>().eq("pay_order_id", payOrder.getPayOrderId()));
                if (existPayCheckMismatchDetail != null) {
                    return;
                }
            }

            if (!result) {
                log.error("save payCheckMismatchDetail failed, pls human intervention : {}", payCheckMismatchDetail);
            }

        }
    }

    //从渠道账单获取成功订单，并核对支付订单表
    @XxlJob("payDetailCheckFromChannelJob")
    public ReturnT<String> payDetailCheckFromChannelJob() {

        //从xxl-job的配置获取时间参数
        Date billDate;
        if (StringUtils.isNotBlank(XxlJobHelper.getJobParam())) {
            SimpleDateFormat sdf = new SimpleDateFormat(COMMON_DATE_PATTERN);
            //默认查询上一日的账单
            try {
                billDate = sdf.parse(XxlJobHelper.getJobParam());
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        } else {
            billDate = DateUtils.addDays(new Date(), -1);
        }

        int currentPage = 1;


        QueryWrapper<WechatTransaction> wrapper = new QueryWrapper<>();
        wrapper.ge("date", DateUtils.truncate(billDate, Calendar.DATE));
        wrapper.lt("date", DateUtils.truncate(DateUtils.addDays(billDate, 1), Calendar.DATE));
        wrapper.eq("type", "SUCCESS");
        wrapper.orderBy(true, true, "gmt_create");

        Page<WechatTransaction> wechatTransactionPage = wechatTransactionService.page(new Page<>(currentPage, PAGE_SIZE), wrapper);

        wechatTransactionPage.getRecords().forEach(this::doExecute);

        while (wechatTransactionPage.getCurrent() < wechatTransactionPage.getPages()) {
            currentPage++;
            wechatTransactionPage = wechatTransactionService.page(new Page<>(currentPage, PAGE_SIZE), wrapper);
            wechatTransactionPage.getRecords().forEach(this::doExecute);
        }

        return ReturnT.SUCCESS;
    }

    public void doExecute(WechatTransaction wechatTransaction) {
        PayOrder payOrder = payOrderService.queryByOrderId(wechatTransaction.getMchOrderNo());

        PayCheckMismatchDetail payCheckMismatchDetail = null;

        //渠道侧有，我们没有
        if (payOrder == null) {
            payCheckMismatchDetail = PayCheckMismatchDetail.build(wechatTransaction);
        }

        //渠道成功了，但是我们的状态处于支付中
        else if (payOrder.getOrderState() == PayOrderState.PAYING) {
            //以渠道侧为准，把我们自己的单据状态做推进
            //失败也没关系，我们还有个PayDetailCheckJob也在不断做这个事儿。
            Thread.ofVirtual().start(() -> {
                PaySuccessEvent paySuccessEvent = new PaySuccessEvent();
                paySuccessEvent.setChannelStreamId(wechatTransaction.getWechatOrderNo());
                paySuccessEvent.setPaidAmount(wechatTransaction.getAmount());
                paySuccessEvent.setPayOrderId(wechatTransaction.getMchOrderNo());
                paySuccessEvent.setPaySucceedTime(wechatTransaction.getDate());
                paySuccessEvent.setPayChannel(payOrder.getPayChannel());
                payApplicationService.paySuccess(paySuccessEvent);
            });
        } else if (payOrder.getOrderState() != PayOrderState.PAID) {
            payCheckMismatchDetail = PayCheckMismatchDetail.build(payOrder, wechatTransaction, PayCheckMismatchType.PAY_ORDER_STATUS_NOT_SUCCESS);
        }

        //我们的金额和渠道侧不一致
        else if (wechatTransaction.getAmount().compareTo(payOrder.getPaidAmount()) != 0) {
            payCheckMismatchDetail = PayCheckMismatchDetail.build(payOrder, wechatTransaction, PayCheckMismatchType.CHANNEL_STREAM_AMOUNT_NOT_EQUAL_PAY_ORDER_AMOUNT);
        }

        if (payCheckMismatchDetail != null) {
            boolean result = false;
            try {
                result = payCheckMismatchDetailService.save(payCheckMismatchDetail);
            } catch (DuplicateKeyException e) {
                PayCheckMismatchDetail existPayCheckMismatchDetail = payCheckMismatchDetailService.getOne(new QueryWrapper<PayCheckMismatchDetail>().eq("channel_stream_id", wechatTransaction.getMchRefundOrderNo()));
                if (existPayCheckMismatchDetail != null) {
                    return;
                }
            }

            if (!result) {
                log.error("save payCheckMismatchDetail failed, pls human intervention : {}", payCheckMismatchDetail);
            }

        }
    }
}
