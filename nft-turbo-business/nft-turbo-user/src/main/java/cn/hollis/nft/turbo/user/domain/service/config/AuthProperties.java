package cn.hollis.nft.turbo.user.domain.service.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 短信配置
 *
 * @author hollis
 */
@ConfigurationProperties(prefix = AuthProperties.PREFIX)
public class AuthProperties {
    public static final String PREFIX = "spring.auth";

    private String host;

    private String path;

    private String appcode;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getAppcode() {
        return appcode;
    }

    public void setAppcode(String appcode) {
        this.appcode = appcode;
    }

}
