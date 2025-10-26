package cn.hollis.nft.turbo.order.sharding.id;

import org.redisson.api.RAtomicLong;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;


//应用启动时自动生成一个分布式的 Worker ID，并把它存在一个静态变量里，方便后续用来生成唯一 ID
//实现了CommandLineRunner接口，Spring Boot 启动时会自动调用 run()
public class WorkerIdHolder implements CommandLineRunner {

    private RedissonClient redissonClient;

    //默认值workerId
    @Value("${order.client.name:workerId}")
    private String clientName;

    public static long WORKER_ID;

    public WorkerIdHolder(RedissonClient redissonClient) {
        this.redissonClient = redissonClient;
    }

    //从Redisson获取原子长整型值，递增后对32取模作为工作者ID
    @Override
    public void run(String... args) throws Exception {
        // 获取分布式对象，clientName为redis key-value的key
        //计时器是全局共享的，value是根据redis 中全局自增计数器生成的
        RAtomicLong atomicLong = redissonClient.getAtomicLong(clientName);
        // 递增原子值并取模32，确保工作者ID在0-31范围内
        WORKER_ID = atomicLong.incrementAndGet() % 32;
    }
}
