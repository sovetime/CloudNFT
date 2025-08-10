package cn.hollis.nft.turbo.order.listener;

import cn.hollis.nft.turbo.api.inventory.request.InventoryRequest;
import cn.hollis.nft.turbo.api.inventory.service.InventoryFacadeService;
import cn.hollis.nft.turbo.api.order.OrderFacadeService;
import cn.hollis.nft.turbo.api.order.constant.OrderErrorCode;
import cn.hollis.nft.turbo.api.order.request.OrderCreateAndConfirmRequest;
import cn.hollis.nft.turbo.api.order.request.OrderCreateRequest;
import cn.hollis.nft.turbo.api.order.response.OrderResponse;
import cn.hollis.nft.turbo.api.user.constant.UserType;
import cn.hollis.nft.turbo.base.response.SingleResponse;
import cn.hollis.nft.turbo.order.OrderException;
import cn.hollis.nft.turbo.order.domain.entity.TradeOrder;
import cn.hollis.nft.turbo.order.domain.service.OrderReadService;
import cn.hollis.turbo.stream.consumer.AbstractStreamConsumer;
import cn.hollis.turbo.stream.param.MessageBody;
import com.alibaba.fastjson2.JSON;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.function.Consumer;

import static cn.hollis.nft.turbo.api.order.constant.OrderErrorCode.ORDER_CREATE_VALID_FAILED;

//单条消费MQ的newBuy消息，在rocketmq.broker.check=fasle （stream.yml） 的时候会生效
//这个Bean和NewBuyBatchMsgListener只启动一个。
//本Bean对RocketMQ的Brocker部署不强依赖，即不部署也不到会导致应用无法启动，但是消息会无法发送和消费
@Component
@Slf4j
@ConditionalOnProperty(value = "rocketmq.broker.check", havingValue = "false", matchIfMissing = true)
public class NewBuyMsgListener extends AbstractStreamConsumer {

    @Autowired
    private OrderFacadeService orderFacadeService;

    @Autowired
    private OrderReadService orderReadService;

    @Resource
    private InventoryFacadeService inventoryFacadeService;

    @Bean
    Consumer<Message<MessageBody>> newBuy() {
        return msg -> {
            OrderCreateRequest orderCreateRequest = getMessage(msg, OrderCreateRequest.class);
            doNewBuyExecute(orderCreateRequest);
        };
    }

    public void doNewBuyExecute(OrderCreateRequest orderCreateRequest) {
        OrderCreateAndConfirmRequest orderCreateAndConfirmRequest = new OrderCreateAndConfirmRequest();
        BeanUtils.copyProperties(orderCreateRequest, orderCreateAndConfirmRequest);
        orderCreateAndConfirmRequest.setOperator(UserType.PLATFORM.name());
        orderCreateAndConfirmRequest.setOperatorType(UserType.PLATFORM);
        orderCreateAndConfirmRequest.setOperateTime(new Date());

        OrderResponse orderResponse = orderFacadeService.createAndConfirm(orderCreateAndConfirmRequest);
        //订单因为校验前置校验不通过而下单失败，回滚库存
        if (!orderResponse.getSuccess() && ORDER_CREATE_VALID_FAILED.getCode().equals(orderResponse.getResponseCode())) {
            String orderId = orderResponse.getOrderId();
            TradeOrder tradeOrder = orderReadService.getOrder(orderId);
            //再重新查一次，避免出现并发情况
            if (tradeOrder == null) {
                InventoryRequest collectionInventoryRequest = new InventoryRequest();
                collectionInventoryRequest.setGoodsId(orderCreateRequest.getGoodsId());
                collectionInventoryRequest.setInventory(orderCreateRequest.getItemCount());
                collectionInventoryRequest.setIdentifier(orderCreateRequest.getOrderId());
                collectionInventoryRequest.setGoodsType(orderCreateRequest.getGoodsType());
                SingleResponse<Boolean> decreaseResponse = inventoryFacadeService.increase(collectionInventoryRequest);
                if (decreaseResponse.getSuccess()) {
                    log.info("increase success,collectionInventoryRequest:{}", collectionInventoryRequest);
                    //库存回滚后提前返回
                    return;
                } else {
                    log.error("increase inventory failed,orderCreateRequest:{} , decreaseResponse : {}", JSON.toJSONString(orderCreateRequest), JSON.toJSONString(decreaseResponse));
                    throw new OrderException(OrderErrorCode.INVENTORY_INCREASE_FAILED);
                }
            }
        }
    }
}
