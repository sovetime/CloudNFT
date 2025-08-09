package cn.hollis.nft.turbo.box.domain.service;

import cn.hollis.nft.turbo.api.box.constant.BlindAllotBoxRule;
import cn.hollis.nft.turbo.base.utils.BeanNameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 盲盒分配规则工厂
 *
 * @author hollis
 */
@Service
public class BlindBoxRuleServiceFactory {

    @Autowired
    private final Map<String, BlindBoxRuleService> blindBoxBindServiceMap = new ConcurrentHashMap<String, BlindBoxRuleService>();

    public BlindBoxRuleService get(BlindAllotBoxRule blindAllotBoxRule) {
        String beanName = BeanNameUtils.getBeanName(blindAllotBoxRule.name(), "BlindBoxRuleService");

        //组装出beanName，并从map中获取对应的bean
        BlindBoxRuleService service = blindBoxBindServiceMap.get(beanName);

        if (service != null) {
            return service;
        } else {
            throw new UnsupportedOperationException(
                    "No BlindBoxRuleService Found With blindBoxRule : " + blindAllotBoxRule);
        }
    }
}
