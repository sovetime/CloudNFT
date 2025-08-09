package cn.hollis.nft.turbo.file.config;

import cn.hollis.nft.turbo.file.FileService;
import cn.hollis.nft.turbo.file.MockFileServiceImpl;
import cn.hollis.nft.turbo.file.OssServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;


@Configuration
@EnableConfigurationProperties(OssProperties.class)
public class OssConfiguration {

    @Autowired
    private OssProperties properties;

    @Bean
    @ConditionalOnMissingBean
    @Profile({"default", "prod"})
    public FileService ossService() {
        OssServiceImpl ossService = new OssServiceImpl();
        ossService.setBucket(properties.getBucket());
        ossService.setEndPoint(properties.getEndPoint());
        ossService.setAccessKey(properties.getAccessKey());
        ossService.setAccessSecret(properties.getAccessSecret());
        return ossService;
    }

    @Bean
    @ConditionalOnMissingBean
    @Profile({"dev", "test"})
    public FileService mockFileService() {
        return new MockFileServiceImpl();
    }
}
