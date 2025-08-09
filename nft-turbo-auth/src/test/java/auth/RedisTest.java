package auth;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

public class RedisTest extends AuthBaseTest {

    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void testRedisConnect() {

        redisTemplate.opsForValue().set("auth", "auth");

        Assert.assertTrue(redisTemplate.opsForValue().get("auth").equals("auth"));

        if (redisTemplate.opsForValue().get("auth").equals("auth")) {
            System.out.println("redis connect success");
        }
    }
}
