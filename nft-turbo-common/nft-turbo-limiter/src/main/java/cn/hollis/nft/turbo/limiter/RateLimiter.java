package cn.hollis.nft.turbo.limiter;


//限流服务
public interface RateLimiter {

    //判断一个key是否可以通过  key:限流key  limit:限流数量  windowSize:窗口大小
    public Boolean tryAcquire(String key, int limit, int windowSize);
}
