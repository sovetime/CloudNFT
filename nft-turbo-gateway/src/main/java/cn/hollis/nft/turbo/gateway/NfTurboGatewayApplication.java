package cn.hollis.nft.turbo.gateway;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication(scanBasePackages = "cn.hollis.nft.turbo.gateway")
public class NfTurboGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(NfTurboGatewayApplication.class, args);
    }

}
