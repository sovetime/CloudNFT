package cn.hollis.nft.turbo.order.sharding.id;

import org.apache.commons.lang3.StringUtils;
import org.redisson.api.RAtomicLong;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;


//用于生成和管理工作者ID
//实现了CommandLineRunner接口，在应用启动时自动执行
//通过Redisson客户端获取原子长整型值，用于生成唯一的工作者ID
public class WorkerIdHolder implements CommandLineRunner {

    private RedissonClient redissonClient;

    @Value("${order.client.name:workerId}")
    private String clientName;

    public static long WORKER_ID;

    public WorkerIdHolder(RedissonClient redissonClient) {
        this.redissonClient = redissonClient;
    }

    /**
     * 应用启动时执行的方法，用于初始化工作者ID
     * 从Redisson获取原子长整型值，递增后对32取模作为工作者ID
     * @param args 命令行参数数组
     * @throws Exception 执行过程中可能抛出的异常
     */
    @Override
    public void run(String... args) throws Exception {
        // 获取Redisson原子长整型对象，用于生成分布式唯一的工作者ID
        RAtomicLong atomicLong = redissonClient.getAtomicLong(clientName);
        // 递增原子值并取模32，确保工作者ID在0-31范围内
        WORKER_ID = atomicLong.incrementAndGet() % 32;
    }
}
