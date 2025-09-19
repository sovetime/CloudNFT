package cn.hollis.nft.turbo.order.configuration;

import cn.hollis.nft.turbo.api.goods.service.GoodsFacadeService;
import cn.hollis.nft.turbo.api.inventory.service.InventoryFacadeService;
import cn.hollis.nft.turbo.api.user.service.UserFacadeService;
import cn.hollis.nft.turbo.order.sharding.id.WorkerIdHolder;
import cn.hollis.nft.turbo.order.validator.*;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.web.context.WebApplicationContext;


@Configuration
public class OrderClientConfiguration {

    @Bean
    public WorkerIdHolder workerIdHolder(RedissonClient redisson) {
        return new WorkerIdHolder(redisson);
    }

    @Bean
    //@Scope Spring框架中的注解，用户
    @Scope(value = BeanDefinition.SCOPE_PROTOTYPE)
    public GoodsValidator goodsValidator(GoodsFacadeService goodsFacadeService) {
        return new GoodsValidator(goodsFacadeService);
    }

    @Bean
    @Scope(value = BeanDefinition.SCOPE_PROTOTYPE)
    public StockValidator stockValidator(InventoryFacadeService inventoryFacadeService) {
        return new StockValidator(inventoryFacadeService);
    }

    @Bean
    @Scope(value = BeanDefinition.SCOPE_PROTOTYPE)
    public UserValidator userValidator(UserFacadeService userFacadeService) {
        return new UserValidator(userFacadeService);
    }

    @Bean
    @Scope(value = BeanDefinition.SCOPE_PROTOTYPE)
    public GoodsBookValidator goodsBookValidator(GoodsFacadeService goodsFacadeService) {
        return new GoodsBookValidator(goodsFacadeService);
    }

    @Bean
    public OrderCreateValidator orderValidatorChain(UserValidator userValidator, GoodsValidator goodsValidator, GoodsBookValidator goodsBookValidator) {
        userValidator.setNext(goodsValidator);
        goodsValidator.setNext(goodsBookValidator);
        return userValidator;
    }
}
