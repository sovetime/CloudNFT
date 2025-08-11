package cn.hollis.nft.turbo.goods;


import cn.hollis.nft.turbo.api.chain.service.ChainFacadeService;
import cn.hollis.nft.turbo.api.order.OrderFacadeService;
import cn.hollis.nft.turbo.api.user.service.UserFacadeService;
import cn.hollis.nft.turbo.collection.domain.service.CollectionService;
import cn.hollis.nft.turbo.collection.domain.service.impl.HeldCollectionService;
import cn.hollis.nft.turbo.collection.facade.CollectionReadFacadeServiceImpl;
import cn.hollis.nft.turbo.limiter.SlidingWindowRateLimiter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.redisson.api.RedissonClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {NfTurboGoodsApplication.class})
@ActiveProfiles("test")
public class GoodsBaseTest {

    @MockBean
    protected ChainFacadeService chainFacadeService;

    @MockBean
    protected UserFacadeService userFacadeService;

    @MockBean
    protected CollectionReadFacadeServiceImpl collectionReadFacadeService;

    @MockBean
    protected OrderFacadeService orderFacadeService;

    @Test
    public void test() {

    }
}
