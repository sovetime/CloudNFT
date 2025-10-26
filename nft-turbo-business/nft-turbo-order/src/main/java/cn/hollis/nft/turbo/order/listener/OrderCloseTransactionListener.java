package cn.hollis.nft.turbo.order.listener;

import cn.hollis.nft.turbo.api.order.constant.TradeOrderEvent;
import cn.hollis.nft.turbo.api.order.constant.TradeOrderState;
import cn.hollis.nft.turbo.api.order.request.BaseOrderUpdateRequest;
import cn.hollis.nft.turbo.api.order.request.OrderCancelRequest;
import cn.hollis.nft.turbo.api.order.request.OrderTimeoutRequest;
import cn.hollis.nft.turbo.api.order.response.OrderResponse;
import cn.hollis.nft.turbo.order.domain.entity.TradeOrder;
import cn.hollis.nft.turbo.order.domain.service.OrderManageService;
import cn.hollis.nft.turbo.order.domain.service.OrderReadService;
import com.alibaba.fastjson2.JSON;
import org.apache.rocketmq.client.producer.LocalTransactionState;
import org.apache.rocketmq.client.producer.TransactionListener;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;


@Component
public class OrderCloseTransactionListener implements TransactionListener {

    private static final Logger logger = LoggerFactory.getLogger(OrderCloseTransactionListener.class);

    @Autowired
    private OrderManageService orderManageService;

    @Autowired
    private OrderReadService orderReadService;

    @Override
    public LocalTransactionState executeLocalTransaction(Message message, Object o) {
        try {
            Map<String, String> headers = message.getProperties();
            String closeType = headers.get("CLOSE_TYPE");

            OrderResponse response = null;
            if (TradeOrderEvent.CANCEL.name().equals(closeType)) {
                OrderCancelRequest cancelRequest = JSON.parseObject(JSON.parseObject(message.getBody()).getString("body"), OrderCancelRequest.class);
                logger.info("executeLocalTransaction , baseOrderUpdateRequest = {} , closeType = {}", JSON.toJSONString(cancelRequest), closeType);
                response = orderManageService.cancel(cancelRequest);
            } else if (TradeOrderEvent.TIME_OUT.name().equals(closeType)) {
                OrderTimeoutRequest timeoutRequest = JSON.parseObject(JSON.parseObject(message.getBody()).getString("body"), OrderTimeoutRequest.class);
                logger.info("executeLocalTransaction , baseOrderUpdateRequest = {} , closeType = {}", JSON.toJSONString(timeoutRequest), closeType);
                response = orderManageService.timeout(timeoutRequest);
            } else {
                throw new UnsupportedOperationException("unsupported closeType " + closeType);
            }

            if (response.getSuccess()) {
                return LocalTransactionState.COMMIT_MESSAGE;
            } else {
                return LocalTransactionState.ROLLBACK_MESSAGE;
            }
        } catch (Exception e) {
            logger.error("executeLocalTransaction error, message = {}", message, e);
            return LocalTransactionState.ROLLBACK_MESSAGE;
        }
    }

    @Override
    public LocalTransactionState checkLocalTransaction(MessageExt messageExt) {
        String closeType = messageExt.getProperties().get("CLOSE_TYPE");
        BaseOrderUpdateRequest baseOrderUpdateRequest = null;
        if (TradeOrderEvent.CANCEL.name().equals(closeType)) {
            baseOrderUpdateRequest = JSON.parseObject(JSON.parseObject(new String(messageExt.getBody())).getString("body"), OrderCancelRequest.class);
        } else if (TradeOrderEvent.TIME_OUT.name().equals(closeType)) {
            baseOrderUpdateRequest = JSON.parseObject(JSON.parseObject(new String(messageExt.getBody())).getString("body"), OrderTimeoutRequest.class);
        }

        TradeOrder tradeOrder = orderReadService.getOrder(baseOrderUpdateRequest.getOrderId());

        if (tradeOrder.getOrderState() == TradeOrderState.CLOSED) {
            return LocalTransactionState.COMMIT_MESSAGE;
        }

        return LocalTransactionState.ROLLBACK_MESSAGE;
    }
}
