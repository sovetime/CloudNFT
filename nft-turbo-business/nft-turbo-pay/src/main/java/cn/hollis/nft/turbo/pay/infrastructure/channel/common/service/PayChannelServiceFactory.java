package cn.hollis.nft.turbo.pay.infrastructure.channel.common.service;

import cn.hollis.nft.turbo.api.pay.constant.PayChannel;
import cn.hollis.nft.turbo.base.utils.BeanNameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static cn.hollis.nft.turbo.base.constant.ProfileConstant.PROFILE_DEV;


//支付渠道服务工厂
@Service
public class PayChannelServiceFactory {

    //用于存储支付渠道服务的映射关系，键是服务的 beanName，值是PayChannelService的实例
    @Autowired
    //这里使用@Autowired配合PayChannelService接口实现的@Service 会自动注入相应逻辑的映射关系
    //示例 "wechatPayChannelService", WxPayChannelServiceImpl()
    private final Map<String, PayChannelService> serviceMap = new ConcurrentHashMap<String, PayChannelService>();

    @Value("${spring.profiles.active}")
    private String profile;

    //payChannel 传入支付渠道
    public PayChannelService get(PayChannel payChannel) {

        //在开发环境获取mock 服务
        if (PROFILE_DEV.equals(profile)) {
            return serviceMap.get("mockPayChannelService");
        }

        //获取支付渠道对应的beanName，支付渠道是前端传入的字符串，这里需要进行转换
        //示例 Wechat，PayChannelService-> wechatPayChannelService
        String beanName = BeanNameUtils.getBeanName(payChannel.name(), "PayChannelService");

        //组装出beanName，并从map中获取对应的bean
        PayChannelService payChannelService = serviceMap.get(beanName);

        if (payChannelService != null) {
            return payChannelService;
        } else {
            throw new UnsupportedOperationException("No PayChannelService Found With payChannel : " + payChannel + " , beanName : " + beanName);
        }
    }
}
