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

    // @Async("orderListenExecutor") 移除异步处理，本事件改为同步处理
    // 因为在后面的压测中发现，异步处理会导致整体的订单CONFIRM延迟变长，影响用户体验，所以改为同步调用的方式，详见压测部分视频。
    @TransactionalEventListener(value = OrderCreateEvent.class)
    //@Async("orderListenExecutor")
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
