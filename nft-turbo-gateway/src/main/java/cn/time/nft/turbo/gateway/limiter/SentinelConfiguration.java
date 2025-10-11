package cn.time.nft.turbo.gateway.limiter;

import com.alibaba.csp.sentinel.adapter.gateway.sc.callback.BlockRequestHandler;
import com.alibaba.csp.sentinel.adapter.gateway.sc.callback.GatewayCallbackManager;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;


@Configuration
public class SentinelConfiguration {

    //@PostConstruct 在Spring 容器实例化该类并完成依赖注入后
    //初始化网关限流的拦截处理器
    @PostConstruct
    public void initGatewayBlockHandler() {
        GatewayCallbackManager.setBlockHandler(new BlockRequestHandler() {
            @Override
            public Mono<ServerResponse> handleRequest(ServerWebExchange serverWebExchange, Throwable ex) {
                return ServerResponse.ok().body(Mono.just("限流啦,请求太频繁"), String.class);
            }
        });
    }
}
