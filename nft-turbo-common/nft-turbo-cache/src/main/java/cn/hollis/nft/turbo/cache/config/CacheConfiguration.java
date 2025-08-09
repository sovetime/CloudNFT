package cn.hollis.nft.turbo.cache.config;

import com.alicp.jetcache.anno.config.EnableMethodCache;
import org.springframework.context.annotation.Configuration;


// 缓存配置
@Configuration
// JetCache 提供注解，开启方法级别缓存，指定扫描所有匹配路径下的包
@EnableMethodCache(basePackages = "cn.hollis.nft.turbo")
public class CacheConfiguration {
}
