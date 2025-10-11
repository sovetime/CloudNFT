package cn.time.nft.turbo.notice;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author time
 */
@SpringBootApplication(scanBasePackages = "cn.time.nft.turbo.notice")
@EnableDubbo
public class NfTurboNoticeApplication {

    public static void main(String[] args) {
        SpringApplication.run(NfTurboNoticeApplication.class, args);
    }

}
