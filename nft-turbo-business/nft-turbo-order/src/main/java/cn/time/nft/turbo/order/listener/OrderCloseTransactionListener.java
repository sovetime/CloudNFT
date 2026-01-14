package cn.time.nft.turbo.order.listener;

import cn.time.nft.turbo.api.order.constant.TradeOrderEvent;
import cn.time.nft.turbo.api.order.constant.TradeOrderState;
import cn.time.nft.turbo.api.order.request.BaseOrderUpdateRequest;
import cn.time.nft.turbo.api.order.request.OrderCancelRequest;
import cn.time.nft.turbo.api.order.request.OrderTimeoutRequest;
import cn.time.nft.turbo.api.order.response.OrderResponse;
import cn.time.nft.turbo.order.domain.entity.TradeOrder;
import cn.time.nft.turbo.order.domain.service.OrderManageService;
import cn.time.nft.turbo.order.domain.service.OrderReadService;
import com.alibaba.fastjson2.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.LocalTransactionState;
import org.apache.rocketmq.client.producer.TransactionListener;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;


@Component
@Slf4j
public class OrderCloseTransactionListener implements TransactionListener {

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
                log.info("executeLocalTransaction , baseOrderUpdateRequest = {} , closeType = {}", JSON.toJSONString(cancelRequest), closeType);
                response = orderManageService.cancel(cancelRequest);
            } else if (TradeOrderEvent.TIME_OUT.name().equals(closeType)) {
                OrderTimeoutRequest timeoutRequest = JSON.parseObject(JSON.parseObject(message.getBody()).getString("body"), OrderTimeoutRequest.class);
                log.info("executeLocalTransaction , baseOrderUpdateRequest = {} , closeType = {}", JSON.toJSONString(timeoutRequest), closeType);
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
            log.error("executeLocalTransaction error, message = {}", message, e);
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
