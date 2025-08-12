package cn.hollis.nft.turbo.order.domain.listener;

import cn.hollis.nft.turbo.api.order.OrderFacadeService;
import cn.hollis.nft.turbo.api.order.request.OrderConfirmRequest;
import cn.hollis.nft.turbo.api.user.constant.UserType;
import cn.hollis.nft.turbo.order.domain.entity.TradeOrder;
import cn.hollis.nft.turbo.order.domain.listener.event.OrderCreateEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.Date;


@Component
public class OrderEventListener {

    @Autowired
    private OrderFacadeService orderFacadeService;

    @TransactionalEventListener(value = OrderCreateEvent.class)
    @Async("orderListenExecutor")
    public void onApplicationEvent(OrderCreateEvent event) {

        TradeOrder tradeOrder = (TradeOrder) event.getSource();
        OrderConfirmRequest confirmRequest = new OrderConfirmRequest();
        confirmRequest.setOperator(UserType.PLATFORM.name());
        confirmRequest.setOperatorType(UserType.PLATFORM);
        confirmRequest.setOrderId(tradeOrder.getOrderId());
        confirmRequest.setIdentifier(tradeOrder.getIdentifier());
        confirmRequest.setOperateTime(new Date());
        confirmRequest.setBuyerId(tradeOrder.getBuyerId());
        confirmRequest.setItemCount(tradeOrder.getItemCount());
        confirmRequest.setGoodsType(tradeOrder.getGoodsType());
        confirmRequest.setGoodsId(tradeOrder.getGoodsId());
        orderFacadeService.confirm(confirmRequest);
    }
}
