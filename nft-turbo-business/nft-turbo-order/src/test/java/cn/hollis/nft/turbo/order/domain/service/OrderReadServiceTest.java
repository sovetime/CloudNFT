package cn.hollis.nft.turbo.order.domain.service;

import cn.hollis.nft.turbo.api.order.constant.TradeOrderState;
import cn.hollis.nft.turbo.order.domain.OrderBaseTest;
import cn.hollis.nft.turbo.order.domain.entity.TradeOrder;
import cn.hollis.nft.turbo.order.infrastructure.mapper.OrderMapper;
import org.apache.commons.lang3.time.DateUtils;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

/**
 * @author Hollis
 */
public class OrderReadServiceTest extends OrderBaseTest {
    @Autowired
    OrderReadService orderService;

    @Autowired
    OrderMapper orderMapper;


    @Test
    public void testGetTimeoutOrderByPage() {
        //状态不满足要求
        //时间不满足要求
        for (int i = 0; i < 1; i++) {
            TradeOrder tradeOrder = TradeOrder.createOrder(orderCreateRequest());
            orderMapper.insert(tradeOrder);
        }

        //时间不满足要求
        for (int i = 0; i < 1; i++) {
            TradeOrder tradeOrder = TradeOrder.createOrder(orderCreateRequest());
            tradeOrder.setOrderState(TradeOrderState.CONFIRM);
            orderMapper.insert(tradeOrder);
        }

        //时间和状态都满足要求
        for (int i = 0; i < 2; i++) {
            TradeOrder tradeOrder = TradeOrder.createOrder(orderCreateRequest());
            tradeOrder.setGmtCreate(DateUtils.addMinutes(new Date(), -31));
            tradeOrder.setOrderState(TradeOrderState.CONFIRM);
            orderMapper.insert(tradeOrder);
        }

        //状态不满足要求
        for (int i = 0; i < 2; i++) {
            TradeOrder tradeOrder = TradeOrder.createOrder(orderCreateRequest());
            tradeOrder.setGmtCreate(DateUtils.addMinutes(new Date(), -31));
            tradeOrder.setOrderState(TradeOrderState.PAID);
            orderMapper.insert(tradeOrder);
        }

        List<TradeOrder> tradeOrders = orderService.pageQueryTimeoutOrders(1, null, null);
        Assert.assertEquals(tradeOrders.size(), 1);

        tradeOrders = orderService.pageQueryTimeoutOrders(2, null, null);
        Assert.assertEquals(tradeOrders.size(), 2);

        tradeOrders = orderService.pageQueryTimeoutOrders(1, null, tradeOrders.get(1).getId() + 1);
        Assert.assertEquals(tradeOrders.size(), 0);
    }


    @Test
    public void testPageQueryNeedConfirmOrders() {

        for (int i = 0; i < 1; i++) {
            TradeOrder tradeOrder = TradeOrder.createOrder(orderCreateRequest());
            orderMapper.insert(tradeOrder);
        }

        for (int i = 0; i < 1; i++) {
            TradeOrder tradeOrder = TradeOrder.createOrder(orderCreateRequest());
            tradeOrder.setOrderState(TradeOrderState.CONFIRM);
            orderMapper.insert(tradeOrder);
        }

        for (int i = 0; i < 2; i++) {
            TradeOrder tradeOrder = TradeOrder.createOrder(orderCreateRequest());
            tradeOrder.setOrderState(TradeOrderState.PAID);
            orderMapper.insert(tradeOrder);
        }

        for (int i = 0; i < 2; i++) {
            TradeOrder tradeOrder = TradeOrder.createOrder(orderCreateRequest());
            tradeOrder.setGmtCreate(DateUtils.addMinutes(new Date(), -31));
            tradeOrder.setOrderState(TradeOrderState.PAID);
            tradeOrder.setOrderConfirmedTime(new Date());
            orderMapper.insert(tradeOrder);
        }

        List<TradeOrder> tradeOrders = orderService.pageQueryNeedConfirmOrders(1, null, null);
        Assert.assertEquals(tradeOrders.size(), 1);

        tradeOrders = orderService.pageQueryNeedConfirmOrders(5, null, null);
        Assert.assertEquals(tradeOrders.size(), 4);

        tradeOrders = orderService.pageQueryNeedConfirmOrders(5, null, tradeOrders.get(3).getId());
        Assert.assertEquals(tradeOrders.size(), 1);

        tradeOrders = orderService.pageQueryNeedConfirmOrders(5, null, tradeOrders.get(0).getId() + 1);
        Assert.assertEquals(tradeOrders.size(), 0);
    }
}