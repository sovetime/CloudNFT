package cn.hollis.nft.turbo;

import cn.hollis.nft.turbo.api.box.service.BlindBoxManageFacadeService;
import cn.hollis.nft.turbo.api.box.service.BlindBoxReadFacadeService;
import cn.hollis.nft.turbo.api.chain.service.ChainFacadeService;
import cn.hollis.nft.turbo.api.collection.service.CollectionReadFacadeService;
import cn.hollis.nft.turbo.api.goods.service.GoodsFacadeService;
import cn.hollis.nft.turbo.api.order.OrderFacadeService;
import cn.hollis.nft.turbo.api.pay.service.PayFacadeService;
import cn.hollis.nft.turbo.api.user.service.UserFacadeService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class BusinessDubboConfiguration {

    @DubboReference(version = "1.0.0")
    private ChainFacadeService chainFacadeService;

    @DubboReference(version = "1.0.0")
    private OrderFacadeService orderFacadeService;

    @DubboReference(version = "1.0.0")
    private PayFacadeService payFacadeService;

    @DubboReference(version = "1.0.0")
    private UserFacadeService userFacadeService;

    @DubboReference(version = "1.0.0")
    private CollectionReadFacadeService collectionReadFacadeService;

    @DubboReference(version = "1.0.0")
    private GoodsFacadeService goodsFacadeService;

    @DubboReference(version = "1.0.0")
    private BlindBoxManageFacadeService blindBoxManageFacadeService;

    @DubboReference(version = "1.0.0")
    private BlindBoxReadFacadeService blindBoxReadFacadeService;


    @Bean
    @ConditionalOnMissingBean(name = "collectionFacadeService")
    public CollectionReadFacadeService collectionFacadeService() {
        return collectionReadFacadeService;
    }

    @Bean
    @ConditionalOnMissingBean(name = "userFacadeService")
    public UserFacadeService userFacadeService() {
        return userFacadeService;
    }


    @Bean
    @ConditionalOnMissingBean(name = "payFacadeService")
    public PayFacadeService payFacadeService() {
        return payFacadeService;
    }


    @Bean
    @ConditionalOnMissingBean(name = "orderFacadeService")
    public OrderFacadeService orderFacadeService() {
        return orderFacadeService;
    }

    @Bean
    @ConditionalOnMissingBean(name = "chainFacadeService")
    public ChainFacadeService chainFacadeService() {
        return chainFacadeService;
    }

    @Bean
    @ConditionalOnMissingBean(name = "goodsFacadeService")
    public GoodsFacadeService goodsFacadeService() {
        return goodsFacadeService;
    }

    @Bean
    @ConditionalOnMissingBean(name = "blindBoxManageFacadeService")
    public BlindBoxManageFacadeService blindBoxManageFacadeService() {
        return blindBoxManageFacadeService;
    }

    @Bean
    @ConditionalOnMissingBean(name = "blindBoxReadFacadeService")
    public BlindBoxReadFacadeService blindBoxReadFacadeService() {
        return blindBoxReadFacadeService;
    }

}
