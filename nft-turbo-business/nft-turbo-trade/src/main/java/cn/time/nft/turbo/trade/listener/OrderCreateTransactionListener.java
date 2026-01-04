package cn.time.nft.turbo.trade.listener;

import cn.time.nft.turbo.api.order.OrderFacadeService;
import cn.time.nft.turbo.api.order.constant.TradeOrderState;
import cn.time.nft.turbo.api.order.model.TradeOrderVO;
import cn.time.nft.turbo.api.order.request.OrderCreateAndConfirmRequest;
import cn.time.nft.turbo.api.order.response.OrderResponse;
import cn.time.nft.turbo.base.response.SingleResponse;
import cn.time.nft.turbo.trade.application.TradeApplicationService;
import com.alibaba.fastjson2.JSON;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.LocalTransactionState;
import org.apache.rocketmq.client.producer.TransactionListener;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
@Slf4j
public class OrderCreateTransactionListener implements TransactionListener {

    @Autowired
    private TradeApplicationService tradeApplicationService;

    @Resource
    private OrderFacadeService orderFacadeService;

    //TCC方案常量，默认不开启
    private final static boolean TCC_ENABLED = false;

    //执行本地事务
    @Override
    public LocalTransactionState executeLocalTransaction(Message message, Object o) {
        try {
            //从消息中解析订单请求
            OrderCreateAndConfirmRequest orderCreateAndConfirmRequest = JSON.parseObject(
                    JSON.parseObject(message.getBody()).getString("body"), OrderCreateAndConfirmRequest.class);

            //TCC会导致多次数据库IO，导致CPU飙高，这里没开启
            if (TCC_ENABLED) {
                //TCC方案下单,进行库存扣减try，订单创建，库存扣减confirm，订单确认
                OrderResponse orderResponse = tradeApplicationService.newBuyPlusByTcc(orderCreateAndConfirmRequest);
                if (!orderResponse.getSuccess()) {
                    return LocalTransactionState.ROLLBACK_MESSAGE;
                }

                //为了避免在创建订单的时候，confirm假失败（网络超时），导致库存不扣减的问题，这里获取订单详情
                SingleResponse<TradeOrderVO> response = orderFacadeService.getTradeOrder(orderCreateAndConfirmRequest.getOrderId());
                //如果订单已经创建成功，不需要在做后续处理
                if (response.getSuccess() && response.getData() != null && response.getData().getOrderState() == TradeOrderState.CONFIRM) {
                    return LocalTransactionState.COMMIT_MESSAGE;
                }

                return LocalTransactionState.ROLLBACK_MESSAGE;
            } else {
                //秒杀第三套方案，不基于TCC
                OrderResponse orderResponse = tradeApplicationService.newBuyPlus(orderCreateAndConfirmRequest);
                return orderResponse.getSuccess() ? LocalTransactionState.COMMIT_MESSAGE : LocalTransactionState.ROLLBACK_MESSAGE;
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
        OrderCreateAndConfirmRequest orderCreateAndConfirmRequest = JSON.parseObject(
                JSON.parseObject(new String(messageExt.getBody())).getString("body"), OrderCreateAndConfirmRequest.class);

        //获取订单详情
        SingleResponse<TradeOrderVO> response = orderFacadeService.getTradeOrder(orderCreateAndConfirmRequest.getOrderId());

        //如果订单已经创建成功，则直接返回。不再需要做废单处理了。
        if (response.getSuccess() && response.getData() != null && response.getData().getOrderState() == TradeOrderState.CONFIRM) {
            return LocalTransactionState.COMMIT_MESSAGE;
        }

        return LocalTransactionState.ROLLBACK_MESSAGE;
    }
}
