package cn.hollis.nft.turbo.pay.facade.service;

import cn.hollis.nft.turbo.api.pay.constant.PayErrorCode;
import cn.hollis.nft.turbo.api.pay.constant.PayOrderState;
import cn.hollis.nft.turbo.api.pay.model.PayOrderVO;
import cn.hollis.nft.turbo.api.pay.request.PayCreateRequest;
import cn.hollis.nft.turbo.api.pay.request.PayQueryByBizNo;
import cn.hollis.nft.turbo.api.pay.request.PayQueryCondition;
import cn.hollis.nft.turbo.api.pay.request.PayQueryRequest;
import cn.hollis.nft.turbo.api.pay.response.PayCreateResponse;
import cn.hollis.nft.turbo.api.pay.service.PayFacadeService;
import cn.hollis.nft.turbo.base.exception.BizException;
import cn.hollis.nft.turbo.base.exception.RepoErrorCode;
import cn.hollis.nft.turbo.base.response.MultiResponse;
import cn.hollis.nft.turbo.base.response.SingleResponse;
import cn.hollis.nft.turbo.base.utils.MoneyUtils;
import cn.hollis.nft.turbo.lock.DistributeLock;
import cn.hollis.nft.turbo.pay.domain.entity.PayOrder;
import cn.hollis.nft.turbo.pay.domain.entity.convertor.PayOrderConvertor;
import cn.hollis.nft.turbo.pay.domain.service.PayOrderService;
import cn.hollis.nft.turbo.pay.infrastructure.channel.common.request.PayChannelRequest;
import cn.hollis.nft.turbo.pay.infrastructure.channel.common.response.PayChannelResponse;
import cn.hollis.nft.turbo.pay.infrastructure.channel.common.service.PayChannelServiceFactory;
import cn.hollis.nft.turbo.rpc.facade.Facade;
import cn.hutool.core.lang.Assert;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.dubbo.config.annotation.DubboService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author Hollis
 */
@DubboService(version = "1.0.0")
public class PayFacadeServiceImpl implements PayFacadeService {

    private static final Logger logger = LoggerFactory.getLogger(PayFacadeServiceImpl.class);

    @Autowired
    private PayOrderService payOrderService;

    @Autowired
    private PayChannelServiceFactory payChannelServiceFactory;

    @Facade
    @DistributeLock(keyExpression = "#payCreateRequest.bizNo", scene = "GENERATE_PAY_URL")
    @Override
    public PayCreateResponse generatePayUrl(PayCreateRequest payCreateRequest) {
        PayCreateResponse response = new PayCreateResponse();
        PayOrder payOrder = payOrderService.create(payCreateRequest);

        if (payOrder.getOrderState() == PayOrderState.PAYING) {
            response.setPayOrderId(payOrder.getPayOrderId());
            response.setPayUrl(payOrder.getPayUrl());
            response.setSuccess(true);
            return response;
        }

        if (payOrder.isPaid()) {
            response.setSuccess(false);
            response.setResponseCode(PayErrorCode.ORDER_IS_ALREADY_PAID.getCode());
            response.setResponseMessage(PayErrorCode.ORDER_IS_ALREADY_PAID.getMessage());
            return response;
        }

        PayChannelResponse payChannelResponse = doPay(payCreateRequest, payOrder);

        if (payChannelResponse.getSuccess()) {
            boolean updateResult = payOrderService.paying(payOrder.getPayOrderId(), payChannelResponse.getPayUrl());
            Assert.isTrue(updateResult, () -> new BizException(RepoErrorCode.UPDATE_FAILED));
            response.setSuccess(true);
            response.setPayOrderId(payOrder.getPayOrderId());
            response.setPayUrl(payChannelResponse.getPayUrl());
        } else {
            response.setSuccess(false);
            response.setResponseCode(payChannelResponse.getResponseCode());
            response.setResponseMessage(payChannelResponse.getResponseMessage());
        }
        return response;
    }

    @Override
    @Facade
    public MultiResponse<PayOrderVO> queryPayOrders(PayQueryRequest payQueryRequest) {

        PayQueryCondition payQueryCondition = payQueryRequest.getPayQueryCondition();

        if (payQueryCondition instanceof PayQueryByBizNo payQueryByBizNo) {
            List<PayOrder> payOrders = payOrderService.queryByBizNo(payQueryByBizNo.getBizNo(), payQueryByBizNo.getBizType(), payQueryRequest.getPayerId(), payQueryRequest.getPayOrderState());
            var payQueryResponse = new MultiResponse<PayOrderVO>();
            payQueryResponse.setSuccess(true);
            payQueryResponse.setDatas(PayOrderConvertor.INSTANCE.mapToVo(payOrders));
            return payQueryResponse;
        }

        throw new UnsupportedOperationException("unsupported payQueryCondition : " + payQueryCondition);
    }

    @Override
    @Facade
    public SingleResponse<PayOrderVO> queryPayOrder(String payOrderId) {
        return SingleResponse.of(PayOrderConvertor.INSTANCE.mapToVo(payOrderService.queryByOrderId(payOrderId)));
    }

    @Override
    public SingleResponse<PayOrderVO> queryPayOrder(String payOrderId, String payerId) {
        return SingleResponse.of(PayOrderConvertor.INSTANCE.mapToVo(payOrderService.queryByOrderIdAndPayer(payOrderId, payerId)));
    }

    private PayChannelResponse doPay(PayCreateRequest payCreateRequest, PayOrder payOrder) {
        PayChannelRequest payChannelRequest = new PayChannelRequest();
        payChannelRequest.setAmount(MoneyUtils.yuanToCent(payCreateRequest.getOrderAmount()));
        payChannelRequest.setDescription(payCreateRequest.getMemo());
        payChannelRequest.setOrderId(payOrder.getPayOrderId());
        payChannelRequest.setAttach(payCreateRequest.getBizNo());
        payChannelRequest.setExpireTime(DateUtils.addMinutes(payOrder.getGmtCreate(), PayOrder.DEFAULT_TIME_OUT_MINUTES));
        PayChannelResponse payChannelResponse = payChannelServiceFactory.get(payCreateRequest.getPayChannel()).pay(payChannelRequest);
        return payChannelResponse;
    }
}
