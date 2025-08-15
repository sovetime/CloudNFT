package cn.hollis.nft.turbo.order.infrastructure.config;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.binder.jvm.ExecutorServiceMetrics;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Configuration
public class ThreadPoolConfiguration {

    @Bean
    public ThreadPoolExecutor newBuyConsumePool(MeterRegistry registry) {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                20,
                32,
                60, TimeUnit.SECONDS,
                new LinkedBlockingQueue<>());

        ExecutorServiceMetrics.monitor(registry, executor, "newBuyConsumePool");
        return executor;
    }

    @Bean
    public ThreadPoolExecutor newBuyPlusConsumePool(MeterRegistry registry) {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                20,
                32,
                60, TimeUnit.SECONDS,
                new LinkedBlockingQueue<>());

        ExecutorServiceMetrics.monitor(registry, executor, "newBuyPlusConsumePool");
        return executor;
    }
}
