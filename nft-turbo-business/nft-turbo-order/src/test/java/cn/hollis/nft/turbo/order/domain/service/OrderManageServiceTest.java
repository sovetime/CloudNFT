package cn.hollis.nft.turbo.order.domain.service;

import cn.hollis.nft.turbo.api.common.constant.BusinessCode;
import cn.hollis.nft.turbo.api.goods.response.GoodsSaleResponse;
import cn.hollis.nft.turbo.api.goods.service.GoodsFacadeService;
import cn.hollis.nft.turbo.api.order.constant.TradeOrderState;
import cn.hollis.nft.turbo.api.order.request.*;
import cn.hollis.nft.turbo.api.order.response.OrderResponse;
import cn.hollis.nft.turbo.api.pay.constant.PayChannel;
import cn.hollis.nft.turbo.api.user.constant.UserType;
import cn.hollis.nft.turbo.order.domain.OrderBaseTest;
import cn.hollis.nft.turbo.order.domain.entity.TradeOrder;
import cn.hollis.nft.turbo.order.infrastructure.mapper.OrderMapper;
import cn.hutool.core.util.IdUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

/**
 * @author Hollis
 */
public class OrderManageServiceTest extends OrderBaseTest {
    @Autowired
    OrderManageService orderService;

    @Autowired
    OrderMapper orderMapper;

    @Autowired
    OrderReadService orderReadService;

    @MockBean
    public GoodsFacadeService goodsFacadeService;

    @Before
    public void init() {
        GoodsSaleResponse response = new GoodsSaleResponse();
        response.setSuccess(true);
        when(goodsFacadeService.trySale(any())).thenReturn(response);
    }

    @Test
    public void create() {
        OrderCreateRequest orderCreateRequest = orderCreateRequest();

        String orderId = orderService.create(orderCreateRequest).getOrderId();
        Assert.assertNotNull(orderId);
    }

    @Test
    public void createAndQuery() {
        OrderCreateRequest orderCreateRequest = orderCreateRequest();

        String orderId = orderService.create(orderCreateRequest).getOrderId();
        System.out.println(orderId);

        QueryWrapper<TradeOrder> queryWrapper = new QueryWrapper();
        queryWrapper.eq("order_id", orderId);
        TradeOrder tradeOrder = orderService.getOne(queryWrapper);
        Assert.assertNotNull(tradeOrder);

        queryWrapper = new QueryWrapper();
        queryWrapper.eq("buyer_id", orderCreateRequest.getBuyerId());
        tradeOrder = orderService.getOne(queryWrapper);
        Assert.assertNotNull(tradeOrder);

        queryWrapper = new QueryWrapper();
        queryWrapper.eq("seller_id", orderCreateRequest.getSellerId());
        tradeOrder = orderService.getOne(queryWrapper);
        Assert.assertNotNull(tradeOrder);
    }

    @Test
    public void confirm() {
        OrderCreateRequest orderCreateRequest = orderCreateRequest();

        String orderId = orderService.create(orderCreateRequest).getOrderId();
        Assert.assertNotNull(orderId);

        OrderConfirmRequest orderConfirmRequest = new OrderConfirmRequest();
        orderConfirmRequest.setOperateTime(new Date());
        orderConfirmRequest.setIdentifier(UUID.randomUUID().toString());
        orderConfirmRequest.setOrderId(orderId);
        orderConfirmRequest.setOperatorType(UserType.PLATFORM);
        orderConfirmRequest.setOperator(UserType.PLATFORM.name());

        OrderResponse response = orderService.confirm(orderConfirmRequest);
        Assert.assertTrue(response.getSuccess());
        TradeOrder tradeOrder = orderReadService.getOrder(orderId);
        Assert.assertNotNull(tradeOrder.getOrderConfirmedTime());
        Assert.assertEquals(tradeOrder.getOrderState(), TradeOrderState.CONFIRM);
    }


    @Test
    public void pay() {
        OrderCreateRequest orderCreateRequest = orderCreateRequest();

        String orderId = orderService.create(orderCreateRequest).getOrderId();
        Assert.assertNotNull(orderId);

        OrderConfirmRequest orderConfirmRequest = new OrderConfirmRequest();
        orderConfirmRequest.setOperateTime(new Date());
        orderConfirmRequest.setIdentifier(UUID.randomUUID().toString());
        orderConfirmRequest.setOrderId(orderId);
        orderConfirmRequest.setOperatorType(UserType.PLATFORM);
        orderConfirmRequest.setOperator(UserType.PLATFORM.name());

        OrderResponse response = orderService.confirm(orderConfirmRequest);
        Assert.assertTrue(response.getSuccess());


        OrderPayRequest orderPayRequest = new OrderPayRequest();
        orderPayRequest.setOperateTime(new Date());
        orderPayRequest.setIdentifier(UUID.randomUUID().toString());
        orderPayRequest.setOrderId(orderId);
        orderPayRequest.setOperatorType(UserType.CUSTOMER);
        orderPayRequest.setOperator(orderCreateRequest.getBuyerId());
        orderPayRequest.setPayStreamId(UUID.randomUUID().toString());
        orderPayRequest.setPayChannel(PayChannel.WECHAT);
        orderPayRequest.setAmount(new BigDecimal("1.1"));
        OrderResponse payResponse = orderService.paySuccess(orderPayRequest);
        Assert.assertTrue(payResponse.getSuccess());

        TradeOrder tradeOrder = orderReadService.getOrder(orderId);
        Assert.assertNotNull(tradeOrder.getPaySucceedTime());
        Assert.assertNotNull(tradeOrder.getPayChannel());
        Assert.assertNotNull(tradeOrder.getPaidAmount());
        Assert.assertEquals(tradeOrder.getOrderState(), TradeOrderState.PAID);
        Assert.assertEquals(tradeOrder.getPayChannel(), PayChannel.WECHAT);
        Assert.assertEquals(tradeOrder.getPaidAmount().compareTo(orderPayRequest.getAmount()), 0);
    }

    @Test
    public void createtToPaytoConfirm() {
        OrderCreateRequest orderCreateRequest = orderCreateRequest();

        String orderId = orderService.create(orderCreateRequest).getOrderId();
        Assert.assertNotNull(orderId);

        OrderPayRequest orderPayRequest = new OrderPayRequest();
        orderPayRequest.setOperateTime(new Date());
        orderPayRequest.setIdentifier(UUID.randomUUID().toString());
        orderPayRequest.setOrderId(orderId);
        orderPayRequest.setOperatorType(UserType.CUSTOMER);
        orderPayRequest.setOperator(orderCreateRequest.getBuyerId());
        orderPayRequest.setPayStreamId(UUID.randomUUID().toString());
        orderPayRequest.setPayChannel(PayChannel.WECHAT);
        orderPayRequest.setAmount(new BigDecimal("1.1"));

        try {
            //等 confirm 执行完
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        OrderResponse payResponse = orderService.paySuccess(orderPayRequest);
        System.out.println(JSON.toJSONString(payResponse));
        Assert.assertTrue(payResponse.getSuccess());

        TradeOrder tradeOrder = orderReadService.getOrder(orderId);
        Assert.assertNotNull(tradeOrder.getPaySucceedTime());
        Assert.assertNotNull(tradeOrder.getPayChannel());
        Assert.assertNotNull(tradeOrder.getPaidAmount());
        Assert.assertEquals(tradeOrder.getOrderState(), TradeOrderState.PAID);
        Assert.assertEquals(tradeOrder.getPayChannel(), PayChannel.WECHAT);
        Assert.assertEquals(tradeOrder.getPaidAmount().compareTo(orderPayRequest.getAmount()), 0);

        OrderConfirmRequest orderConfirmRequest = new OrderConfirmRequest();
        orderConfirmRequest.setOperateTime(new Date());
        orderConfirmRequest.setIdentifier(UUID.randomUUID().toString());
        orderConfirmRequest.setOrderId(orderId);
        orderConfirmRequest.setOperatorType(UserType.PLATFORM);
        orderConfirmRequest.setOperator(UserType.PLATFORM.name());
        OrderResponse response = orderService.confirm(orderConfirmRequest);
        Assert.assertTrue(response.getSuccess());

        tradeOrder = orderReadService.getOrder(orderId);
        Assert.assertNotNull(tradeOrder.getPaySucceedTime());
        Assert.assertNotNull(tradeOrder.getOrderConfirmedTime());
        Assert.assertNotNull(tradeOrder.getPayChannel());
        Assert.assertNotNull(tradeOrder.getPaidAmount());
        Assert.assertEquals(tradeOrder.getOrderState(), TradeOrderState.PAID);
        Assert.assertEquals(tradeOrder.getPayChannel(), PayChannel.WECHAT);
        Assert.assertEquals(tradeOrder.getPaidAmount().compareTo(orderPayRequest.getAmount()), 0);
    }

    @Test
    public void payRepeat() {
        OrderCreateRequest orderCreateRequest = orderCreateRequest();

        String orderId = orderService.create(orderCreateRequest).getOrderId();
        Assert.assertNotNull(orderId);

        OrderConfirmRequest orderConfirmRequest = new OrderConfirmRequest();
        orderConfirmRequest.setOperateTime(new Date());
        orderConfirmRequest.setIdentifier(UUID.randomUUID().toString());
        orderConfirmRequest.setOrderId(orderId);
        orderConfirmRequest.setOperatorType(UserType.PLATFORM);
        orderConfirmRequest.setOperator(UserType.PLATFORM.name());

        try {
            //等 confirm 执行完
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        OrderPayRequest orderPayRequest = new OrderPayRequest();
        orderPayRequest.setOperateTime(new Date());
        orderPayRequest.setIdentifier(UUID.randomUUID().toString());
        orderPayRequest.setOrderId(orderId);
        orderPayRequest.setOperatorType(UserType.CUSTOMER);
        orderPayRequest.setOperator(orderCreateRequest.getBuyerId());
        orderPayRequest.setPayStreamId(UUID.randomUUID().toString());
        orderPayRequest.setPayChannel(PayChannel.WECHAT);
        orderPayRequest.setAmount(new BigDecimal("1.1"));
        OrderResponse payResponse = orderService.paySuccess(orderPayRequest);
        Assert.assertTrue(payResponse.getSuccess());

        payResponse = orderService.paySuccess(orderPayRequest);
        Assert.assertTrue(payResponse.getSuccess());
    }

    @Test
    public void payTwice() {
        OrderCreateRequest orderCreateRequest = orderCreateRequest();

        String orderId = orderService.create(orderCreateRequest).getOrderId();
        Assert.assertNotNull(orderId);

        OrderConfirmRequest orderConfirmRequest = new OrderConfirmRequest();
        orderConfirmRequest.setOperateTime(new Date());
        orderConfirmRequest.setIdentifier(UUID.randomUUID().toString());
        orderConfirmRequest.setOrderId(orderId);
        orderConfirmRequest.setOperatorType(UserType.PLATFORM);
        orderConfirmRequest.setOperator(UserType.PLATFORM.name());

        OrderResponse response = orderService.confirm(orderConfirmRequest);
        Assert.assertTrue(response.getSuccess());


        OrderPayRequest orderPayRequest = new OrderPayRequest();
        orderPayRequest.setOperateTime(new Date());
        orderPayRequest.setIdentifier(UUID.randomUUID().toString());
        orderPayRequest.setOrderId(orderId);
        orderPayRequest.setOperatorType(UserType.CUSTOMER);
        orderPayRequest.setOperator(orderCreateRequest.getBuyerId());
        orderPayRequest.setPayStreamId(UUID.randomUUID().toString());
        orderPayRequest.setPayChannel(PayChannel.WECHAT);
        orderPayRequest.setAmount(new BigDecimal("1.1"));
        OrderResponse payResponse = orderService.paySuccess(orderPayRequest);
        Assert.assertTrue(payResponse.getSuccess());

        orderPayRequest.setPayStreamId(UUID.randomUUID().toString());
        orderPayRequest.setIdentifier(UUID.randomUUID().toString());
        payResponse = orderService.paySuccess(orderPayRequest);
        Assert.assertFalse(payResponse.getSuccess());
    }

    @Test
    public void cancel() {
        OrderCreateRequest orderCreateRequest = orderCreateRequest();

        String orderId = orderService.create(orderCreateRequest).getOrderId();
        Assert.assertNotNull(orderId);

        OrderCancelRequest orderCancelRequest = new OrderCancelRequest();
        orderCancelRequest.setOperateTime(new Date());
        orderCancelRequest.setIdentifier(UUID.randomUUID().toString());
        orderCancelRequest.setOrderId(orderId);
        orderCancelRequest.setOperatorType(UserType.CUSTOMER);
        orderCancelRequest.setOperator(orderCreateRequest.getBuyerId());

        try {
            //等 confirm 执行完
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        OrderResponse response = orderService.cancel(orderCancelRequest);
        System.out.println(JSON.toJSONString(response));
        Assert.assertTrue(response.getSuccess());

        TradeOrder tradeOrder = orderReadService.getOrder(orderId);
        Assert.assertNotNull(tradeOrder.getOrderClosedTime());
        Assert.assertEquals(tradeOrder.getOrderState(), TradeOrderState.CLOSED);
    }

    @Test
    public void cancelOthersOrder() {
        OrderCreateRequest orderCreateRequest = orderCreateRequest();

        String orderId = orderService.create(orderCreateRequest).getOrderId();
        Assert.assertNotNull(orderId);

        OrderCancelRequest orderCancelRequest = new OrderCancelRequest();
        orderCancelRequest.setOperateTime(new Date());
        orderCancelRequest.setIdentifier(UUID.randomUUID().toString());
        orderCancelRequest.setOrderId(orderId);
        orderCancelRequest.setOperatorType(UserType.CUSTOMER);
        //取消其他人的订单
        orderCancelRequest.setOperator(UUID.randomUUID().toString());

        OrderResponse response = orderService.cancel(orderCancelRequest);
        Assert.assertFalse(response.getSuccess());
        Assert.assertEquals(response.getResponseCode(), "PERMISSION_DENIED");
    }

    @Test
    public void timeout() {
        OrderCreateRequest orderCreateRequest = orderCreateRequest();

        String orderId = orderService.create(orderCreateRequest).getOrderId();
        Assert.assertNotNull(orderId);

        OrderTimeoutRequest orderTimeoutRequest = new OrderTimeoutRequest();
        orderTimeoutRequest.setOperateTime(new Date());
        orderTimeoutRequest.setIdentifier(UUID.randomUUID().toString());
        orderTimeoutRequest.setOrderId(orderId);
        orderTimeoutRequest.setOperatorType(UserType.PLATFORM);
        orderTimeoutRequest.setOperator(UserType.PLATFORM.name());

        try {
            //等 confirm 执行完
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        OrderResponse response = orderService.timeout(orderTimeoutRequest);
        System.out.println(JSON.toJSONString(response));
        Assert.assertTrue(response.getSuccess());

        TradeOrder tradeOrder = orderReadService.getOrder(orderId);
        Assert.assertNotNull(tradeOrder.getOrderClosedTime());
        Assert.assertEquals(tradeOrder.getOrderState(), TradeOrderState.CLOSED);
    }

    @Test
    public void testUid() {
        System.out.println(IdUtil.getSnowflake(BusinessCode.TRADE_ORDER.code()).nextId());
    }

}