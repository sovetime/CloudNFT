package cn.hollis.nft.turbo.user.domain.service.config;

import cn.hollis.nft.turbo.user.domain.service.AuthService;
import cn.hollis.nft.turbo.user.domain.service.AuthServiceImpl;
import cn.hollis.nft.turbo.user.domain.service.MockAuthServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;


@Configuration
@EnableConfigurationProperties(AuthProperties.class)
public class AuthConfiguration {

    @Autowired
    private AuthProperties authProperties;

    @Bean
    @ConditionalOnMissingBean
    //环境配置注解，指定当前bean只有在特定的环境激活时才会被创建
    @Profile({"default", "prod"})
    public AuthService authService() {
        return new AuthServiceImpl(authProperties.getHost(), authProperties.getPath(), authProperties.getAppcode());
    }

    @Bean
    @ConditionalOnMissingBean
    @Profile({"dev","test"})
    public AuthService mockAuthService() {
        return new MockAuthServiceImpl();
    }

}
