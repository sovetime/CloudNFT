package cn.hollis.nft.turbo.pay.domain.service;

import cn.hollis.nft.turbo.api.pay.constant.PayOrderState;
import cn.hollis.nft.turbo.api.pay.request.PayCreateRequest;
import cn.hollis.nft.turbo.base.exception.BizException;
import cn.hollis.nft.turbo.base.exception.RepoErrorCode;
import cn.hollis.nft.turbo.pay.domain.entity.PayOrder;
import cn.hollis.nft.turbo.pay.domain.event.PaySuccessEvent;
import cn.hollis.nft.turbo.pay.domain.event.RefundSuccessEvent;
import cn.hollis.nft.turbo.pay.infrastructure.mapper.PayOrderMapper;
import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;


@Service
public class PayOrderService extends ServiceImpl<PayOrderMapper, PayOrder> {
    private static final Logger logger = LoggerFactory.getLogger(PayOrderService.class);

    @Autowired
    private PayOrderMapper payOrderMapper;

    //创建支付蛋
    public PayOrder create(PayCreateRequest payCreateRequest) {
        //查询支付订单
        PayOrder existPayOrder = payOrderMapper.selectByBizNoAndPayer(
                payCreateRequest.getPayerId(), payCreateRequest.getBizNo(),
                payCreateRequest.getBizType().name(), payCreateRequest.getPayChannel().name());

        if (existPayOrder != null) {
            if (existPayOrder.getOrderState() != PayOrderState.EXPIRED) {
                return existPayOrder;
            }
        }

        //创建支付订单
        PayOrder payOrder = PayOrder.create(payCreateRequest);
        //保存到数据库中
        boolean saveResult = save(payOrder);
        Assert.isTrue(saveResult, () -> new BizException(RepoErrorCode.INSERT_FAILED));

        return payOrder;
    }


    public Boolean paying(String payOrderId, String payUrl) {
        PayOrder payOrder = payOrderMapper.selectByPayOrderId(payOrderId);
        payOrder.paying(payUrl);

        boolean saveResult = saveOrUpdate(payOrder);
        Assert.isTrue(saveResult, () -> new BizException(RepoErrorCode.UPDATE_FAILED));

        return true;
    }

    public Boolean paySuccess(PaySuccessEvent paySuccessEvent) {
        PayOrder payOrder = payOrderMapper.selectByPayOrderId(paySuccessEvent.getPayOrderId());
        payOrder.paySuccess(paySuccessEvent);

        boolean saveResult = saveOrUpdate(payOrder);
        Assert.isTrue(saveResult, () -> new BizException(RepoErrorCode.UPDATE_FAILED));

        return true;
    }

    public Boolean payExpired(String payOrderId) {
        PayOrder payOrder = payOrderMapper.selectByPayOrderId(payOrderId);
        payOrder.payExpired();

        boolean saveResult = saveOrUpdate(payOrder);
        Assert.isTrue(saveResult, () -> new BizException(RepoErrorCode.UPDATE_FAILED));

        return true;
    }

    public Boolean payFailed(String payOrderId) {
        PayOrder payOrder = payOrderMapper.selectByPayOrderId(payOrderId);
        payOrder.payFailed();

        boolean saveResult = saveOrUpdate(payOrder);
        Assert.isTrue(saveResult, () -> new BizException(RepoErrorCode.UPDATE_FAILED));

        return true;
    }

    public Boolean refundSuccess(RefundSuccessEvent refundSuccessEvent) {
        PayOrder payOrder = payOrderMapper.selectByPayOrderId(refundSuccessEvent.getPayOrderId());
        payOrder.refundSuccess(refundSuccessEvent);

        boolean saveResult = saveOrUpdate(payOrder);
        Assert.isTrue(saveResult, () -> new BizException(RepoErrorCode.UPDATE_FAILED));

        return true;
    }

    public List<PayOrder> queryByBizNo(String bizNo, String bizOrderType, String payerId, PayOrderState payOrderState) {
        QueryWrapper<PayOrder> queryWrapper = new QueryWrapper();
        queryWrapper.eq("biz_no", bizNo);
        queryWrapper.eq("biz_type", bizOrderType);
        queryWrapper.eq("payer_id", payerId);
        queryWrapper.eq("order_state", payOrderState.name());

        return this.list(queryWrapper);
    }

    public PayOrder queryByOrderId(String payOrderId) {
        QueryWrapper<PayOrder> queryWrapper = new QueryWrapper();
        queryWrapper.eq("pay_order_id", payOrderId);
        return this.getOne(queryWrapper);
    }

    public PayOrder queryByOrderIdAndPayer(String payOrderId, String payerId) {
        QueryWrapper<PayOrder> queryWrapper = new QueryWrapper();
        queryWrapper.eq("pay_order_id", payOrderId);
        queryWrapper.eq("payer_id", payerId);
        return this.getOne(queryWrapper);
    }

    public List<PayOrder> pageQueryTimeoutOrders(int pageSize, Long minId) {
        QueryWrapper<PayOrder> wrapper = new QueryWrapper<>();
        wrapper.eq("order_state", PayOrderState.PAYING);
        wrapper.lt("gmt_create", DateUtils.addMinutes(new Date(), -PayOrder.DEFAULT_TIME_OUT_MINUTES));
        if (minId != null) {
            wrapper.ge("id", minId);
        }
        wrapper.orderBy(true, true, "gmt_create");
        wrapper.last("limit " + pageSize);

        return this.list(wrapper);
    }
}
