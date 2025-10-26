package cn.hollis.nft.turbo.chain.domain.constant;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 文昌链配置
 *
 * @author wswyb001
 * @date 2024/01/17
 */
@Component
@ConfigurationProperties(prefix = "nft.turbo.chain.wenchang")
public class WenChangChainConfiguration {

    private String host;

    private String apiKey;

    private String apiSecret;

    private String chainAddrSuper;

    public String host() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String apiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String apiSecret() {
        return apiSecret;
    }

    public void setApiSecret(String apiSecret) {
        this.apiSecret = apiSecret;
    }

    public String chainAddrSuper() {
        return chainAddrSuper;
    }

    public void setChainAddrSuper(String chainAddrSuper) {
        this.chainAddrSuper = chainAddrSuper;
    }
}
