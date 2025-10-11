package cn.time.nft.turbo.order;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication(scanBasePackages = "cn.time.nft.turbo.order")
@EnableDubbo
public class NfTurboOrderApplication {

    public static void main(String[] args) {
        SpringApplication.run(NfTurboOrderApplication.class, args);
    }

}
