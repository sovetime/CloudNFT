package cn.hollis.nft.turbo.limiter.configuration;

import com.alibaba.csp.sentinel.adapter.spring.webflux.callback.BlockRequestHandler;
import com.alibaba.csp.sentinel.adapter.spring.webflux.callback.WebFluxCallbackManager;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * 为了解决这个问题：https://github.com/alibaba/Sentinel/issues/3298
 *
 * @author Hollis
 */
@Configuration
public class SentinelConfiguration {

    @PostConstruct
    public void initGatewayBlockHandler() {
        WebFluxCallbackManager.setBlockHandler(new BlockRequestHandler() {
            @Override
            public Mono<ServerResponse> handleRequest(ServerWebExchange serverWebExchange, Throwable ex) {
                return ServerResponse.ok().body(Mono.just("限流啦,请求太频繁"), String.class);
            }
        });
    }
}
