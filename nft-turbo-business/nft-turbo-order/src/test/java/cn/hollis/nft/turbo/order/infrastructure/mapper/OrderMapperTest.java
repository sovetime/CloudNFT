package cn.hollis.nft.turbo.order.infrastructure.mapper;


import cn.hollis.nft.turbo.api.order.constant.TradeOrderState;
import cn.hollis.nft.turbo.api.order.request.OrderCancelRequest;
import cn.hollis.nft.turbo.api.order.request.OrderFinishRequest;
import cn.hollis.nft.turbo.api.order.request.OrderPayRequest;
import cn.hollis.nft.turbo.api.pay.constant.PayChannel;
import cn.hollis.nft.turbo.order.domain.OrderBaseTest;
import cn.hollis.nft.turbo.order.domain.entity.TradeOrder;
import cn.hutool.core.util.IdUtil;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author Hollis
 */
public class OrderMapperTest extends OrderBaseTest {

    @Autowired
    OrderMapper orderMapper;

    @Test
    public void selectByOrderId() {
        TradeOrder tradeOrder = TradeOrder.createOrder(orderCreateRequest());
        tradeOrder.setId(IdUtil.getSnowflake().nextId());
        int result = orderMapper.insert(tradeOrder);
        Assert.assertEquals(result, 1);

        String orderId = tradeOrder.getOrderId();

        TradeOrder existOrder = orderMapper.selectByOrderId(orderId);
        Assert.assertNotNull(existOrder.getId());
        Assert.assertNotNull(existOrder.getOrderId());
        Assert.assertNotNull(existOrder.getOrderState());
        Assert.assertNotNull(existOrder.getBuyerId());
        Assert.assertNotNull(existOrder.getBuyerType());
        Assert.assertNotNull(existOrder.getSellerId());
        Assert.assertNotNull(existOrder.getSellerType());
        Assert.assertNotNull(existOrder.getGoodsId());
        Assert.assertNotNull(existOrder.getGoodsName());
        Assert.assertNotNull(existOrder.getGoodsType());
        Assert.assertNotNull(existOrder.getOrderAmount());
        Assert.assertNotNull(existOrder.getIdentifier());
        Assert.assertNotNull(existOrder.getPaidAmount());
        Assert.assertNotNull(existOrder.getOrderState());


        Assert.assertNull(existOrder.getPayStreamId());
        Assert.assertNull(existOrder.getPayChannel());
        Assert.assertNull(existOrder.getOrderClosedTime());
        Assert.assertNull(existOrder.getOrderConfirmedTime());
        Assert.assertNull(existOrder.getPaySucceedTime());
        Assert.assertNull(existOrder.getOrderFinishedTime());
        Assert.assertNull(existOrder.getCloseType());

        Assert.assertEquals(existOrder.getId(), tradeOrder.getId());
        Assert.assertEquals(existOrder.getOrderId(), tradeOrder.getOrderId());
        Assert.assertEquals(existOrder.getOrderState(), tradeOrder.getOrderState());
        Assert.assertEquals(existOrder.getBuyerId(), tradeOrder.getBuyerId());
        Assert.assertEquals(existOrder.getBuyerType(), tradeOrder.getBuyerType());
        Assert.assertEquals(existOrder.getSellerId(), tradeOrder.getSellerId());
        Assert.assertEquals(existOrder.getSellerType(), tradeOrder.getSellerType());
        Assert.assertEquals(existOrder.getGoodsId(), tradeOrder.getGoodsId());
        Assert.assertEquals(existOrder.getGoodsName(), tradeOrder.getGoodsName());
        Assert.assertEquals(existOrder.getGoodsType(), tradeOrder.getGoodsType());
        Assert.assertEquals(existOrder.getOrderAmount().compareTo(tradeOrder.getOrderAmount()), 0);
        Assert.assertEquals(existOrder.getPaidAmount().compareTo(BigDecimal.ZERO), 0);
        Assert.assertEquals(existOrder.getIdentifier(), tradeOrder.getIdentifier());
        Assert.assertEquals(existOrder.getPaidAmount().compareTo(tradeOrder.getPaidAmount()), 0);
    }

    @Test
    public void selectByIdentifier() {
        TradeOrder tradeOrder = TradeOrder.createOrder(orderCreateRequest());
        tradeOrder.setId(IdUtil.getSnowflake().nextId());
        int result = orderMapper.insert(tradeOrder);
        Assert.assertEquals(result, 1);

        TradeOrder existOrder = orderMapper.selectByIdentifier(tradeOrder.getIdentifier(), tradeOrder.getBuyerId());
        Assert.assertNotNull(existOrder.getId());
        Assert.assertNotNull(existOrder.getOrderId());
        Assert.assertNotNull(existOrder.getOrderState());
        Assert.assertNotNull(existOrder.getBuyerId());
        Assert.assertNotNull(existOrder.getBuyerType());
        Assert.assertNotNull(existOrder.getSellerId());
        Assert.assertNotNull(existOrder.getSellerType());
        Assert.assertNotNull(existOrder.getGoodsId());
        Assert.assertNotNull(existOrder.getGoodsName());
        Assert.assertNotNull(existOrder.getGoodsType());
        Assert.assertNotNull(existOrder.getOrderAmount());
        Assert.assertNotNull(existOrder.getIdentifier());
        Assert.assertNotNull(existOrder.getPaidAmount());
        Assert.assertNotNull(existOrder.getOrderState());


        Assert.assertNull(existOrder.getPayStreamId());
        Assert.assertNull(existOrder.getPayChannel());
        Assert.assertNull(existOrder.getOrderClosedTime());
        Assert.assertNull(existOrder.getOrderConfirmedTime());
        Assert.assertNull(existOrder.getPaySucceedTime());
        Assert.assertNull(existOrder.getOrderFinishedTime());
        Assert.assertNull(existOrder.getCloseType());

        Assert.assertEquals(existOrder.getId(), tradeOrder.getId());
        Assert.assertEquals(existOrder.getOrderId(), tradeOrder.getOrderId());
        Assert.assertEquals(existOrder.getOrderState(), tradeOrder.getOrderState());
        Assert.assertEquals(existOrder.getBuyerId(), tradeOrder.getBuyerId());
        Assert.assertEquals(existOrder.getBuyerType(), tradeOrder.getBuyerType());
        Assert.assertEquals(existOrder.getSellerId(), tradeOrder.getSellerId());
        Assert.assertEquals(existOrder.getSellerType(), tradeOrder.getSellerType());
        Assert.assertEquals(existOrder.getGoodsId(), tradeOrder.getGoodsId());
        Assert.assertEquals(existOrder.getGoodsName(), tradeOrder.getGoodsName());
        Assert.assertEquals(existOrder.getGoodsType(), tradeOrder.getGoodsType());
        Assert.assertEquals(existOrder.getOrderAmount().compareTo(tradeOrder.getOrderAmount()), 0);
        Assert.assertEquals(existOrder.getPaidAmount().compareTo(BigDecimal.ZERO), 0);
        Assert.assertEquals(existOrder.getIdentifier(), tradeOrder.getIdentifier());
        Assert.assertEquals(existOrder.getPaidAmount().compareTo(tradeOrder.getPaidAmount()), 0);
    }

    @Test
    public void updateByOrderId_createToFinished() {
        TradeOrder tradeOrder = TradeOrder.createOrder(orderCreateRequest());
        tradeOrder.setId(IdUtil.getSnowflake().nextId());
        int result = orderMapper.insert(tradeOrder);
        Assert.assertEquals(result, 1);

        OrderPayRequest payRequest = new OrderPayRequest();
        payRequest.setAmount(BigDecimal.TEN);
        payRequest.setPayStreamId("alipay32132131");
        payRequest.setPayChannel(PayChannel.ALIPAY);
        payRequest.setOperateTime(new Date());
        payRequest.setOrderId(tradeOrder.getOrderId());
        tradeOrder.paySuccess(payRequest);

        OrderFinishRequest finishRequest = new OrderFinishRequest();
        finishRequest.setOrderId(tradeOrder.getOrderId());
        finishRequest.setOperateTime(new Date());
        tradeOrder.finish(finishRequest);
        int res = orderMapper.updateByOrderId(tradeOrder);
        Assert.assertEquals(res, 1);

        TradeOrder existOrder = orderMapper.selectByIdentifier(tradeOrder.getIdentifier(), tradeOrder.getBuyerId());
        Assert.assertNotNull(existOrder.getId());
        Assert.assertNotNull(existOrder.getOrderId());
        Assert.assertNotNull(existOrder.getOrderState());
        Assert.assertNotNull(existOrder.getBuyerId());
        Assert.assertNotNull(existOrder.getBuyerType());
        Assert.assertNotNull(existOrder.getSellerId());
        Assert.assertNotNull(existOrder.getSellerType());
        Assert.assertNotNull(existOrder.getGoodsId());
        Assert.assertNotNull(existOrder.getGoodsName());
        Assert.assertNotNull(existOrder.getGoodsType());
        Assert.assertNotNull(existOrder.getOrderAmount());
        Assert.assertNotNull(existOrder.getIdentifier());
        Assert.assertNotNull(existOrder.getPaidAmount());
        Assert.assertNotNull(existOrder.getOrderState());

        Assert.assertEquals(existOrder.getId(), tradeOrder.getId());
        Assert.assertEquals(existOrder.getOrderId(), tradeOrder.getOrderId());
        Assert.assertEquals(existOrder.getOrderState(), TradeOrderState.FINISH);
        Assert.assertEquals(existOrder.getBuyerId(), tradeOrder.getBuyerId());
        Assert.assertEquals(existOrder.getBuyerType(), tradeOrder.getBuyerType());
        Assert.assertEquals(existOrder.getSellerId(), tradeOrder.getSellerId());
        Assert.assertEquals(existOrder.getSellerType(), tradeOrder.getSellerType());
        Assert.assertEquals(existOrder.getGoodsId(), tradeOrder.getGoodsId());
        Assert.assertEquals(existOrder.getGoodsName(), tradeOrder.getGoodsName());
        Assert.assertEquals(existOrder.getGoodsType(), tradeOrder.getGoodsType());
        Assert.assertEquals(existOrder.getOrderAmount().compareTo(tradeOrder.getOrderAmount()), 0);
        Assert.assertEquals(existOrder.getPaidAmount().compareTo(payRequest.getAmount()), 0);
        Assert.assertEquals(existOrder.getPaySucceedTime().compareTo(payRequest.getOperateTime()), 0);
        Assert.assertEquals(existOrder.getPayStreamId(), payRequest.getPayStreamId());
        Assert.assertEquals(existOrder.getPayChannel(), payRequest.getPayChannel());
        Assert.assertEquals(existOrder.getIdentifier(), tradeOrder.getIdentifier());
        Assert.assertEquals(existOrder.getPaidAmount().compareTo(tradeOrder.getPaidAmount()), 0);
        Assert.assertEquals(existOrder.getOrderFinishedTime().compareTo(finishRequest.getOperateTime()), 0);
    }

    @Test
    public void updateByOrderId_createToClosed() {
        TradeOrder tradeOrder = TradeOrder.createOrder(orderCreateRequest());
        tradeOrder.setId(IdUtil.getSnowflake().nextId());
        int result = orderMapper.insert(tradeOrder);
        Assert.assertEquals(result, 1);

        OrderCancelRequest cancelRequest = new OrderCancelRequest();
        cancelRequest.setOrderId(tradeOrder.getOrderId());
        cancelRequest.setOperateTime(new Date());
        tradeOrder.close(cancelRequest);

        orderMapper.updateByOrderId(tradeOrder);

        TradeOrder existOrder = orderMapper.selectByIdentifier(tradeOrder.getIdentifier(), tradeOrder.getBuyerId());
        Assert.assertNotNull(existOrder.getId());
        Assert.assertNotNull(existOrder.getOrderId());
        Assert.assertNotNull(existOrder.getOrderState());
        Assert.assertNotNull(existOrder.getBuyerId());
        Assert.assertNotNull(existOrder.getBuyerType());
        Assert.assertNotNull(existOrder.getSellerId());
        Assert.assertNotNull(existOrder.getSellerType());
        Assert.assertNotNull(existOrder.getGoodsId());
        Assert.assertNotNull(existOrder.getGoodsName());
        Assert.assertNotNull(existOrder.getGoodsType());
        Assert.assertNotNull(existOrder.getOrderAmount());
        Assert.assertNotNull(existOrder.getIdentifier());
        Assert.assertNotNull(existOrder.getPaidAmount());
        Assert.assertNotNull(existOrder.getOrderState());

        Assert.assertEquals(existOrder.getId(), tradeOrder.getId());
        Assert.assertEquals(existOrder.getOrderId(), tradeOrder.getOrderId());
        Assert.assertEquals(existOrder.getOrderState(), TradeOrderState.CLOSED);
        Assert.assertEquals(existOrder.getBuyerId(), tradeOrder.getBuyerId());
        Assert.assertEquals(existOrder.getBuyerType(), tradeOrder.getBuyerType());
        Assert.assertEquals(existOrder.getSellerId(), tradeOrder.getSellerId());
        Assert.assertEquals(existOrder.getSellerType(), tradeOrder.getSellerType());
        Assert.assertEquals(existOrder.getGoodsId(), tradeOrder.getGoodsId());
        Assert.assertEquals(existOrder.getGoodsName(), tradeOrder.getGoodsName());
        Assert.assertEquals(existOrder.getGoodsType(), tradeOrder.getGoodsType());
        Assert.assertEquals(existOrder.getOrderAmount().compareTo(tradeOrder.getOrderAmount()), 0);
        Assert.assertEquals(existOrder.getOrderClosedTime().compareTo(cancelRequest.getOperateTime()), 0);
        Assert.assertEquals(existOrder.getCloseType(), cancelRequest.getOrderEvent().name());
        Assert.assertEquals(existOrder.getIdentifier(), tradeOrder.getIdentifier());
        Assert.assertEquals(existOrder.getPaidAmount().compareTo(tradeOrder.getPaidAmount()), 0);

    }
}