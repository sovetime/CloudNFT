package cn.hollis.nft.turbo.pay.domain.service;

import cn.hollis.nft.turbo.pay.domain.entity.WechatTransaction;
import cn.hollis.nft.turbo.pay.infrastructure.mapper.WechatTransactionMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class WechatTransactionService extends ServiceImpl<WechatTransactionMapper, WechatTransaction> {
    private static final Logger logger = LoggerFactory.getLogger(PayOrderService.class);

    @Autowired
    private WechatTransactionMapper wechatTransactionMapper;

    @Override
    public WechatTransactionMapper getBaseMapper() {
        return this.wechatTransactionMapper;
    }
}
