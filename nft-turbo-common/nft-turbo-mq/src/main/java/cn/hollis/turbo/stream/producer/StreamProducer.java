package cn.hollis.turbo.stream.producer;

import cn.hollis.turbo.stream.param.MessageBody;
import com.alibaba.fastjson.JSON;
import org.apache.rocketmq.common.message.MessageConst;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.messaging.support.MessageBuilder;

import java.util.UUID;

/**
 * @author hollis
 */
public class StreamProducer {

    private static Logger logger = LoggerFactory.getLogger(StreamProducer.class);

    public static final int DELAY_LEVEL_1_M = 5;

    public static final String ROCKET_MQ_MESSAGE_ID = "ROCKET_MQ_MESSAGE_ID";

    public static final String ROCKET_TAGS = "ROCKET_TAGS";

    public static final String ROCKET_MQ_TOPIC = "ROCKET_MQ_TOPIC";

    //SpringCloud Stream 的核心类，用于向消息中间件（如RocketMQ、Kafka等）发送消息
    @Autowired
    private StreamBridge streamBridge;

    // 发送消息
    //bingingName 绑定的
    //msg 创建的订单对象
    public boolean send(String bingingName, String tag, String msg) {
        // 构建消息对象
        MessageBody message = new MessageBody()
                .setIdentifier(UUID.randomUUID().toString())
                .setBody(msg);

        logger.info("send message : {} , {} , {}", bingingName, tag, JSON.toJSONString(message));
        boolean result = streamBridge.send(bingingName, MessageBuilder.withPayload(message).setHeader("TAGS", tag)
                .build());

        logger.info("send result : {} , {} , {}", bingingName, tag, result);
        return result;
    }

    /**
     * 发送延迟消息
     *
     * @param bingingName
     * @param tag
     * @param msg
     * @param delayLevel  RocketMQ支持18个级别的延迟时间，分别为1s、5s、10s、30s、1m、2m、3m、4m、5m、6m、7m、8m、9m、10m、20m、30m、1h、2h
     * @return
     */
    public boolean send(String bingingName, String tag, String msg, int delayLevel) {
        // 构建消息对象
        MessageBody message = new MessageBody()
                .setIdentifier(UUID.randomUUID().toString())
                .setBody(msg);
        logger.info("send message : {} , {} , {}", bingingName, tag, JSON.toJSONString(message));
        boolean result = streamBridge.send(bingingName, MessageBuilder.withPayload(message).setHeader("TAGS", tag).setHeader(MessageConst.PROPERTY_DELAY_TIME_LEVEL, delayLevel)
                .build());
        logger.info("send result : {} , {} , {}", bingingName, tag, result);
        return result;
    }

    public boolean send(String bingingName, String tag, String msg, String headerKey, String headerValue) {
        // 构建消息对象
        MessageBody message = new MessageBody()
                .setIdentifier(UUID.randomUUID().toString())
                .setBody(msg);
        logger.info("send message : {} , {}", bingingName, JSON.toJSONString(message));
        boolean result = streamBridge.send(bingingName, MessageBuilder.withPayload(message).setHeader("TAGS", tag).setHeader(headerKey, headerValue)
                .build());
        logger.info("send result : {} , {}", bingingName, result);
        return result;
    }
}
