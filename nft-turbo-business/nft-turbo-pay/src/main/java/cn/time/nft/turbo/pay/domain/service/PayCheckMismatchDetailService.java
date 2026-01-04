package cn.time.nft.turbo.pay.domain.service;

import cn.time.nft.turbo.pay.domain.entity.PayCheckMismatchDetail;
import cn.time.nft.turbo.pay.infrastructure.mapper.PayCheckMismatchDetailMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PayCheckMismatchDetailService extends ServiceImpl<PayCheckMismatchDetailMapper, PayCheckMismatchDetail> {
    private static final Logger logger = LoggerFactory.getLogger(PayCheckMismatchDetailService.class);

    @Autowired
    private PayCheckMismatchDetailMapper payCheckMismatchDetailMapper;

    @Override
    public PayCheckMismatchDetailMapper getBaseMapper() {
        return this.payCheckMismatchDetailMapper;
    }
}
