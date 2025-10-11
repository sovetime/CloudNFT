package cn.time.nft.turbo.order.domain.listener.event;

import cn.time.nft.turbo.api.order.request.BaseOrderRequest;
import org.springframework.context.ApplicationEvent;


public class OrderTimeoutEvent extends ApplicationEvent {

    public OrderTimeoutEvent(BaseOrderRequest baseOrderRequest) {
        super(baseOrderRequest);
    }
}
