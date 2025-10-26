package cn.hollis.nft.turbo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication(scanBasePackages = "cn.hollis.nft.turbo")
public class NfTurboBusinessApplication {

    public static void main(String[] args) {
        SpringApplication.run(NfTurboBusinessApplication.class, args);
    }

}
