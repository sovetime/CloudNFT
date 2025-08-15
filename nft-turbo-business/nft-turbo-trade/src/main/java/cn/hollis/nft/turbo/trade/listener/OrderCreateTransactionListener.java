package cn.hollis.nft.turbo.trade.listener;

import cn.hollis.nft.turbo.api.order.OrderFacadeService;
import cn.hollis.nft.turbo.api.order.constant.TradeOrderState;
import cn.hollis.nft.turbo.api.order.model.TradeOrderVO;
import cn.hollis.nft.turbo.api.order.request.OrderCreateAndConfirmRequest;
import cn.hollis.nft.turbo.api.order.response.OrderResponse;
import cn.hollis.nft.turbo.base.response.SingleResponse;
import cn.hollis.nft.turbo.trade.application.TradeApplicationService;
import com.alibaba.fastjson2.JSON;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.LocalTransactionState;
import org.apache.rocketmq.client.producer.TransactionListener;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
@Slf4j
public class OrderCreateTransactionListener implements TransactionListener {

    @Autowired
    private TradeApplicationService tradeApplicationService;

    @Resource
    private OrderFacadeService orderFacadeService;

    @Override
    public LocalTransactionState executeLocalTransaction(Message message, Object o) {
        try {
            OrderCreateAndConfirmRequest orderCreateAndConfirmRequest = JSON.parseObject(JSON.parseObject(message.getBody()).getString("body"), OrderCreateAndConfirmRequest.class);
            //此处废弃了TCC的方案，主要是这个方案上会多次访问数据库，占用很多IO，导致CPU飙高的问题，所以改用非TCC方案，详见压测部分视频
            //tradeApplicationService.newBuyPlusByTcc(orderCreateAndConfirmRequest);
            //为了避免在创建订单的时候，confirm假失败（比如网络超时），导致库存不扣减的问题，这里需要查询最新的状态决定是否要发消息
            //但是这里还是有可能出现因为网络延迟或者数据库异常而导致查询到的订单状态不是CONFIRM，但是后来又变成了CONFIRM的情况，所以需要做补偿，详见NewBuyPlusMsgListener.newBuyPlusPreCancel
            //SingleResponse<TradeOrderVO> response = orderFacadeService.getTradeOrder(orderCreateAndConfirmRequest.getOrderId());
            //如果订单已经创建成功，则直接返回。不再需要做废单处理了。
            //if (response.getSuccess() && response.getData() != null && response.getData().getOrderState() == TradeOrderState.CONFIRM) {
            //     return LocalTransactionState.COMMIT_MESSAGE;
            //}

            OrderResponse orderResponse = tradeApplicationService.newBuyPlus(orderCreateAndConfirmRequest);
            return orderResponse.getSuccess() ? LocalTransactionState.COMMIT_MESSAGE : LocalTransactionState.ROLLBACK_MESSAGE;
        } catch (Exception e) {
            log.error("executeLocalTransaction error, message = {}", message, e);
            return LocalTransactionState.ROLLBACK_MESSAGE;
        }
    }

    @Override
    public LocalTransactionState checkLocalTransaction(MessageExt messageExt) {
        OrderCreateAndConfirmRequest orderCreateAndConfirmRequest = JSON.parseObject(JSON.parseObject(new String(messageExt.getBody())).getString("body"), OrderCreateAndConfirmRequest.class);

        SingleResponse<TradeOrderVO> response = orderFacadeService.getTradeOrder(orderCreateAndConfirmRequest.getOrderId());

        //如果订单已经创建成功，则直接返回。不再需要做废单处理了。
        if (response.getSuccess() && response.getData() != null && response.getData().getOrderState() == TradeOrderState.CONFIRM) {
            return LocalTransactionState.COMMIT_MESSAGE;
        }

        return LocalTransactionState.ROLLBACK_MESSAGE;
    }
}
