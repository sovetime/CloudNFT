package cn.hollis.nft.turbo.goods;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author hollis
 */
@SpringBootApplication(scanBasePackages = {"cn.hollis.nft.turbo.goods","cn.hollis.nft.turbo.collection","cn.hollis.nft.turbo.box"})
@EnableDubbo(scanBasePackages = {"cn.hollis.nft.turbo.goods","cn.hollis.nft.turbo.collection","cn.hollis.nft.turbo.box"})
public class NfTurboGoodsApplication {

    public static void main(String[] args) {
        SpringApplication.run(NfTurboGoodsApplication.class, args);
    }

}
