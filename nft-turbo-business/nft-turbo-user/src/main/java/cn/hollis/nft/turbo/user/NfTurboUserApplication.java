package cn.hollis.nft.turbo.user;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author hollis
 */
@SpringBootApplication(scanBasePackages = "cn.hollis.nft.turbo.user")
@EnableDubbo
public class NfTurboUserApplication {

    public static void main(String[] args) {
        SpringApplication.run(NfTurboUserApplication.class, args);
    }

}
