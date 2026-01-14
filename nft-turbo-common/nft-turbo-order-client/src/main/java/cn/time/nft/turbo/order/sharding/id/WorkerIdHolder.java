package cn.time.nft.turbo.order.sharding.id;

import org.redisson.api.RAtomicLong;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;


//应用启动时自动生成一个分布式的 Worker ID，并把它存在一个静态变量里，方便后续用来生成唯一 ID
//实现了CommandLineRunner接口，Spring Boot 启动时会自动调用 run()
public class WorkerIdHolder implements CommandLineRunner {

    private RedissonClient redissonClient;

    @Value("${order.client.name:workerId}")
    private String clientName;

    public static long WORKER_ID;

    public WorkerIdHolder(RedissonClient redissonClient) {
        this.redissonClient = redissonClient;
    }

    @Override
    public void run(String... args) throws Exception {
        //从redis中获取自增id,全局唯一
        RAtomicLong atomicLong = redissonClient.getAtomicLong(clientName);
        //对自增id取32，雪花算法限制机器id32位
        WORKER_ID = atomicLong.incrementAndGet() % 32;
    }
}
