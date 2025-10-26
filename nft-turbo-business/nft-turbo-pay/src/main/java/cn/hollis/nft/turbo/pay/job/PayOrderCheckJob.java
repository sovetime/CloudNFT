package cn.hollis.nft.turbo.pay.job;

import cn.hollis.nft.turbo.base.utils.MoneyUtils;
import cn.hollis.nft.turbo.pay.application.service.PayApplicationService;
import cn.hollis.nft.turbo.pay.domain.entity.PayOrder;
import cn.hollis.nft.turbo.pay.domain.event.PaySuccessEvent;
import cn.hollis.nft.turbo.pay.domain.service.PayOrderService;
import cn.hollis.nft.turbo.pay.infrastructure.channel.common.response.PayResultQueryResponse;
import cn.hollis.nft.turbo.pay.infrastructure.channel.common.service.PayChannelServiceFactory;
import cn.hollis.nft.turbo.pay.infrastructure.channel.wechat.constant.WxTradeState;
import cn.hollis.nft.turbo.pay.infrastructure.channel.wechat.entity.WxPayNotifyEntity;
import cn.hutool.core.date.DateUtil;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.annotation.XxlJob;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * @author Hollis
 */
@Component
public class PayOrderCheckJob {

    @Autowired
    private PayOrderService payOrderService;

    @Autowired
    private PayChannelServiceFactory payChannelServiceFactory;

    @Autowired
    private PayApplicationService payApplicationService;

    private static final int PAGE_SIZE = 100;

    @XxlJob("payQueryResultExecute")
    public ReturnT<String> execute() {

        List<PayOrder> payOrders = payOrderService.pageQueryPayingOrders(PAGE_SIZE, null);

        payOrders.stream()
                .filter(payOrder -> payOrder.getGmtModified().compareTo(DateUtils.addSeconds(new Date(), -3)) < 0)
                .forEach(this::executeSingle);

        while (CollectionUtils.isNotEmpty(payOrders)) {
            Long maxId = payOrders.stream().mapToLong(PayOrder::getId).max().orElse(Long.MAX_VALUE);
            payOrders = payOrderService.pageQueryPayingOrders(PAGE_SIZE, maxId + 1);
            payOrders.stream()
                    .filter(payOrder -> payOrder.getGmtModified().compareTo(DateUtils.addSeconds(new Date(), -3)) < 0)
                    .forEach(this::executeSingle);
        }

        return ReturnT.SUCCESS;
    }

    private void executeSingle(PayOrder payOrder) {
        //调微信支付的查询接口，检查是否已经成功。
        PayResultQueryResponse response = payChannelServiceFactory.get(payOrder.getPayChannel()).payResultQuery(payOrder.getPayOrderId());
        WxPayNotifyEntity wxPayNotifyEntity = response.getWxPayNotifyEntity();
        if (response.getSuccess() && WxTradeState.SUCCESS.name().equals(response.getWxPayNotifyEntity().getTradeState())) {
            PaySuccessEvent paySuccessEvent = new PaySuccessEvent();
            paySuccessEvent.setChannelStreamId(wxPayNotifyEntity.getTransactionId());
            paySuccessEvent.setPaidAmount(MoneyUtils.centToYuan(Long.valueOf(wxPayNotifyEntity.getAmount().getTotal())));
            paySuccessEvent.setPayOrderId(wxPayNotifyEntity.getOutTradeNo());
            paySuccessEvent.setPaySucceedTime(DateUtil.parseUTC(wxPayNotifyEntity.getSuccessTime()));
            paySuccessEvent.setPayChannel(payOrder.getPayChannel());
            payApplicationService.paySuccess(paySuccessEvent);
        }
    }
}
