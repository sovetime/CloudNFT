package cn.hollis.nft.turbo.pay.application.service;

import cn.hollis.nft.turbo.api.collection.constant.GoodsSaleBizType;
import cn.hollis.nft.turbo.api.collection.request.CollectionCreateRequest;
import cn.hollis.nft.turbo.api.collection.service.CollectionManageFacadeService;
import cn.hollis.nft.turbo.api.common.constant.BizOrderType;
import cn.hollis.nft.turbo.api.goods.constant.GoodsType;
import cn.hollis.nft.turbo.api.goods.request.GoodsSaleRequest;
import cn.hollis.nft.turbo.api.goods.response.GoodsSaleResponse;
import cn.hollis.nft.turbo.api.goods.service.GoodsFacadeService;
import cn.hollis.nft.turbo.api.order.OrderFacadeService;
import cn.hollis.nft.turbo.api.order.constant.OrderErrorCode;
import cn.hollis.nft.turbo.api.order.model.TradeOrderVO;
import cn.hollis.nft.turbo.api.order.request.OrderCreateRequest;
import cn.hollis.nft.turbo.api.order.request.OrderPayRequest;
import cn.hollis.nft.turbo.api.order.response.OrderResponse;
import cn.hollis.nft.turbo.api.pay.constant.PayChannel;
import cn.hollis.nft.turbo.api.pay.constant.PayErrorCode;
import cn.hollis.nft.turbo.api.pay.request.PayCreateRequest;
import cn.hollis.nft.turbo.api.pay.request.RefundCreateRequest;
import cn.hollis.nft.turbo.base.exception.BizException;
import cn.hollis.nft.turbo.base.response.SingleResponse;
import cn.hollis.nft.turbo.base.utils.MoneyUtils;
import cn.hollis.nft.turbo.base.utils.RemoteCallWrapper;
import cn.hollis.nft.turbo.pay.domain.entity.PayOrder;
import cn.hollis.nft.turbo.pay.domain.entity.RefundOrder;
import cn.hollis.nft.turbo.pay.domain.event.PaySuccessEvent;
import cn.hollis.nft.turbo.pay.domain.event.RefundSuccessEvent;
import cn.hollis.nft.turbo.pay.domain.service.PayOrderService;
import cn.hollis.nft.turbo.pay.domain.service.RefundOrderService;
import cn.hollis.nft.turbo.pay.infrastructure.channel.common.request.RefundChannelRequest;
import cn.hollis.nft.turbo.pay.infrastructure.channel.common.response.RefundChannelResponse;
import cn.hollis.nft.turbo.pay.infrastructure.channel.common.service.PayChannelServiceFactory;
import cn.hutool.core.lang.Assert;
import com.alibaba.fastjson.JSON;
import io.seata.spring.annotation.GlobalTransactional;
import io.seata.tm.api.transaction.TransactionHookManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

/**
 * @author Hollis
 */
@Service
@Slf4j
public class PayApplicationService {

    private static final String REFUND_MEMO_PREFIX = "退款：";

    @Autowired
    private PayOrderService payOrderService;

    @Autowired
    private OrderFacadeService orderFacadeService;

    @Autowired
    private GoodsFacadeService goodsFacadeService;

    @Autowired
    private CollectionManageFacadeService collectionManageFacadeService;

    @Autowired
    private RefundOrderService refundOrderService;

    @Autowired
    @Lazy
    private PayChannelServiceFactory payChannelServiceFactory;

    @Autowired
    protected TransactionTemplate transactionTemplate;

    /**
     * 用于测试Seata+ShardingJDBC
     * <p>
     * 注意：如果要测试这个方法，需要把orderService.create(request)方法上的 @ShardingSphereTransactionType(TransactionType.BASE) 注解加上，否则无法回滚
     */
    @GlobalTransactional(rollbackFor = Exception.class)
    public void test() {
        CollectionCreateRequest request = new CollectionCreateRequest();
        request.setIdentifier(String.valueOf(System.currentTimeMillis()));
        request.setName("测试藏品");
        request.setQuantity(100L);
        request.setSaleTime(new Date());
        request.setPrice(BigDecimal.TEN);
        request.setCover("https://t7.baidu.com/it/u=1595072465,3644073269&fm=193&f=GIF");
        collectionManageFacadeService.create(request);

        OrderCreateRequest orderCreateRequest = new OrderCreateRequest();
        orderCreateRequest.setBuyerId("2511111");
        orderCreateRequest.setOrderId("2511111");
        orderCreateRequest.setSellerId("123321111");
        orderCreateRequest.setGoodsId("10018");
        orderCreateRequest.setGoodsName(UUID.randomUUID().toString());
        orderCreateRequest.setGoodsType(GoodsType.COLLECTION);
        orderCreateRequest.setOrderAmount(new BigDecimal("10.000000"));
        orderCreateRequest.setIdentifier(UUID.randomUUID().toString());
        orderCreateRequest.setItemPrice(new BigDecimal("10.000000"));
        orderCreateRequest.setItemCount(1);

        //注意：如果要测试这个方法，需要把orderService.create(request)方法上的 @ShardingSphereTransactionType(TransactionType.BASE) 注解加上，否则无法回滚
        OrderResponse response = orderFacadeService.create(orderCreateRequest);
        Assert.isTrue(response.getSuccess(), () -> new BizException(OrderErrorCode.UPDATE_ORDER_FAILED));

        PayCreateRequest payCreateRequest = new PayCreateRequest();
        payCreateRequest.setOrderAmount(orderCreateRequest.getOrderAmount());
        payCreateRequest.setBizNo(response.getOrderId());
        payCreateRequest.setBizType(BizOrderType.TRADE_ORDER);
        payCreateRequest.setMemo(orderCreateRequest.getGoodsName());
        payCreateRequest.setPayChannel(PayChannel.MOCK);
        payCreateRequest.setPayerId(orderCreateRequest.getBuyerId());
        payCreateRequest.setPayerType(orderCreateRequest.getBuyerType());
        payCreateRequest.setPayeeId(orderCreateRequest.getSellerId());
        payCreateRequest.setPayeeType(orderCreateRequest.getSellerType());
        PayOrder payOrder = payOrderService.create(payCreateRequest);
        Assert.notNull(payOrder, () -> new BizException(PayErrorCode.PAY_ORDER_CREATE_FAILED));
        throw new RuntimeException();
    }

    /**
     * 支付成功
     * <pre>
     *     正常支付成功：
     *     1、查询订单状态
     *     2、推进订单状态到支付成功
     *     3、藏品库存真正扣减
     *     4、创建持有的藏品
     *     5、推进支付状态到支付成功
     *     6、持有的藏品上链
     *
     *     支付幂等成功：
     *      1、查询订单状态
     *      2、推进支付状态到支付成功
     *
     *      重复支付：
     *      1、查询订单状态
     *      2、创建退款单
     *      3、重试退款直到成功
     * </pre>
     */
    @GlobalTransactional(rollbackFor = Exception.class)
    public boolean paySuccess(PaySuccessEvent paySuccessEvent) {

        PayOrder payOrder = payOrderService.queryByOrderId(paySuccessEvent.getPayOrderId());
        if (payOrder.isPaid()) {
            return true;
        }

        SingleResponse<TradeOrderVO> response = orderFacadeService.getTradeOrder(payOrder.getBizNo());
        TradeOrderVO tradeOrderVO = response.getData();

        OrderPayRequest orderPayRequest = getOrderPayRequest(paySuccessEvent, payOrder);
        OrderResponse orderResponse = RemoteCallWrapper.call(req -> orderFacadeService.paySuccess(req), orderPayRequest, "orderFacadeService.pay", false);

        //如果订单已经被其他支付推进到支付成功，或者已经关单，则启动退款流程
        if (needChargeBack(orderResponse)) {
            log.info("order already paid ,do chargeback ," + payOrder.getBizNo());

            Boolean result = payOrderService.paySuccess(paySuccessEvent);
            Assert.isTrue(result, () -> new BizException(PayErrorCode.PAY_SUCCESS_NOTICE_FAILED));
            doChargeBack(paySuccessEvent, tradeOrderVO);

            return true;
        }

        if (!orderResponse.getSuccess()) {
            log.error("orderFacadeService.pay error, response = {}", JSON.toJSONString(orderResponse));
            return false;
        }

        ///confirmSale 被废弃，详Service.confirmSale
        ///GoodsSaleRequest goodsSaleRequest = getGoodsSaleRequest(tradeOrderVO);
        ///GoodsSaleResponse goodsSaleResponse = RemoteCallWrapper.call(req -> goodsFacadeService.confirmSale(req), goodsSaleRequest, "goodsFacadeService.confirmSale");

        GoodsSaleRequest goodsSaleRequest = getGoodsSaleRequest(tradeOrderVO);
        GoodsSaleResponse goodsSaleResponse = RemoteCallWrapper.call(req -> goodsFacadeService.paySuccess(req), goodsSaleRequest, "goodsFacadeService.confirmSale");

        switch (tradeOrderVO.getGoodsType()) {
            case COLLECTION:
                //只有藏品需要在支付成功后立即上链
                TransactionHookManager.registerHook(new PaySuccessTransactionHook(goodsSaleResponse.getHeldCollectionId()));
                break;
            default:
                //do nothing
        }

        Boolean result = payOrderService.paySuccess(paySuccessEvent);
        Assert.isTrue(result, () -> new BizException(PayErrorCode.PAY_SUCCESS_NOTICE_FAILED));

        return true;
    }

    /**
     * 支付失败（明确的支付失败，而不是处理中、系统异常等）处理：
     * 1、订单状态不需要做任何操作
     * 2、支付单状态关闭
     * 3、藏品、链不需要任何操作
     *
     * @param payOrderId
     * @return
     */
    @GlobalTransactional(rollbackFor = Exception.class)
    public boolean payFailed(String payOrderId) {
        PayOrder payOrder = payOrderService.queryByOrderId(payOrderId);

        if (payOrder.isPayFailed()) {
            return true;
        }

        Boolean result = payOrderService.payFailed(payOrderId);
        Assert.isTrue(result, () -> new BizException(PayErrorCode.PAY_SUCCESS_NOTICE_FAILED));

        return true;
    }

    private static boolean needChargeBack(OrderResponse orderResponse) {
        return orderResponse.getResponseCode() != null
                && (orderResponse.getResponseCode().equals(OrderErrorCode.ORDER_ALREADY_PAID.getCode())
                || orderResponse.getResponseCode().equals(OrderErrorCode.ORDER_ALREADY_CLOSED.getCode()));
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean refundSuccess(RefundSuccessEvent refundSuccessEvent) {
        RefundOrder refundOrder = refundOrderService.queryByOrderId(refundSuccessEvent.getRefundOrderId());
        if (refundOrder.isRefunded()) {
            return true;
        }

        boolean refundResult = payOrderService.refundSuccess(refundSuccessEvent)
                && refundOrderService.refundSuccess(refundSuccessEvent);

        Assert.isTrue(refundResult, () -> new BizException(PayErrorCode.REFUND_SUCCESS_NOTICE_FAILED));

        return true;
    }

    private static GoodsSaleRequest getGoodsSaleRequest(TradeOrderVO tradeOrderVO) {
        GoodsSaleRequest goodsSaleRequest = new GoodsSaleRequest();
        goodsSaleRequest.setGoodsId(Long.valueOf(tradeOrderVO.getGoodsId()));
        goodsSaleRequest.setGoodsType(tradeOrderVO.getGoodsType().name());
        goodsSaleRequest.setIdentifier(tradeOrderVO.getOrderId());
        goodsSaleRequest.setUserId(tradeOrderVO.getBuyerId());
        goodsSaleRequest.setQuantity(tradeOrderVO.getItemCount());
        goodsSaleRequest.setBizNo(tradeOrderVO.getOrderId());
        goodsSaleRequest.setBizType(GoodsSaleBizType.PRIMARY_TRADE.name());
        goodsSaleRequest.setName(tradeOrderVO.getGoodsName());
        goodsSaleRequest.setCover(tradeOrderVO.getGoodsPicUrl());
        goodsSaleRequest.setPurchasePrice(tradeOrderVO.getItemPrice());

        return goodsSaleRequest;
    }

    private static OrderPayRequest getOrderPayRequest(PaySuccessEvent paySuccessEvent, PayOrder payOrder) {
        OrderPayRequest orderPayRequest = new OrderPayRequest();
        orderPayRequest.setOperateTime(paySuccessEvent.getPaySucceedTime());
        orderPayRequest.setPayChannel(paySuccessEvent.getPayChannel());
        orderPayRequest.setPayStreamId(payOrder.getPayOrderId());
        orderPayRequest.setAmount(paySuccessEvent.getPaidAmount());
        orderPayRequest.setOrderId(payOrder.getBizNo());
        orderPayRequest.setOperatorType(payOrder.getPayerType());
        orderPayRequest.setOperator(payOrder.getPayerId());
        orderPayRequest.setIdentifier(payOrder.getPayOrderId());
        return orderPayRequest;
    }

    private void doChargeBack(PaySuccessEvent paySuccessEvent, TradeOrderVO tradeOrderVO) {
        RefundCreateRequest refundCreateRequest = new RefundCreateRequest();
        refundCreateRequest.setIdentifier(paySuccessEvent.getChannelStreamId());
        refundCreateRequest.setMemo(REFUND_MEMO_PREFIX + tradeOrderVO.getOrderId());
        refundCreateRequest.setPayOrderId(paySuccessEvent.getPayOrderId());
        refundCreateRequest.setRefundAmount(paySuccessEvent.getPaidAmount());
        refundCreateRequest.setRefundChannel(paySuccessEvent.getPayChannel());
        RefundOrder refundOrder = refundOrderService.create(refundCreateRequest);
        Assert.notNull(refundOrder, () -> new BizException(PayErrorCode.REFUND_CREATE_FAILED));

        //异步进行退款执行，失败了交给定时任务重试
        Thread.ofVirtual().start(() -> {
            RefundChannelRequest refundChannelRequest = new RefundChannelRequest();
            refundChannelRequest.setRefundOrderId(refundOrder.getRefundOrderId());
            refundChannelRequest.setPaidAmount(MoneyUtils.yuanToCent(refundOrder.getPaidAmount()));
            refundChannelRequest.setPayChannelStreamId(refundOrder.getPayChannelStreamId());
            refundChannelRequest.setPayOrderId(refundOrder.getPayOrderId());
            refundChannelRequest.setRefundAmount(MoneyUtils.yuanToCent(refundOrder.getApplyRefundAmount()));
            refundChannelRequest.setRefundReason(refundOrder.getMemo());

            RefundChannelResponse refundChannelResponse = payChannelServiceFactory.get(paySuccessEvent.getPayChannel()).refund(refundChannelRequest);

            if (refundChannelResponse.getSuccess()) {
                refundOrderService.refunding(refundOrder.getRefundOrderId());
            }
        });
    }
}
