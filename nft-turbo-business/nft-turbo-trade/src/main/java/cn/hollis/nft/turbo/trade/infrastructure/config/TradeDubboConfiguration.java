package cn.hollis.nft.turbo.trade.infrastructure.config;

import cn.hollis.nft.turbo.api.check.service.InventoryCheckFacadeService;
import cn.hollis.nft.turbo.api.goods.service.GoodsFacadeService;
import cn.hollis.nft.turbo.api.goods.service.GoodsTransactionFacadeService;
import cn.hollis.nft.turbo.api.inventory.InventoryTransactionFacadeService;
import cn.hollis.nft.turbo.api.inventory.service.InventoryFacadeService;
import cn.hollis.nft.turbo.api.order.OrderFacadeService;
import cn.hollis.nft.turbo.api.order.OrderTransactionFacadeService;
import cn.hollis.nft.turbo.api.pay.service.PayFacadeService;
import cn.hollis.nft.turbo.api.user.service.UserFacadeService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Hollis
 */
@Configuration
public class TradeDubboConfiguration {

    @DubboReference(version = "1.0.0")
    private OrderFacadeService orderFacadeService;

    @DubboReference(version = "1.0.0")
    private PayFacadeService payFacadeService;

    @DubboReference(version = "1.0.0")
    private GoodsFacadeService goodsFacadeService;

    @DubboReference(version = "1.0.0")
    private InventoryFacadeService inventoryFacadeService;

    @DubboReference(version = "1.0.0")
    private UserFacadeService userFacadeService;

    @DubboReference(version = "1.0.0")
    private OrderTransactionFacadeService orderTransactionFacadeService;

    @DubboReference(version = "1.0.0")
    private InventoryTransactionFacadeService inventoryTransactionFacadeService;

    @DubboReference(version = "1.0.0")
    private InventoryCheckFacadeService inventoryCheckFacadeService;

    @DubboReference(version = "1.0.0")
    private GoodsTransactionFacadeService goodsTransactionFacadeService;

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
    @ConditionalOnMissingBean(name = "goodsFacadeService")
    public GoodsFacadeService goodsFacadeService() {
        return goodsFacadeService;
    }

    @Bean
    @ConditionalOnMissingBean(name = "inventoryFacadeService")
    public InventoryFacadeService inventoryFacadeService() {
        return inventoryFacadeService;
    }

    @Bean
    @ConditionalOnMissingBean(name = "userFacadeService")
    public UserFacadeService userFacadeService() {
        return userFacadeService;
    }

    @Bean
    @ConditionalOnMissingBean(name = "orderTransactionFacadeService")
    public OrderTransactionFacadeService orderTransactionFacadeService() {
        return orderTransactionFacadeService;
    }

    @Bean
    @ConditionalOnMissingBean(name = "goodsTransactionFacadeService")
    public GoodsTransactionFacadeService goodsTransactionFacadeService() {
        return goodsTransactionFacadeService;
    }

    @Bean
    @ConditionalOnMissingBean(name = "inventoryTransactionFacadeService")
    public InventoryTransactionFacadeService inventoryTransactionFacadeService() {
        return inventoryTransactionFacadeService;
    }

    @Bean
    @ConditionalOnMissingBean(name = "inventoryCheckFacadeService")
    public InventoryCheckFacadeService inventoryCheckFacadeService() {
        return inventoryCheckFacadeService;
    }
}
