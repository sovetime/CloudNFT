package cn.hollis.nft.turbo.trade.listener;

import cn.hollis.nft.turbo.api.inventory.request.InventoryRequest;
import cn.hollis.nft.turbo.api.inventory.service.InventoryFacadeService;
import cn.hollis.nft.turbo.api.order.request.OrderCreateRequest;
import cn.hollis.nft.turbo.base.response.SingleResponse;
import com.alibaba.fastjson2.JSON;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.LocalTransactionState;
import org.apache.rocketmq.client.producer.TransactionListener;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
@Slf4j
//库存扣减事务监听器
//用于 RocketMQ 事务消息中，处理库存预扣减的本地事务逻辑及事务状态回查
public class InventoryDecreaseTransactionListener implements TransactionListener {

    @Resource
    private InventoryFacadeService inventoryFacadeService;

    //执行本地事务
    //该方法会在事务消息发送到 Broker 但没有推送给消费者时被调用，这里是库存预扣减
    @Override
    public LocalTransactionState executeLocalTransaction(Message message, Object o) {
        try {
            // 从消息中解析订单请求
            OrderCreateRequest orderCreateRequest = JSON.parseObject(JSON.parseObject(message.getBody()).getString("body"), OrderCreateRequest.class);
            InventoryRequest inventoryRequest = new InventoryRequest(orderCreateRequest);
            //redis中库存预扣减
            SingleResponse<Boolean> response = inventoryFacadeService.decrease(inventoryRequest);

            if (response.getSuccess() && response.getData()) {
                return LocalTransactionState.COMMIT_MESSAGE;
            } else {
                return LocalTransactionState.ROLLBACK_MESSAGE;
            }
        } catch (Exception e) {
            log.error("executeLocalTransaction error, message = {}", message, e);
            return LocalTransactionState.ROLLBACK_MESSAGE;
        }
    }

    //事务回查方法，用于判断本地事务是否已经执行成功。
    //当 RocketMQ Broker 没有收到事务提交/回滚结果时，会回调此方法进行事务状态检查
    @Override
    public LocalTransactionState checkLocalTransaction(MessageExt messageExt) {
        // 从消息中解析订单请求
        OrderCreateRequest orderCreateRequest = JSON.parseObject(
                JSON.parseObject(new String(messageExt.getBody())).getString("body"),
                OrderCreateRequest.class);

        SingleResponse<String> response;

        // 构造库存请求
        InventoryRequest inventoryRequest = new InventoryRequest(orderCreateRequest);
        // 查询库存扣减日志，判断是否执行过扣减操作
        response = inventoryFacadeService.getInventoryDecreaseLog(inventoryRequest);
        // 如果日志存在，则提交事务；否则回滚事务
        return response.getSuccess() && response.getData() != null ?
                LocalTransactionState.COMMIT_MESSAGE : LocalTransactionState.ROLLBACK_MESSAGE;
    }
}
