package cn.hollis.nft.turbo.trade.validator;

import cn.hollis.nft.turbo.order.validator.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


//订单创建前置校验器配置
@Configuration
public class OrderPreValidatorConfig {

    @Autowired
    private GoodsValidator goodsValidator;

    @Autowired
    private UserValidator userValidator;

    @Autowired
    private GoodsBookValidator goodsBookValidator;

    @Autowired
    private StockValidator stockValidator;

    @Bean
    public OrderCreateValidator orderPreValidatorChain() {
        userValidator.setNext(goodsValidator);
        goodsValidator.setNext(stockValidator);
        stockValidator.setNext(goodsBookValidator);
        return userValidator;
    }

}
