package cn.hollis.nft.turbo.pay.infrastructure;

import cn.hollis.nft.turbo.api.chain.service.ChainFacadeService;
import cn.hollis.nft.turbo.api.collection.service.CollectionReadFacadeService;
import cn.hollis.nft.turbo.api.collection.service.CollectionManageFacadeService;
import cn.hollis.nft.turbo.api.goods.service.GoodsFacadeService;
import cn.hollis.nft.turbo.api.order.OrderFacadeService;
import cn.hollis.nft.turbo.api.user.service.UserFacadeService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Hollis
 */
@Configuration
public class PayDubboConfiguration {

    @DubboReference(version = "1.0.0")
    private CollectionReadFacadeService collectionReadFacadeService;

    @Bean
    @ConditionalOnMissingBean(name = "collectionFacadeService")
    public CollectionReadFacadeService collectionFacadeService() {
        return collectionReadFacadeService;
    }

    @DubboReference(version = "1.0.0")
    private UserFacadeService userFacadeService;

    @Bean
    @ConditionalOnMissingBean(name = "userFacadeService")
    public UserFacadeService userFacadeService() {
        return userFacadeService;
    }

    @DubboReference(version = "1.0.0")
    private OrderFacadeService orderFacadeService;

    @Bean
    @ConditionalOnMissingBean(name = "orderFacadeService")
    public OrderFacadeService orderFacadeService() {
        return this.orderFacadeService;
    }

    @DubboReference(version = "1.0.0")
    private ChainFacadeService chainFacadeService;

    @Bean
    @ConditionalOnMissingBean(name = "chainFacadeService")
    public ChainFacadeService chainFacadeService() {
        return this.chainFacadeService;
    }

    @DubboReference(version = "1.0.0")
    private CollectionManageFacadeService collectionManageFacadeService;

    @Bean
    @ConditionalOnMissingBean(name = "collectionManageFacadeService")
    public CollectionManageFacadeService collectionManageFacadeService() {
        return collectionManageFacadeService;
    }

    @DubboReference(version = "1.0.0")
    private GoodsFacadeService goodsFacadeService;

    @Bean
    @ConditionalOnMissingBean(name = "goodsFacadeService")
    public GoodsFacadeService goodsFacadeService() {
        return goodsFacadeService;
    }
}
