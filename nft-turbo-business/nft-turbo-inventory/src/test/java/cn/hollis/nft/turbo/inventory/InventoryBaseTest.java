package cn.hollis.nft.turbo.inventory;

import cn.hollis.nft.turbo.api.chain.service.ChainFacadeService;
import cn.hollis.nft.turbo.api.collection.service.CollectionReadFacadeService;
import cn.hollis.nft.turbo.api.order.OrderFacadeService;
import cn.hollis.nft.turbo.api.user.service.UserFacadeService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.redisson.api.RedissonClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {NfTurboInventoryApplication.class})
@ActiveProfiles("test")
public class InventoryBaseTest {

    @MockBean
    private RedissonClient redissonClient;

    @MockBean
    private ChainFacadeService chainFacadeService;

    @MockBean
    private UserFacadeService userFacadeService;

    @MockBean
    private CollectionReadFacadeService collectionReadFacadeService;

    @MockBean
    private OrderFacadeService orderFacadeService;

    @Test
    public void test(){

    }
}
