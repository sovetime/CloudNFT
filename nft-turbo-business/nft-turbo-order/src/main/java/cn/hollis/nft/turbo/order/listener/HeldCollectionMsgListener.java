package cn.hollis.nft.turbo.order.listener;

import cn.hollis.nft.turbo.api.collection.constant.GoodsSaleBizType;
import cn.hollis.nft.turbo.api.collection.constant.HeldCollectionState;
import cn.hollis.nft.turbo.api.collection.model.HeldCollectionDTO;
import cn.hollis.nft.turbo.api.order.request.OrderFinishRequest;
import cn.hollis.nft.turbo.api.order.response.OrderResponse;
import cn.hollis.nft.turbo.api.user.constant.UserType;
import cn.hollis.nft.turbo.order.domain.service.OrderManageService;
import cn.hollis.turbo.stream.consumer.AbstractStreamConsumer;
import cn.hollis.turbo.stream.param.MessageBody;
import lombok.extern.slf4j.Slf4j;
import org.locationtech.jts.util.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.function.Consumer;

/**
 * @author Hollis
 */
@Component
@Slf4j
public class HeldCollectionMsgListener extends AbstractStreamConsumer {

    @Autowired
    private OrderManageService orderManageService;

    @Bean
    Consumer<Message<MessageBody>> heldCollection() {
        return msg -> {
            HeldCollectionDTO heldCollectionDTO = getMessage(msg, HeldCollectionDTO.class);

            if (heldCollectionDTO.getState().equals(HeldCollectionState.ACTIVED.name()) && !GoodsSaleBizType.AIR_DROP.name().equals(heldCollectionDTO.getBizType())) {
                String orderId = heldCollectionDTO.getBizNo();
                OrderFinishRequest orderFinishRequest = new OrderFinishRequest();
                orderFinishRequest.setIdentifier("order_confirm_" + heldCollectionDTO.getId());
                orderFinishRequest.setOrderId(orderId);
                orderFinishRequest.setOperator(UserType.PLATFORM.name());
                orderFinishRequest.setOperatorType(UserType.PLATFORM);
                orderFinishRequest.setOperateTime(new Date());
                OrderResponse orderResponse = orderManageService.finish(orderFinishRequest);
                Assert.isTrue(orderResponse.getSuccess(), "finish order failed");
            }

        };

    }
}
