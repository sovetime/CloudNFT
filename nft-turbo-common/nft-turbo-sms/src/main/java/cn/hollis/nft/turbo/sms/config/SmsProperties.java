package cn.hollis.nft.turbo.sms.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;


//短信配置
@ConfigurationProperties(prefix = SmsProperties.PREFIX)
@Getter
@Setter
public class SmsProperties {
    public static final String PREFIX = "spring.sms";

    private String host;

    private String path;

    private String appcode;

    private String smsSignId;

    private String templateId;

}
