package cn.hollis.nft.turbo.trade.validator;

import cn.hollis.nft.turbo.order.validator.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//之前这个责任链和orderValidatorChain之间的主要区别是会额外做一次库存校验，
// 但是目前看调用这个责任链的地方主要是newBuy、newBuyPlus等接口，但是这些接口会同步扣减库存的，库存扣减的lua中已经做了防超卖，
// 所以这里会多一次额外的redis请求，其实可以精简掉，所以这个类暂时先废弃，统一用orderValidatorChain
//订单创建前置校验器配置
@Configuration
@Deprecated
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
