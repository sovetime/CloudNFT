package cn.hollis.nft.turbo.box.domain.listener.event;

import cn.hollis.nft.turbo.box.domain.entity.BlindBoxItem;
import org.springframework.context.ApplicationEvent;

/**
 * 创建HeldCollection事件
 *
 * @author Hollis
 */
public class BlindBoxOpenEvent extends ApplicationEvent {

    public BlindBoxOpenEvent(Long blindBoxItemId) {
        super(blindBoxItemId);
    }
}
