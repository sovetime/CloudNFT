package cn.hollis.nft.turbo.chain;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author hollis
 */
@SpringBootApplication(scanBasePackages = "cn.hollis.nft.turbo.chain")
@EnableDubbo
public class NfTurboChainApplication {

    public static void main(String[] args) {
        SpringApplication.run(NfTurboChainApplication.class, args);
    }

}
