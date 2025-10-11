package cn.time.nft.turbo.pay.facade.service;

import cn.time.nft.turbo.api.pay.constant.PayErrorCode;
import cn.time.nft.turbo.api.pay.constant.PayOrderState;
import cn.time.nft.turbo.api.pay.model.PayOrderVO;
import cn.time.nft.turbo.api.pay.request.PayCreateRequest;
import cn.time.nft.turbo.api.pay.request.PayQueryByBizNo;
import cn.time.nft.turbo.api.pay.request.PayQueryCondition;
import cn.time.nft.turbo.api.pay.request.PayQueryRequest;
import cn.time.nft.turbo.api.pay.response.PayCreateResponse;
import cn.time.nft.turbo.api.pay.service.PayFacadeService;
import cn.time.nft.turbo.base.exception.BizException;
import cn.time.nft.turbo.base.exception.RepoErrorCode;
import cn.time.nft.turbo.base.response.MultiResponse;
import cn.time.nft.turbo.base.response.SingleResponse;
import cn.time.nft.turbo.base.utils.MoneyUtils;
import cn.time.nft.turbo.lock.DistributeLock;
import cn.time.nft.turbo.pay.domain.entity.PayOrder;
import cn.time.nft.turbo.pay.domain.entity.convertor.PayOrderConvertor;
import cn.time.nft.turbo.pay.domain.service.PayOrderService;
import cn.time.nft.turbo.pay.infrastructure.channel.common.request.PayChannelRequest;
import cn.time.nft.turbo.pay.infrastructure.channel.common.response.PayChannelResponse;
import cn.time.nft.turbo.pay.infrastructure.channel.common.service.PayChannelServiceFactory;
import cn.time.nft.turbo.rpc.facade.Facade;
import cn.hutool.core.lang.Assert;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;


@DubboService(version = "1.0.0")
public class PayFacadeServiceImpl implements PayFacadeService {

    @Autowired
    private PayOrderService payOrderService;

    @Autowired
    private PayChannelServiceFactory payChannelServiceFactory;

    // 生成支付链接
    @Facade
    @DistributeLock(keyExpression = "#payCreateRequest.bizNo", scene = "GENERATE_PAY_URL")
    @Override
    public PayCreateResponse generatePayUrl(PayCreateRequest payCreateRequest) {
        PayCreateResponse response = new PayCreateResponse();
        //创建支付订单
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

        //获取支付渠道响应（url）并进行支付操作
        PayChannelResponse payChannelResponse = doPay(payCreateRequest, payOrder);

        //修改支付状态
        if (payChannelResponse.getSuccess()) {
            //更新支付订单为支付中
            //系统需要等待支付渠道的回调通知才能确认支付是否成功
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

    //获取支付渠道响应（url）
    private PayChannelResponse doPay(PayCreateRequest payCreateRequest, PayOrder payOrder) {
        //创建支付渠道请求
        PayChannelRequest payChannelRequest = new PayChannelRequest();
        payChannelRequest.setAmount(MoneyUtils.yuanToCent(payCreateRequest.getOrderAmount()));
        payChannelRequest.setDescription(payCreateRequest.getMemo());
        payChannelRequest.setOrderId(payOrder.getPayOrderId());
        payChannelRequest.setAttach(payCreateRequest.getBizNo());
        payChannelRequest.setExpireTime(DateUtils.addMinutes(payOrder.getGmtCreate(), PayOrder.DEFAULT_TIME_OUT_MINUTES));

        //获取支付渠道响应（url）并进行支付
        PayChannelResponse payChannelResponse = payChannelServiceFactory
                                                        .get(payCreateRequest.getPayChannel())
                                                        .pay(payChannelRequest);
        return payChannelResponse;
    }
}
