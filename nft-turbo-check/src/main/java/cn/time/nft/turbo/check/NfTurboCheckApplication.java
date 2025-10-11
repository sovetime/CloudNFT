package cn.time.nft.turbo.check;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication(scanBasePackages = {"cn.time.nft.turbo.check"})
//启用 Dubbo 框架的注解功能
@EnableDubbo
public class NfTurboCheckApplication {

    public static void main(String[] args) {
        SpringApplication.run(NfTurboCheckApplication.class, args);
    }

}
