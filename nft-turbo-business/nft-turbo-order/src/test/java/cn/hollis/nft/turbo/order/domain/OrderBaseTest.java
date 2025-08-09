package cn.hollis.nft.turbo.order.domain;

import cn.hollis.nft.turbo.api.common.constant.BusinessCode;
import cn.hollis.nft.turbo.api.goods.constant.GoodsType;
import cn.hollis.nft.turbo.api.order.request.OrderCreateRequest;
import cn.hollis.nft.turbo.api.pay.service.PayFacadeService;
import cn.hollis.nft.turbo.order.NfTurboOrderApplication;
import cn.hollis.nft.turbo.order.sharding.id.DistributeID;
import cn.hollis.nft.turbo.order.sharding.id.WorkerIdHolder;
import cn.hutool.core.util.RandomUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.redisson.api.RedissonClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * @author Hollis
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {NfTurboOrderApplication.class})
@ActiveProfiles("test")
public class OrderBaseTest {

    @MockBean
    private PayFacadeService payFacadeService;

    @MockBean
    private RedissonClient redissonClient;

    @MockBean
    private WorkerIdHolder workerIdHolder;

    @Test
    public void test(){

    }

    protected OrderCreateRequest orderCreateRequest() {
        OrderCreateRequest orderCreateRequest = new OrderCreateRequest();
        String buyerId = String.valueOf(UUID.randomUUID().hashCode());
        orderCreateRequest.setBuyerId(buyerId);
        orderCreateRequest.setSellerId(UUID.randomUUID().toString().substring(0, 10));
        orderCreateRequest.setGoodsId(RandomUtil.randomNumbers(5));
        orderCreateRequest.setGoodsName(UUID.randomUUID().toString());
        orderCreateRequest.setGoodsType(GoodsType.BLIND_BOX);
        orderCreateRequest.setOrderAmount(new BigDecimal("20233.33"));
        orderCreateRequest.setIdentifier(UUID.randomUUID().toString());
        orderCreateRequest.setItemPrice(new BigDecimal("3212"));
        orderCreateRequest.setItemCount(1);
        orderCreateRequest.setOrderId(DistributeID.generateWithSnowflake(BusinessCode.TRADE_ORDER, WorkerIdHolder.WORKER_ID, buyerId));
        return orderCreateRequest;
    }
}
