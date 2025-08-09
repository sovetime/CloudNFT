package cn.hollis.nft.turbo.trade.listener;

import cn.hollis.nft.turbo.api.goods.request.GoodsSaleRequest;
import cn.hollis.nft.turbo.api.goods.response.GoodsSaleResponse;
import cn.hollis.nft.turbo.api.goods.service.GoodsFacadeService;
import cn.hollis.nft.turbo.api.inventory.request.InventoryRequest;
import cn.hollis.nft.turbo.api.inventory.service.InventoryFacadeService;
import cn.hollis.nft.turbo.api.order.OrderFacadeService;
import cn.hollis.nft.turbo.api.order.constant.TradeOrderEvent;
import cn.hollis.nft.turbo.api.order.constant.TradeOrderState;
import cn.hollis.nft.turbo.api.order.model.TradeOrderVO;
import cn.hollis.nft.turbo.api.order.request.BaseOrderUpdateRequest;
import cn.hollis.nft.turbo.api.order.request.OrderCancelRequest;
import cn.hollis.nft.turbo.api.order.request.OrderTimeoutRequest;
import cn.hollis.nft.turbo.base.response.SingleResponse;
import cn.hollis.nft.turbo.trade.exception.TradeException;
import cn.hollis.turbo.stream.consumer.AbstractStreamConsumer;
import cn.hollis.turbo.stream.param.MessageBody;
import com.alibaba.fastjson2.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

import static cn.hollis.nft.turbo.trade.exception.TradeErrorCode.INVENTORY_ROLLBACK_FAILED;

/**
 * 交易订单监听器
 *
 * @author hollis
 */
@Slf4j
@Component
public class TradeOrderListener extends AbstractStreamConsumer {

    @Autowired
    private InventoryFacadeService inventoryFacadeService;

    @Autowired
    private GoodsFacadeService goodsFacadeService;

    @Autowired
    private OrderFacadeService orderFacadeService;

    @Bean
    Consumer<Message<MessageBody>> orderClose() {
        return msg -> {
            String closeType = msg.getHeaders().get("CLOSE_TYPE", String.class);
            BaseOrderUpdateRequest orderUpdateRequest;
            if (TradeOrderEvent.CANCEL.name().equals(closeType)) {
                orderUpdateRequest = getMessage(msg, OrderCancelRequest.class);
            } else if (TradeOrderEvent.TIME_OUT.name().equals(closeType)) {
                orderUpdateRequest = getMessage(msg, OrderTimeoutRequest.class);
            } else {
                throw new UnsupportedOperationException("unsupported closeType " + closeType);
            }

            SingleResponse<TradeOrderVO> response = orderFacadeService.getTradeOrder(orderUpdateRequest.getOrderId());
            if (!response.getSuccess()) {
                log.error("getTradeOrder failed,orderCloseRequest:{} , orderQueryResponse : {}", JSON.toJSONString(orderUpdateRequest), JSON.toJSONString(response));
                throw new TradeException(INVENTORY_ROLLBACK_FAILED);
            }
            TradeOrderVO tradeOrderVO = response.getData();
            if (response.getData().getOrderState() != TradeOrderState.CLOSED) {
                log.error("trade order state is illegal ,orderCloseRequest:{} , tradeOrderVO : {}", JSON.toJSONString(orderUpdateRequest), JSON.toJSONString(tradeOrderVO));
                throw new TradeException(INVENTORY_ROLLBACK_FAILED);
            }

            GoodsSaleRequest goodsSaleRequest = new GoodsSaleRequest(tradeOrderVO);
            GoodsSaleResponse cancelSaleResult = goodsFacadeService.cancelSale(goodsSaleRequest);
            if (!cancelSaleResult.getSuccess()) {
                log.error("cancelSale failed,orderCloseRequest:{} , collectionSaleResponse : {}", JSON.toJSONString(orderUpdateRequest), JSON.toJSONString(cancelSaleResult));
                throw new TradeException(INVENTORY_ROLLBACK_FAILED);
            }

            InventoryRequest collectionInventoryRequest = new InventoryRequest(tradeOrderVO);
            SingleResponse<Boolean> decreaseResponse = inventoryFacadeService.increase(collectionInventoryRequest);
            if (decreaseResponse.getSuccess()) {
                log.info("increase success,collectionInventoryRequest:{}", collectionInventoryRequest);
            } else {
                log.error("increase inventory failed,orderCloseRequest:{} , decreaseResponse : {}", JSON.toJSONString(orderUpdateRequest), JSON.toJSONString(decreaseResponse));
                throw new TradeException(INVENTORY_ROLLBACK_FAILED);
            }
        };
    }
}
