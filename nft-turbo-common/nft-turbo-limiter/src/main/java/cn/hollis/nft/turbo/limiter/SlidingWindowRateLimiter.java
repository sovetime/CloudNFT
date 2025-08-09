package cn.hollis.nft.turbo.limiter;

import org.redisson.api.RRateLimiter;
import org.redisson.api.RateIntervalUnit;
import org.redisson.api.RateType;
import org.redisson.api.RedissonClient;


//滑动窗口限流服务
public class SlidingWindowRateLimiter implements RateLimiter {

    private RedissonClient redissonClient;

    private static final String LIMIT_KEY_PREFIX = "nft:turbo:limit:";

    public SlidingWindowRateLimiter(RedissonClient redissonClient) {
        this.redissonClient = redissonClient;
    }

    @Override
    public Boolean tryAcquire(String key, int limit, int windowSize) {
        //获取分布式限流器
        RRateLimiter rRateLimiter = redissonClient.getRateLimiter(LIMIT_KEY_PREFIX + key);

        //使用RateType.OVERALL表示集群限流策略
        if (!rRateLimiter.isExists()) {
            rRateLimiter.trySetRate(RateType.OVERALL, limit, windowSize, RateIntervalUnit.SECONDS);
        }

        //获取令牌
        return rRateLimiter.tryAcquire();
    }
}
