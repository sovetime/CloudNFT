package cn.hollis.nft.turbo.inventory;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication(scanBasePackages = "cn.hollis.nft.turbo.inventory")
@EnableDubbo
public class NfTurboInventoryApplication {

    public static void main(String[] args) {
        SpringApplication.run(NfTurboInventoryApplication.class, args);
    }

}
