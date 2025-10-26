package cn.hollis.nft.turbo.tcc.config;

import cn.hollis.nft.turbo.tcc.service.TransactionLogService;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@MapperScan("cn.hollis.nft.turbo.tcc.mapper")
public class TccConfiguration {

    @Bean
    public TransactionLogService transactionLogService() {
        return new TransactionLogService();
    }
}
