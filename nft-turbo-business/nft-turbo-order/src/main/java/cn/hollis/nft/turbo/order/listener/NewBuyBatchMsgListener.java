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
import com.alibaba.fastjson2.JSON;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.apache.rocketmq.spring.core.RocketMQPushConsumerLifecycleListener;
import org.locationtech.jts.util.Assert;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.*;

import static cn.hollis.nft.turbo.api.order.constant.OrderErrorCode.ORDER_CREATE_VALID_FAILED;


//批量消费MQ的newBuy消息，在rocketmq.broker.check=true （stream.yml） 的时候会生效
//这个Bean和NewBuyMsgListener只启动一个。本Bean对RocketMQ的Brocker部署强依赖，即不部署会导致应用无法启动，
//如果你不部署MQ，想要运行本应用，则需要把rocketmq.broker.check改为false
@Component
@Slf4j
@RocketMQMessageListener(topic = "new-buy-topic", consumerGroup = "trade-group")
@ConditionalOnProperty(value = "rocketmq.broker.check", havingValue = "true")
public class NewBuyBatchMsgListener implements RocketMQListener<List<Object>>, RocketMQPushConsumerLifecycleListener {

    @Autowired
    private OrderFacadeService orderFacadeService;

    @Autowired
    private OrderReadService orderReadService;

    @Resource
    private InventoryFacadeService inventoryFacadeService;

    //创建线程池
    private final ExecutorService executor = Executors.newFixedThreadPool(16);

    @Override
    public void onMessage(List<Object> strings) {
        log.info("NewBuyBatchMsgListener receive message: {}", strings);
    }

    @Override
    public void prepareStart(DefaultMQPushConsumer consumer) {
        // 设置拉取间隔
        consumer.setPullInterval(1000);
        // 设置批量拉取数量
        consumer.setConsumeMessageBatchMaxSize(128);
        // 设置批量消费数量
        consumer.setPullBatchSize(64);
        // 设置消费模式
        // MessageListenerConcurrently 并发消费
        consumer.registerMessageListener((MessageListenerConcurrently) (msgs, context) -> {
            log.info("NewBuyBatchMsgListener receive message size: {}", msgs.size());

            // 使用 CompletionService 管理并发任务，方便获取执行结果
            CompletionService<Boolean> completionService = new ExecutorCompletionService<>(executor);
            List<Future<Boolean>> futures = new ArrayList<>();

            // 遍历每条消息，封装任务并提交
            msgs.forEach(messageExt -> {
                Callable<Boolean> task = () -> {
                    try {
                        //解析消息
                        OrderCreateRequest orderCreateRequest = JSON.parseObject(JSON.parseObject(messageExt.getBody()).getString("body"), OrderCreateRequest.class);
                        //执行任务
                        return doNewBuyExecute(orderCreateRequest);
                    } catch (Exception e) {
                        log.error("Task failed", e);
                        return false; // 标记失败
                    }
                };
                futures.add(completionService.submit(task));
            });

            //检查结果
            boolean allSuccess = true;
            try {
                for (int i = 0; i < msgs.size(); i++) {
                    Future<Boolean> future = completionService.take();
                    if (!future.get()) {
                        allSuccess = false;
                        break;
                    }
                }
            } catch (Exception e) {
                allSuccess = false;
            }

            //根据结果返回消费状态
            return allSuccess ? ConsumeConcurrentlyStatus.CONSUME_SUCCESS : ConsumeConcurrentlyStatus.RECONSUME_LATER;
        });
    }

    //执行实际的下单逻辑
    public boolean doNewBuyExecute(OrderCreateRequest orderCreateRequest) {
        //确认订单请求
        OrderCreateAndConfirmRequest orderCreateAndConfirmRequest = new OrderCreateAndConfirmRequest();
        BeanUtils.copyProperties(orderCreateRequest, orderCreateAndConfirmRequest);
        orderCreateAndConfirmRequest.setOperator(UserType.PLATFORM.name());
        orderCreateAndConfirmRequest.setOperatorType(UserType.PLATFORM);
        orderCreateAndConfirmRequest.setOperateTime(new Date());

        //确认订单
        OrderResponse orderResponse = orderFacadeService.createAndConfirm(orderCreateAndConfirmRequest);
        //订单因为校验前置校验不通过而下单失败，回滚库存
        if (!orderResponse.getSuccess() && ORDER_CREATE_VALID_FAILED.getCode().equals(orderResponse.getResponseCode())) {
            String orderId = orderResponse.getOrderId();
            //按照订单号查询订单信息
            TradeOrder tradeOrder = orderReadService.getOrder(orderId);
            //再重新查一次，避免出现并发情况
            if (tradeOrder == null) {
                InventoryRequest collectionInventoryRequest = new InventoryRequest();
                collectionInventoryRequest.setGoodsId(orderCreateRequest.getGoodsId());
                collectionInventoryRequest.setInventory(orderCreateRequest.getItemCount());
                collectionInventoryRequest.setIdentifier(orderCreateRequest.getOrderId());
                collectionInventoryRequest.setGoodsType(orderCreateRequest.getGoodsType());

                // 增加库存（调用库存服务执行回滚）
                SingleResponse<Boolean> decreaseResponse = inventoryFacadeService.increase(collectionInventoryRequest);
                if (decreaseResponse.getSuccess()) {
                    log.info("increase success,collectionInventoryRequest:{}", collectionInventoryRequest);
                    //库存回滚后提前返回
                    return true;
                } else {
                    log.error("increase inventory failed,orderCreateRequest:{} , decreaseResponse : {}", JSON.toJSONString(orderCreateRequest), JSON.toJSONString(decreaseResponse));
                    throw new OrderException(OrderErrorCode.INVENTORY_INCREASE_FAILED);
                }
            }
        }
        Assert.isTrue(orderResponse.getSuccess(), "create order failed ," + orderResponse.getResponseMessage());
        return true;
    }
}