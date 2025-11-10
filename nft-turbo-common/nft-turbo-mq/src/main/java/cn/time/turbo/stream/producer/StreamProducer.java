package cn.time.turbo.stream.producer;

import cn.time.turbo.stream.param.MessageBody;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.common.message.MessageConst;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.messaging.support.MessageBuilder;

import java.util.UUID;

// 消息发送类
@Slf4j
public class StreamProducer {

    //对应RocketMQ内部延迟级别
    public static final int DELAY_LEVEL_30_S = 4;

    public static final int DELAY_LEVEL_1_M = 5;

    public static final String ROCKET_MQ_MESSAGE_ID = "ROCKET_MQ_MESSAGE_ID";

    public static final String ROCKET_TAGS = "ROCKET_TAGS";

    public static final String ROCKET_MQ_TOPIC = "ROCKET_MQ_TOPIC";

    //SpringCloud Stream 的核心类，用于向消息中间件（如RocketMQ、Kafka等）发送消息
    @Autowired
    private StreamBridge streamBridge;

    //发送消息
    public boolean send(String bingingName, String tag, String msg) {
        // 构建消息对象
        MessageBody message = new MessageBody()
                //消息唯一标识符，进行幂等控制
                .setIdentifier(UUID.randomUUID().toString())
                .setBody(msg);

        log.info("send message : {} , {} , {}", bingingName, tag, JSON.toJSONString(message));

        boolean result = streamBridge.send(bingingName, MessageBuilder.withPayload(message)
                .setHeader("TAGS", tag)
                .build());

        log.info("send result : {} , {} , {}", bingingName, tag, result);
        return result;
    }

    //发送延迟消息
    public boolean send(String bingingName, String tag, String msg, int delayLevel) {
        // 构建消息对象
        MessageBody message = new MessageBody()
                //消息唯一标识符，进行幂等控制
                .setIdentifier(UUID.randomUUID().toString())
                .setBody(msg);

        log.info("send message : {} , {} , {}", bingingName, tag, JSON.toJSONString(message));

        boolean result = streamBridge.send(bingingName, MessageBuilder.withPayload(message)
                .setHeader("TAGS", tag)
                .setHeader(MessageConst.PROPERTY_DELAY_TIME_LEVEL, delayLevel)
                .build());

        log.info("send result : {} , {} , {}", bingingName, tag, result);

        return result;
    }

    //发送延迟消息
    public boolean send(String bingingName, String tag, String msg, String headerKey, String headerValue) {
        // 构建消息对象
        MessageBody message = new MessageBody()
                .setIdentifier(UUID.randomUUID().toString())
                .setBody(msg);

        log.info("send message : {} , {}", bingingName, JSON.toJSONString(message));

        boolean result = streamBridge.send(bingingName, MessageBuilder.withPayload(message)
                .setHeader("TAGS", tag)
                .setHeader(headerKey, headerValue)
                .build());

        log.info("send result : {} , {}", bingingName, result);
        return result;
    }

}