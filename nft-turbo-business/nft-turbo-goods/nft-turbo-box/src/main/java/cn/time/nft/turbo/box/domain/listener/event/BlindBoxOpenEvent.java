package cn.time.nft.turbo.box.domain.listener.event;

import org.springframework.context.ApplicationEvent;


//创建HeldCollection事件
public class BlindBoxOpenEvent extends ApplicationEvent {

    public BlindBoxOpenEvent(Long blindBoxItemId) {
        super(blindBoxItemId);
    }
}
