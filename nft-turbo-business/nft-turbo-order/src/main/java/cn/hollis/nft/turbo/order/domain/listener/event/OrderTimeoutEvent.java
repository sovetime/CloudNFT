package cn.hollis.nft.turbo.order.domain.listener.event;

import cn.hollis.nft.turbo.api.order.request.BaseOrderRequest;
import org.springframework.context.ApplicationEvent;

/**
 * @author Hollis
 */
public class OrderTimeoutEvent extends ApplicationEvent {

    public OrderTimeoutEvent(BaseOrderRequest baseOrderRequest) {
        super(baseOrderRequest);
    }
}
