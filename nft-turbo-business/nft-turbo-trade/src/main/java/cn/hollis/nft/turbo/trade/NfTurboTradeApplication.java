package cn.hollis.nft.turbo.trade;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author hollis
 */
@SpringBootApplication(scanBasePackages = "cn.hollis.nft.turbo.trade")
@EnableDubbo
public class NfTurboTradeApplication {

    public static void main(String[] args) {
        SpringApplication.run(NfTurboTradeApplication.class, args);
    }

}
