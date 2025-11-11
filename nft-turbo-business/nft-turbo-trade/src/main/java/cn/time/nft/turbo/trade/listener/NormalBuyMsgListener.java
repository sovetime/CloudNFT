package cn.time.nft.turbo.trade.listener;

import cn.time.nft.turbo.api.goods.request.GoodsSaleRequest;
import cn.time.nft.turbo.api.goods.service.GoodsTransactionFacadeService;
import cn.time.nft.turbo.api.order.OrderFacadeService;
import cn.time.nft.turbo.api.order.OrderTransactionFacadeService;
import cn.time.nft.turbo.api.order.constant.TradeOrderState;
import cn.time.nft.turbo.api.order.model.TradeOrderVO;
import cn.time.nft.turbo.api.order.request.OrderCreateAndConfirmRequest;
import cn.time.nft.turbo.api.order.request.OrderDiscardRequest;
import cn.time.nft.turbo.api.order.response.OrderResponse;
import cn.time.nft.turbo.api.user.constant.UserType;
import cn.time.nft.turbo.base.response.SingleResponse;
import cn.time.turbo.stream.consumer.AbstractStreamConsumer;
import cn.time.turbo.stream.param.MessageBody;
import cn.hutool.core.lang.Assert;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;


@Component
@Slf4j
//普通下单listener
public class NormalBuyMsgListener extends AbstractStreamConsumer {

    @Resource
    private OrderFacadeService orderFacadeService;

    @Resource
    private OrderTransactionFacadeService orderTransactionFacadeService;

    @Resource
    private GoodsTransactionFacadeService goodsTransactionFacadeService;

    //预废单，消息到达时先检查订单状态，订单创建成功，则直接返回，不进行后续的废单处理
    @Bean
    Consumer<Message<MessageBody>> normalBuyPreCancel() {
        return msg -> {
            //从msg中解析出消息对象
            OrderCreateAndConfirmRequest orderCreateAndConfirmRequest = getMessage(msg, OrderCreateAndConfirmRequest.class);
            //获取订单详情
            SingleResponse<TradeOrderVO> response = orderFacadeService.getTradeOrder(orderCreateAndConfirmRequest.getOrderId());

            //如果订单已经创建成功，则直接返回。不再需要做废单处理了。
            if (response.getSuccess() && response.getData() != null &&
                response.getData().getOrderState() == TradeOrderState.CONFIRM) {
                return;
            }

            //废单
            doCancel(orderCreateAndConfirmRequest);
        };
    }

    //废单，直接废单
    @Bean
    Consumer<Message<MessageBody>> normalBuyCancel() {
        return msg -> {
            //从msg中解析出消息对象
            OrderCreateAndConfirmRequest orderCreateAndConfirmRequest = getMessage(msg, OrderCreateAndConfirmRequest.class);
            //废单
            doCancel(orderCreateAndConfirmRequest);
        };
    }

    //废单
    private void doCancel(OrderCreateAndConfirmRequest orderCreateAndConfirmRequest) {
        //解锁库存
        GoodsSaleRequest goodsSaleRequest = new GoodsSaleRequest(orderCreateAndConfirmRequest);
        boolean result = goodsTransactionFacadeService.cancelDecreaseInventory(goodsSaleRequest).getSuccess();
        Assert.isTrue(result, "order cancel failed");

        OrderDiscardRequest orderDiscardRequest = new OrderDiscardRequest();
        orderDiscardRequest.setOperatorType(UserType.PLATFORM);
        orderDiscardRequest.setOperator(UserType.PLATFORM.name());
        BeanUtils.copyProperties(orderCreateAndConfirmRequest, orderDiscardRequest);

        OrderResponse orderResponse = orderTransactionFacadeService.cancelOrder(orderDiscardRequest,"normalBuy");
        Assert.isTrue(orderResponse.getSuccess(), orderResponse.getResponseCode());
    }
}
