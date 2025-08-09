package chain;

import cn.hollis.nft.turbo.chain.NfTurboChainApplication;
import cn.hollis.nft.turbo.limiter.SlidingWindowRateLimiter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.redisson.api.RedissonClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {NfTurboChainApplication.class})
@ActiveProfiles("test")
public class ChainBaseTest {
    @MockBean
    protected SlidingWindowRateLimiter slidingWindowRateLimiter;


    @MockBean
    private RedissonClient redissonClient;

    @Test
    public void test(){

    }
}
