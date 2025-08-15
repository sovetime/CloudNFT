package cn.hollis.nft.turbo.trade.listener;

import cn.hollis.nft.turbo.api.goods.request.GoodsSaleRequest;
import cn.hollis.nft.turbo.api.goods.response.GoodsSaleResponse;
import cn.hollis.nft.turbo.api.goods.service.GoodsFacadeService;
import cn.hollis.nft.turbo.api.inventory.InventoryTransactionFacadeService;
import cn.hollis.nft.turbo.api.inventory.request.InventoryRequest;
import cn.hollis.nft.turbo.api.inventory.service.InventoryFacadeService;
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
import com.alibaba.fastjson.JSON;
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
public class NewBuyPlusMsgListener extends AbstractStreamConsumer {

    @Resource
    private OrderFacadeService orderFacadeService;

    @Resource
    private OrderTransactionFacadeService orderTransactionFacadeService;

    @Resource
    private InventoryTransactionFacadeService inventoryTransactionFacadeService;

    @Resource
    private GoodsFacadeService goodsFacadeService;

    @Resource
    private InventoryFacadeService inventoryFacadeService;

    @Bean
    Consumer<Message<MessageBody>> newBuyPlusPreCancel() {
        return msg -> {
            OrderCreateAndConfirmRequest orderCreateAndConfirmRequest = getMessage(msg, OrderCreateAndConfirmRequest.class);
            log.warn("NewBuyPlusMsgListener receive newBuyPlusPreCancel message : {}", JSON.toJSONString(orderCreateAndConfirmRequest));
            SingleResponse<TradeOrderVO> response = orderFacadeService.getTradeOrder(orderCreateAndConfirmRequest.getOrderId());

            //如果订单已经创建成功，则直接返回。不再需要做废单处理了。
            if (response.getSuccess() && response.getData() != null && response.getData().getOrderState() == TradeOrderState.CONFIRM) {
                //为了解决，OrderCreateTransactionListener里面存在的因为网络延迟或者数据库异常而导致查询到的订单状态不是CONFIRM，但是后来又变成了CONFIRM的情况，
                //所以在这里需要做补偿
                GoodsSaleRequest goodsSaleRequest = new GoodsSaleRequest(orderCreateAndConfirmRequest);
                log.info("saleWithoutHint in newBuyPlusPreCancel message : {}", JSON.toJSONString(orderCreateAndConfirmRequest));
                GoodsSaleResponse goodsSaleResponse = goodsFacadeService.saleWithoutHint(goodsSaleRequest);
                Assert.isTrue(goodsSaleResponse.getSuccess(), "saleWithoutHint failed ," + response.getResponseMessage());
                return;
            }

            //doCancel(orderCreateAndConfirmRequest);
            SingleResponse<Boolean> increaseResponse = inventoryFacadeService.increase(new InventoryRequest(orderCreateAndConfirmRequest));
            Assert.isTrue(increaseResponse.getSuccess() && increaseResponse.getData(), "increase inventory failed");
        };
    }

    @Bean
    Consumer<Message<MessageBody>> newBuyPlusCancel() {
        return msg -> {
            OrderCreateAndConfirmRequest orderCreateAndConfirmRequest = getMessage(msg, OrderCreateAndConfirmRequest.class);
            log.warn("NewBuyPlusMsgListener receive newBuyPlusCancel message : {}", JSON.toJSONString(orderCreateAndConfirmRequest));
            doCancel(orderCreateAndConfirmRequest);
        };
    }

    //TCC场景下的cancel
    @Deprecated
    private void doCancel(OrderCreateAndConfirmRequest orderCreateAndConfirmRequest) {
        InventoryRequest inventoryRequest = new InventoryRequest(orderCreateAndConfirmRequest);
        boolean result = inventoryTransactionFacadeService.cancelDecrease(inventoryRequest);
        Assert.isTrue(result, "inventory increase failed");
        OrderDiscardRequest orderDiscardRequest = new OrderDiscardRequest();
        orderDiscardRequest.setOperatorType(UserType.PLATFORM);
        orderDiscardRequest.setOperator(UserType.PLATFORM.name());
        BeanUtils.copyProperties(orderCreateAndConfirmRequest, orderDiscardRequest);
        OrderResponse orderResponse = orderTransactionFacadeService.cancelOrder(orderDiscardRequest, "newBuyPlus");
        Assert.isTrue(orderResponse.getSuccess(), orderResponse.getResponseCode());
    }
}
