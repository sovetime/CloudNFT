package cn.time.nft.turbo.goods;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication(scanBasePackages = {"cn.time.nft.turbo.goods","cn.time.nft.turbo.collection","cn.time.nft.turbo.box"})
@EnableDubbo(scanBasePackages = {"cn.time.nft.turbo.goods","cn.time.nft.turbo.collection","cn.time.nft.turbo.box"})
public class NfTurboGoodsApplication {

    public static void main(String[] args) {
        SpringApplication.run(NfTurboGoodsApplication.class, args);
    }

}
