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

/**
 * @author Hollis
 */
@Configuration
@EnableConfigurationProperties(AuthProperties.class)
public class AuthConfiguration {

    @Autowired
    private AuthProperties authProperties;

    @Bean
    @ConditionalOnMissingBean
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
