package cn.time.nft.turbo.order.infrastructure.config;

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

        //是 Micrometer 框架提供的一个工具方法，用于自动收集线程池的关键性能指标，并将这些指标注册到指定的 MeterRegistry 中
        //可以通过Prometheus+Grafana进行可视化监控
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
