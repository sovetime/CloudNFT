package cn.hollis.nft.turbo.order.domain.listener.event;

import cn.hollis.nft.turbo.order.domain.entity.TradeOrder;
import org.springframework.context.ApplicationEvent;

/**
 * @author Hollis
 */
public class OrderCreateEvent extends ApplicationEvent {

    public OrderCreateEvent(TradeOrder tradeOrder) {
        super(tradeOrder);
    }
}
