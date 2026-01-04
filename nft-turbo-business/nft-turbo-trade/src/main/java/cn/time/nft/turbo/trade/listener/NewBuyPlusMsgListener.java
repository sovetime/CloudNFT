package cn.time.nft.turbo.trade.listener;

import cn.hutool.core.lang.Assert;
import cn.time.nft.turbo.api.goods.request.GoodsSaleRequest;
import cn.time.nft.turbo.api.goods.response.GoodsSaleResponse;
import cn.time.nft.turbo.api.goods.service.GoodsFacadeService;
import cn.time.nft.turbo.api.inventory.InventoryTransactionFacadeService;
import cn.time.nft.turbo.api.inventory.request.InventoryRequest;
import cn.time.nft.turbo.api.inventory.service.InventoryFacadeService;
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
import com.alibaba.fastjson.JSON;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;


@Component
@Slf4j
//TCC补偿监听器
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

    //try失败的补偿机制
    @Bean
    public Consumer<Message<MessageBody>> newBuyPlusCancel() {
        return msg -> {
            //从msg中解析出消息对象
            OrderCreateAndConfirmRequest orderCreateAndConfirmRequest = getMessage(msg, OrderCreateAndConfirmRequest.class);
            log.warn("NewBuyPlusMsgListener receive newBuyPlusCancel message : {}", JSON.toJSONString(orderCreateAndConfirmRequest));

            //取消订单
            doCancel(orderCreateAndConfirmRequest);
        };
    }

    //数据回滚
    private void doCancel(OrderCreateAndConfirmRequest orderCreateAndConfirmRequest) {
        //库存扣减-cancel
        InventoryRequest inventoryRequest = new InventoryRequest(orderCreateAndConfirmRequest);
        boolean result = inventoryTransactionFacadeService.cancelDecrease(inventoryRequest);
        Assert.isTrue(result, "inventory increase failed");
        OrderDiscardRequest orderDiscardRequest = new OrderDiscardRequest();
        orderDiscardRequest.setOperatorType(UserType.PLATFORM);
        orderDiscardRequest.setOperator(UserType.PLATFORM.name());
        BeanUtils.copyProperties(orderCreateAndConfirmRequest, orderDiscardRequest);

        //取消订单-cancel
        OrderResponse orderResponse = orderTransactionFacadeService.cancelOrder(orderDiscardRequest, "newBuyPlus");
        Assert.isTrue(orderResponse.getSuccess(), orderResponse.getResponseCode());
    }

    //confirm失败,可能是网络延迟/数据库异常导致的，检查有问题进行补偿，confirm失败进行回滚
    //由于网络延迟或者数据库异常而导致查询到的订单状态不是CONFIRM，但是后来又变成了CONFIRM的情况，的补偿机制
    @Bean
    public Consumer<Message<MessageBody>> newBuyPlusPreCancel() {
        return msg -> {
            //获取消息
            OrderCreateAndConfirmRequest orderCreateAndConfirmRequest = getMessage(msg, OrderCreateAndConfirmRequest.class);
            log.warn("NewBuyPlusMsgListener receive newBuyPlusPreCancel message : {}", JSON.toJSONString(orderCreateAndConfirmRequest));

            //获取订单详情
            SingleResponse<TradeOrderVO> response = orderFacadeService.getTradeOrder(orderCreateAndConfirmRequest.getOrderId());

            //如果订单已经创建成功，则直接返回。不再需要做废单处理了
            if (response.getSuccess() && response.getData() != null && response.getData().getOrderState() == TradeOrderState.CONFIRM) {
                GoodsSaleRequest goodsSaleRequest = new GoodsSaleRequest(orderCreateAndConfirmRequest);
                log.info("saleWithoutHint in newBuyPlusPreCancel message : {}", JSON.toJSONString(orderCreateAndConfirmRequest));
                //藏品出售 -无hit
                GoodsSaleResponse goodsSaleResponse = goodsFacadeService.saleWithoutHint(goodsSaleRequest);
                Assert.isTrue(goodsSaleResponse.getSuccess(), "saleWithoutHint failed ," + response.getResponseMessage());
                return;
            }

            //库存增加（redis库存）
            SingleResponse<Boolean> increaseResponse = inventoryFacadeService.increase(new InventoryRequest(orderCreateAndConfirmRequest));
            Assert.isTrue(increaseResponse.getSuccess() && increaseResponse.getData(), "increase inventory failed");
        };
    }
}
