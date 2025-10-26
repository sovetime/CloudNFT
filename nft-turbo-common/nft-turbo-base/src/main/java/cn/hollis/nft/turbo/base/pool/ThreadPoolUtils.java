package cn.hollis.nft.turbo.base.pool;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.*;


public class ThreadPoolUtils {
    private static ThreadFactory smsSendFactory = new ThreadFactoryBuilder()
            .setNameFormat("demo-pool-%d").build();

    private static ExecutorService smsSendPool = new ThreadPoolExecutor(5, 20,
            0L, TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<Runnable>(1024), smsSendFactory, new ThreadPoolExecutor.AbortPolicy());

    public static ExecutorService getSmsSendPool() {
        return smsSendPool;
    }
}
