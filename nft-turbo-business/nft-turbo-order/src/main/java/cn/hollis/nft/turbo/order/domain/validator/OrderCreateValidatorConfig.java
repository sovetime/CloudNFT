package cn.hollis.nft.turbo.order.domain.validator;

import cn.hollis.nft.turbo.order.validator.GoodsBookValidator;
import cn.hollis.nft.turbo.order.validator.GoodsValidator;
import cn.hollis.nft.turbo.order.validator.OrderCreateValidator;
import cn.hollis.nft.turbo.order.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


//订单创建校验器配置
@Configuration
public class OrderCreateValidatorConfig {

    @Autowired
    private GoodsValidator goodsValidator;

    @Autowired
    private UserValidator userValidator;

    @Autowired
    private GoodsBookValidator goodsBookValidator;

    @Bean
    public OrderCreateValidator orderValidatorChain() {
        userValidator.setNext(goodsValidator);
        goodsValidator.setNext(goodsBookValidator);
        return userValidator;
    }
}
