package cn.time.nft.turbo.chain.domain.constant;

import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


//文昌链配置
@Component
@Setter
@ConfigurationProperties(prefix = "nft.turbo.chain.wenchang")
public class WenChangChainConfiguration {

    private String host;

    private String apiKey;

    private String apiSecret;

    private String chainAddrSuper;

    public String host() {
        return host;
    }

    public String apiKey() {
        return apiKey;
    }

    public String apiSecret() {
        return apiSecret;
    }

    public String chainAddrSuper() {
        return chainAddrSuper;
    }

}
