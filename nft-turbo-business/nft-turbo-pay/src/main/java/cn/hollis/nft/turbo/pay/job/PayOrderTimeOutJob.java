package cn.hollis.nft.turbo.pay.job;

import cn.hollis.nft.turbo.pay.domain.entity.PayOrder;
import cn.hollis.nft.turbo.pay.domain.service.PayOrderService;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.annotation.XxlJob;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Hollis
 */
@Component
public class PayOrderTimeOutJob {

    @Autowired
    private PayOrderService payOrderService;

    private static final int PAGE_SIZE = 100;

    private static final Logger LOG = LoggerFactory.getLogger(PayOrderTimeOutJob.class);

    @XxlJob("payTimeOutExecute")
    public ReturnT<String> execute() {

        List<PayOrder> payOrders = payOrderService.pageQueryTimeoutOrders(PAGE_SIZE, null);

        payOrders.forEach(this::executeSingle);

        while (CollectionUtils.isNotEmpty(payOrders)) {
            Long maxId = payOrders.stream().mapToLong(PayOrder::getId).max().orElse(Long.MAX_VALUE);
            payOrders = payOrderService.pageQueryTimeoutOrders(PAGE_SIZE, maxId + 1);
            payOrders.forEach(this::executeSingle);
        }

        return ReturnT.SUCCESS;
    }

    private void executeSingle(PayOrder payOrder) {
        LOG.info("start to execute order timeout , orderId is {}", payOrder.getPayOrderId());
        //这里如果做的更好一点，需要避免并发的情况，即渠道刚支付成功，还没来得及通知，这里就关单了，会导致用户付款了成功后但是订单没有了。会引起客诉。解决方案有以下2个：
        //1、给支付渠道的超时时间要比本系统的超时时间更短，让渠道先超时，然后本系统在超时的时候去反查一下渠道状态，如果渠道已经支付成功，则不处理，如果渠道超时，则处理。
        //2、在超时关单处理时，先去调用渠道的超时关单接口，确保渠道侧先关单成功（渠道内部会自己保障关单和支付的并发）后再执行本系统的关单操作。
        payOrderService.payExpired(payOrder.getPayOrderId());
    }
}
