package cn.hollis.nft.turbo.trade.listener;

import cn.hollis.nft.turbo.api.goods.request.GoodsSaleRequest;
import cn.hollis.nft.turbo.api.goods.service.GoodsTransactionFacadeService;
import cn.hollis.nft.turbo.api.order.OrderFacadeService;
import cn.hollis.nft.turbo.api.order.OrderTransactionFacadeService;
import cn.hollis.nft.turbo.api.order.constant.TradeOrderState;
import cn.hollis.nft.turbo.api.order.model.TradeOrderVO;
import cn.hollis.nft.turbo.api.order.request.OrderCreateAndConfirmRequest;
import cn.hollis.nft.turbo.api.order.request.OrderDiscardRequest;
import cn.hollis.nft.turbo.api.order.response.OrderResponse;
import cn.hollis.nft.turbo.api.user.constant.UserType;
import cn.hollis.nft.turbo.base.response.SingleResponse;
import cn.hollis.turbo.stream.consumer.AbstractStreamConsumer;
import cn.hollis.turbo.stream.param.MessageBody;
import cn.hutool.core.lang.Assert;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Bean
    Consumer<Message<MessageBody>> normalBuyPreCancel() {
        return msg -> {
            OrderCreateAndConfirmRequest orderCreateAndConfirmRequest = getMessage(msg, OrderCreateAndConfirmRequest.class);
            SingleResponse<TradeOrderVO> response = orderFacadeService.getTradeOrder(orderCreateAndConfirmRequest.getOrderId());

            //如果订单已经创建成功，则直接返回。不再需要做废单处理了。
            if (response.getSuccess() && response.getData() != null && response.getData().getOrderState() == TradeOrderState.CONFIRM) {
                return;
            }

            doCancel(orderCreateAndConfirmRequest);
        };
    }

    @Bean
    Consumer<Message<MessageBody>> normalBuyCancel() {
        return msg -> {
            OrderCreateAndConfirmRequest orderCreateAndConfirmRequest = getMessage(msg, OrderCreateAndConfirmRequest.class);
            doCancel(orderCreateAndConfirmRequest);
        };
    }

    private void doCancel(OrderCreateAndConfirmRequest orderCreateAndConfirmRequest) {
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
