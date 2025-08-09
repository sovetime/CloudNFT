package cn.hollis.nft.turbo.order.domain.validator;

import cn.hollis.nft.turbo.order.validator.GoodsBookValidator;
import cn.hollis.nft.turbo.order.validator.GoodsValidator;
import cn.hollis.nft.turbo.order.validator.OrderCreateValidator;
import cn.hollis.nft.turbo.order.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 订单创建校验器配置
 *
 * @author hollis
 */
@Configuration
public class OrderCreateAndConfirmValidatorConfig {

    @Autowired
    private GoodsValidator goodsValidator;

    @Autowired
    private UserValidator userValidator;

    @Bean
    public OrderCreateValidator orderConfirmValidatorChain() {
        userValidator.setNext(goodsValidator);
        return userValidator;
    }
}
