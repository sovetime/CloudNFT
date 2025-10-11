package cn.time.nft.turbo.order.domain.listener.event;

import cn.time.nft.turbo.order.domain.entity.TradeOrder;
import org.springframework.context.ApplicationEvent;


public class OrderCreateEvent extends ApplicationEvent {

    public OrderCreateEvent(TradeOrder tradeOrder) {
        super(tradeOrder);
    }
}
