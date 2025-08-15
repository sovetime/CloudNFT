package cn.hollis.nft.turbo.goods.listener;

import cn.hollis.nft.turbo.api.goods.request.GoodsSaleRequest;
import cn.hollis.nft.turbo.api.goods.response.GoodsSaleResponse;
import cn.hollis.nft.turbo.api.goods.service.GoodsFacadeService;
import cn.hollis.nft.turbo.api.order.request.OrderCreateAndConfirmRequest;
import cn.hollis.nft.turbo.api.order.request.OrderCreateRequest;
import cn.hollis.nft.turbo.api.user.constant.UserType;
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

/**
 * @author 批量消费MQ的newBuy消息，在rocketmq.broker.check=true （stream.yml） 的时候会生效
 * 这个Bean和NewBuyMsgListener只启动一个。本Bean对RocketMQ的Brocker部署强依赖，即不部署会导致应用无法启动，
 * 如果你不部署MQ，想要运行本应用，则需要把rocketmq.broker.check改为false
 */
@Component
@Slf4j
@RocketMQMessageListener(topic = "new-buy-plus-topic", consumerGroup = "new-buy-plus-group")
@ConditionalOnProperty(value = "rocketmq.broker.check", havingValue = "true")
public class NewBuyPlusBatchMsgListener implements RocketMQListener<List<Object>>, RocketMQPushConsumerLifecycleListener {

    @Autowired
    private GoodsFacadeService goodsFacadeService;

    @Resource
    private ThreadPoolExecutor newBuyPlusConsumePool;


    @Override
    public void onMessage(List<Object> strings) {
        log.info("NewBuyPlusBatchMsgListener receive message: {}", strings);
    }

    @Override
    public void prepareStart(DefaultMQPushConsumer consumer) {
        consumer.setPullInterval(500);
        consumer.setConsumeMessageBatchMaxSize(64);
        consumer.setPullBatchSize(64);
        consumer.registerMessageListener((MessageListenerConcurrently) (msgs, context) -> {
            log.warn("NewBuyPlusBatchMsgListener receive message size: {}", msgs.size());

            CompletionService<Boolean> completionService = new ExecutorCompletionService<>(newBuyPlusConsumePool);
            List<Future<Boolean>> futures = new ArrayList<>();

            // 1. 提交所有任务
            msgs.forEach(messageExt -> {
                Callable<Boolean> task = () -> {
                    try {
                        OrderCreateRequest orderCreateRequest = JSON.parseObject(JSON.parseObject(messageExt.getBody()).getString("body"), OrderCreateRequest.class);
                        return doNewBuyPlusExecute(orderCreateRequest);
                    } catch (Exception e) {
                        log.error("Task failed", e);
                        return false; // 标记失败
                    }
                };
                futures.add(completionService.submit(task));
            });

            // 2. 检查结果
            boolean allSuccess = true;
            try {
                for (int i = 0; i < msgs.size(); i++) {
                    Future<Boolean> future = completionService.take();
                    if (!future.get()) { // 3.发现一个失败立即终止
                        allSuccess = false;
                        break;
                    }
                }
            } catch (Exception e) {
                allSuccess = false;
            }

            // 3. 根据结果返回消费状态
            return allSuccess ? ConsumeConcurrentlyStatus.CONSUME_SUCCESS
                    : ConsumeConcurrentlyStatus.RECONSUME_LATER;
        });
    }

    public boolean doNewBuyPlusExecute(OrderCreateRequest orderCreateRequest) {
        OrderCreateAndConfirmRequest orderCreateAndConfirmRequest = new OrderCreateAndConfirmRequest();
        BeanUtils.copyProperties(orderCreateRequest, orderCreateAndConfirmRequest);
        orderCreateAndConfirmRequest.setOperator(UserType.PLATFORM.name());
        orderCreateAndConfirmRequest.setOperatorType(UserType.PLATFORM);
        orderCreateAndConfirmRequest.setOperateTime(new Date());
        GoodsSaleRequest goodsSaleRequest = new GoodsSaleRequest(orderCreateAndConfirmRequest);
        GoodsSaleResponse response = goodsFacadeService.saleWithoutHint(goodsSaleRequest);
        Assert.isTrue(response.getSuccess(), "saleWithoutHint failed ," + response.getResponseMessage());
        return true;
    }
}