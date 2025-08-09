package cn.hollis.nft.turbo.order.domain.listener;

import cn.hollis.nft.turbo.api.goods.response.GoodsSaleResponse;
import cn.hollis.nft.turbo.api.goods.service.GoodsFacadeService;
import cn.hollis.nft.turbo.api.order.constant.TradeOrderState;
import cn.hollis.nft.turbo.api.order.request.OrderCreateRequest;
import cn.hollis.nft.turbo.order.domain.OrderBaseTest;
import cn.hollis.nft.turbo.order.domain.entity.TradeOrder;
import cn.hollis.nft.turbo.order.domain.listener.event.OrderCreateEvent;
import cn.hollis.nft.turbo.order.domain.service.OrderManageService;
import cn.hollis.nft.turbo.order.domain.service.OrderReadService;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

/**
 * @author Hollis
 */
public class OrderEventListenerTest extends OrderBaseTest {

    @Autowired
    OrderEventListener orderEventListener;

    @Autowired
    OrderManageService orderManageService;
    @Autowired
    OrderReadService orderReadService;

    @MockBean
    public GoodsFacadeService goodsFacadeService;

    @Test
    public void testOnApplicationEvent() {
        GoodsSaleResponse response = new GoodsSaleResponse();
        response.setSuccess(true);
        when(goodsFacadeService.trySale(any())).thenReturn(response);

        OrderCreateRequest orderCreateRequest = orderCreateRequest();

        String orderId = orderManageService.create(orderCreateRequest).getOrderId();

        TradeOrder tradeOrder = orderReadService.getOrder(orderId);

        orderEventListener.onApplicationEvent(new OrderCreateEvent(tradeOrder));

        try {
            //等子线程执行完
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        tradeOrder = orderReadService.getOrder(orderId);

        Assert.assertEquals(tradeOrder.getOrderState(), TradeOrderState.CONFIRM);
        Assert.assertNotNull(tradeOrder.getOrderConfirmedTime());
    }

}