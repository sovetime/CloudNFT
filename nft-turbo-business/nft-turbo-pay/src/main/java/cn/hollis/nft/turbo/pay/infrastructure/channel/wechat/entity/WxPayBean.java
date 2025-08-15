package cn.hollis.nft.turbo.pay.infrastructure.channel.wechat.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@PropertySource("classpath:/wxpay.properties")
@ConfigurationProperties(prefix = "wxpay")
public class WxPayBean {
    private String appId;
    private String keyPath;
    private String publicKeyPath;
    private String certPath;
    private String certP12Path;
    private String platformCertPath;
    private String mchId;
    private String apiKey;
    private String apiKey3;
    private String domain;

    @Override
    public String toString() {
        return "WxPayV3Bean{" +
                "keyPath='" + keyPath + '\'' +
                ", certPath='" + certPath + '\'' +
                ", certP12Path='" + certP12Path + '\'' +
                ", platformCertPath='" + platformCertPath + '\'' +
                ", mchId='" + mchId + '\'' +
                ", apiKey='" + apiKey + '\'' +
                ", apiKey3='" + apiKey3 + '\'' +
                ", domain='" + domain + '\'' +
                '}';
    }
}
