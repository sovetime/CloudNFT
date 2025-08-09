package cn.hollis.nft.turbo.order.infrastructure.mapper;


import cn.hollis.nft.turbo.api.order.constant.TradeOrderEvent;
import cn.hollis.nft.turbo.order.domain.OrderBaseTest;
import cn.hollis.nft.turbo.order.domain.entity.TradeOrder;
import cn.hollis.nft.turbo.order.domain.entity.TradeOrderStream;
import cn.hutool.core.util.IdUtil;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * @author Hollis
 */
public class OrderStreamMapperTest extends OrderBaseTest {

    @Autowired
    OrderStreamMapper orderStreamMapper;


    @Test
    public void selectByIdentifier() {
        TradeOrder tradeOrder = TradeOrder.createOrder(orderCreateRequest());
        tradeOrder.setId(IdUtil.getSnowflake().nextId());

        String identifier = UUID.randomUUID().toString();
        TradeOrderStream tradeOrderStream = new TradeOrderStream(tradeOrder, TradeOrderEvent.CREATE, identifier);
        tradeOrderStream.setId(IdUtil.getSnowflake().nextId());
        int result = orderStreamMapper.insert(tradeOrderStream);
        Assert.assertEquals(result, 1);

        TradeOrderStream existOrder = orderStreamMapper.selectByIdentifier(identifier, TradeOrderEvent.CREATE.name(), tradeOrder.getOrderId());
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
        Assert.assertNotNull(existOrder.getStreamType());
        Assert.assertNotNull(existOrder.getStreamIdentifier());


        Assert.assertNull(existOrder.getPayStreamId());
        Assert.assertNull(existOrder.getPayChannel());
        Assert.assertNull(existOrder.getOrderClosedTime());
        Assert.assertNull(existOrder.getOrderConfirmedTime());
        Assert.assertNull(existOrder.getPaySucceedTime());
        Assert.assertNull(existOrder.getOrderFinishedTime());
        Assert.assertNull(existOrder.getCloseType());

        Assert.assertNotEquals(existOrder.getId(), tradeOrder.getId());
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
        Assert.assertEquals(existOrder.getStreamType(), TradeOrderEvent.CREATE);
        Assert.assertEquals(existOrder.getStreamIdentifier(), identifier);
        Assert.assertEquals(existOrder.getIdentifier(), tradeOrder.getIdentifier());
        Assert.assertEquals(existOrder.getPaidAmount().compareTo(tradeOrder.getPaidAmount()), 0);
    }

}