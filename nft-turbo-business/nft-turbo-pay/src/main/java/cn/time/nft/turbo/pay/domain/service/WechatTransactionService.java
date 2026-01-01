package cn.time.nft.turbo.pay.domain.service;

import cn.time.nft.turbo.pay.domain.entity.WechatTransaction;
import cn.time.nft.turbo.pay.infrastructure.mapper.WechatTransactionMapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
@Slf4j
public class WechatTransactionService extends ServiceImpl<WechatTransactionMapper, WechatTransaction> {

    @Autowired
    private WechatTransactionMapper wechatTransactionMapper;

    @Override
    public WechatTransactionMapper getBaseMapper() {
        return this.wechatTransactionMapper;
    }

    public WechatTransaction queryByMchOrderNo(String mchOrderNo) {
        return wechatTransactionMapper.selectOne(new QueryWrapper<WechatTransaction>()
                .eq("mch_order_no", mchOrderNo)
        );
    }
}
