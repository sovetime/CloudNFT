package auth;

import cn.hollis.nft.turbo.user.NfTurboUserApplication;
import com.alicp.jetcache.CacheManager;
import com.alicp.jetcache.redisson.RedissonCache;
import com.alicp.jetcache.redisson.RedissonCacheConfig;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.redisson.api.RedissonClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {NfTurboUserApplication.class})
@ActiveProfiles("test")
public class UserBaseTest {

    @MockBean
    private RedissonClient redissonClient;

    @MockBean
    private CacheManager cacheManager;

    @Test
    public void test(){

    }
}
