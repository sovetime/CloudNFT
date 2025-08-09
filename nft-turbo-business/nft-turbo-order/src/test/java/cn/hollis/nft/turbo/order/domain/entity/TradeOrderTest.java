package cn.hollis.nft.turbo.order.domain.entity;


import cn.hollis.nft.turbo.api.goods.constant.GoodsType;
import cn.hollis.nft.turbo.api.order.constant.TradeOrderState;
import cn.hollis.nft.turbo.api.order.request.*;
import cn.hollis.nft.turbo.api.pay.constant.PayChannel;
import com.alibaba.fastjson2.JSON;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

/**
 * @author Hollis
 */
public class TradeOrderTest {

    @Test
    public void createOrder() {
        OrderCreateRequest orderCreateRequest = orderCreateRequest();
        TradeOrder tradeOrder = TradeOrder.createOrder(orderCreateRequest);

        System.out.println(JSON.toJSONString(tradeOrder));
        Assert.assertEquals(orderCreateRequest.getItemCount(), tradeOrder.getItemCount());
        Assert.assertEquals(orderCreateRequest.getItemPrice().compareTo(tradeOrder.getItemPrice()), 0);
        Assert.assertEquals(orderCreateRequest.getBuyerId(), tradeOrder.getBuyerId());
        Assert.assertEquals(orderCreateRequest.getBuyerType(), tradeOrder.getBuyerType());
        Assert.assertEquals(orderCreateRequest.getSellerId(), tradeOrder.getSellerId());
        Assert.assertEquals(orderCreateRequest.getSellerType(), tradeOrder.getSellerType());
        Assert.assertEquals(orderCreateRequest.getGoodsId(), tradeOrder.getGoodsId());
        Assert.assertEquals(orderCreateRequest.getGoodsName(), tradeOrder.getGoodsName());
        Assert.assertEquals(orderCreateRequest.getGoodsType(), tradeOrder.getGoodsType());
        Assert.assertEquals(orderCreateRequest.getOrderAmount(), tradeOrder.getOrderAmount());
        Assert.assertEquals(orderCreateRequest.getSnapshotVersion(), tradeOrder.getSnapshotVersion());
        Assert.assertEquals(orderCreateRequest.getGoodsPicUrl(), tradeOrder.getGoodsPicUrl());
        Assert.assertNotNull(tradeOrder.getOrderId());
        Assert.assertNull(tradeOrder.getOrderFinishedTime());
        Assert.assertNull(tradeOrder.getOrderConfirmedTime());
        Assert.assertNull(tradeOrder.getPayChannel());
        Assert.assertNull(tradeOrder.getPayStreamId());
        Assert.assertNull(tradeOrder.getPaySucceedTime());
        Assert.assertEquals(tradeOrder.getOrderState(), TradeOrderState.CREATE);
    }

    @Test
    public void confirm() {
        OrderCreateRequest orderCreateRequest = orderCreateRequest();
        TradeOrder tradeOrder = TradeOrder.createOrder(orderCreateRequest);

        OrderConfirmRequest orderConfirmRequest = new OrderConfirmRequest();
        orderConfirmRequest.setIdentifier(UUID.randomUUID().toString());
        orderConfirmRequest.setOperateTime(new Date());
        tradeOrder.confirm(orderConfirmRequest);

        Assert.assertEquals(tradeOrder.getOrderState(), TradeOrderState.CONFIRM);
        Assert.assertNotNull(tradeOrder.getOrderConfirmedTime());
    }

    @Test
    public void timeout() {
        OrderCreateRequest orderCreateRequest = orderCreateRequest();
        TradeOrder tradeOrder = TradeOrder.createOrder(orderCreateRequest);

        Assert.assertNull(tradeOrder.getOrderConfirmedTime());
        Assert.assertEquals(tradeOrder.getOrderState(), TradeOrderState.CREATE);

        OrderTimeoutRequest orderTimeoutRequest = new OrderTimeoutRequest();
        orderTimeoutRequest.setOperateTime(new Date());
        tradeOrder.close(orderTimeoutRequest);

        Assert.assertEquals(orderTimeoutRequest.getOrderEvent().name(), tradeOrder.getCloseType());
        Assert.assertEquals(tradeOrder.getOrderState(), TradeOrderState.CLOSED);
        Assert.assertEquals(0, tradeOrder.getOrderClosedTime().compareTo(orderTimeoutRequest.getOperateTime()));
    }

    @Test
    public void cancel() {
        OrderCreateRequest orderCreateRequest = orderCreateRequest();
        TradeOrder tradeOrder = TradeOrder.createOrder(orderCreateRequest);

        Assert.assertNull(tradeOrder.getOrderConfirmedTime());
        Assert.assertEquals(tradeOrder.getOrderState(), TradeOrderState.CREATE);

        OrderCancelRequest orderCancelRequest = new OrderCancelRequest();
        orderCancelRequest.setOperateTime(new Date());
        tradeOrder.close(orderCancelRequest);

        Assert.assertEquals(orderCancelRequest.getOrderEvent().name(), tradeOrder.getCloseType());
        Assert.assertEquals(tradeOrder.getOrderState(), TradeOrderState.CLOSED);
        Assert.assertEquals(0, tradeOrder.getOrderClosedTime().compareTo(orderCancelRequest.getOperateTime()));
    }

    @Test
    public void pay() {
        OrderCreateRequest orderCreateRequest = orderCreateRequest();
        TradeOrder tradeOrder = TradeOrder.createOrder(orderCreateRequest);

        Assert.assertNull(tradeOrder.getOrderConfirmedTime());
        Assert.assertEquals(tradeOrder.getOrderState(), TradeOrderState.CREATE);

        OrderConfirmRequest orderConfirmRequest = new OrderConfirmRequest();
        orderConfirmRequest.setIdentifier(UUID.randomUUID().toString());
        orderConfirmRequest.setOperateTime(new Date());
        tradeOrder.confirm(orderConfirmRequest);

        OrderPayRequest orderPayRequest = new OrderPayRequest();
        orderPayRequest.setOperateTime(new Date());
        orderPayRequest.setPayChannel(PayChannel.ALIPAY);
        orderPayRequest.setPayStreamId("dsadasdsa");
        orderPayRequest.setAmount(new BigDecimal("2312.32"));

        tradeOrder.paySuccess(orderPayRequest);

        Assert.assertEquals(tradeOrder.getOrderState(), TradeOrderState.PAID);
        Assert.assertEquals(tradeOrder.getPayChannel(), orderPayRequest.getPayChannel());
        Assert.assertEquals(tradeOrder.getPayStreamId(), orderPayRequest.getPayStreamId());
        Assert.assertEquals(tradeOrder.getPaidAmount(), orderPayRequest.getAmount());
        Assert.assertEquals(0, tradeOrder.getPaySucceedTime().compareTo(orderPayRequest.getOperateTime()));
    }

    @Test
    public void finish() {
        OrderCreateRequest orderCreateRequest = orderCreateRequest();
        TradeOrder tradeOrder = TradeOrder.createOrder(orderCreateRequest);

        Assert.assertNull(tradeOrder.getOrderConfirmedTime());
        Assert.assertEquals(tradeOrder.getOrderState(), TradeOrderState.CREATE);

        OrderConfirmRequest orderConfirmRequest = new OrderConfirmRequest();
        orderConfirmRequest.setIdentifier(UUID.randomUUID().toString());
        orderConfirmRequest.setOperateTime(new Date());
        tradeOrder.confirm(orderConfirmRequest);

        OrderPayRequest orderPayRequest = new OrderPayRequest();
        orderPayRequest.setOperateTime(new Date());
        orderPayRequest.setPayChannel(PayChannel.ALIPAY);
        orderPayRequest.setPayStreamId("dsadasdsa");
        orderPayRequest.setAmount(new BigDecimal("2312.32"));

        tradeOrder.paySuccess(orderPayRequest);

        OrderFinishRequest orderFinishRequest = new OrderFinishRequest();
        orderFinishRequest.setOperateTime(new Date());

        tradeOrder.finish(orderFinishRequest);

        Assert.assertEquals(tradeOrder.getOrderFinishedTime().compareTo(orderFinishRequest.getOperateTime()), 0);
        Assert.assertEquals(tradeOrder.getOrderState(), TradeOrderState.FINISH);
    }

    private OrderCreateRequest orderCreateRequest() {
        OrderCreateRequest orderCreateRequest = new OrderCreateRequest();
        orderCreateRequest.setBuyerId("2312321");
        orderCreateRequest.setSellerId("67546456");
        orderCreateRequest.setGoodsId("909090");
        orderCreateRequest.setGoodsName("测试商品名称");
        orderCreateRequest.setGoodsType(GoodsType.BLIND_BOX);
        orderCreateRequest.setOrderAmount(new BigDecimal("20233.33"));
        orderCreateRequest.setItemCount(2);
        orderCreateRequest.setItemPrice(new BigDecimal("21312"));
        orderCreateRequest.setSnapshotVersion(123);
        orderCreateRequest.setOrderId(UUID.randomUUID().toString());
        orderCreateRequest.setGoodsPicUrl("http://www.nft.com/pic");
        return orderCreateRequest;
    }
}