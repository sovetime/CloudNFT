package cn.hollis.nft.turbo.order.facade;

import cn.hollis.nft.turbo.api.order.OrderTransactionFacadeService;
import cn.hollis.nft.turbo.api.order.constant.OrderErrorCode;
import cn.hollis.nft.turbo.api.order.request.OrderConfirmRequest;
import cn.hollis.nft.turbo.api.order.request.OrderCreateRequest;
import cn.hollis.nft.turbo.api.order.request.OrderDiscardRequest;
import cn.hollis.nft.turbo.api.order.response.OrderResponse;
import cn.hollis.nft.turbo.base.exception.BizException;
import cn.hollis.nft.turbo.lock.DistributeLock;
import cn.hollis.nft.turbo.order.domain.service.OrderManageService;
import cn.hollis.nft.turbo.rpc.facade.Facade;
import cn.hollis.nft.turbo.tcc.entity.TransCancelSuccessType;
import cn.hollis.nft.turbo.tcc.entity.TransConfirmSuccessType;
import cn.hollis.nft.turbo.tcc.entity.TransTrySuccessType;
import cn.hollis.nft.turbo.tcc.request.TccRequest;
import cn.hollis.nft.turbo.tcc.response.TransactionCancelResponse;
import cn.hollis.nft.turbo.tcc.response.TransactionConfirmResponse;
import cn.hollis.nft.turbo.tcc.response.TransactionTryResponse;
import cn.hollis.nft.turbo.tcc.service.TransactionLogService;
import cn.hutool.core.lang.Assert;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;


@DubboService(version = "1.0.0")
public class OrderTransactionFacadeServiceImpl implements OrderTransactionFacadeService {

    @Autowired
    private OrderManageService orderManageService;

    @Autowired
    private TransactionLogService transactionLogService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    @Facade
    @DistributeLock(keyExpression = "#orderCreateRequest.orderId",scene = "NORMAL_BUY_ORDER")
    public OrderResponse tryOrder(OrderCreateRequest orderCreateRequest) {
        TransactionTryResponse transactionTryResponse = transactionLogService.tryTransaction(new TccRequest(orderCreateRequest.getOrderId(), "normalBuy", "ORDER"));
        Assert.isTrue(transactionTryResponse.getSuccess(), "transaction try failed");

        if (transactionTryResponse.getTransTrySuccessType() == TransTrySuccessType.TRY_SUCCESS) {
            OrderResponse orderResponse = orderManageService.create(orderCreateRequest);
            Assert.isTrue(orderResponse.getSuccess(), () -> new BizException(OrderErrorCode.CREATE_ORDER_FAILED));
            return orderResponse;
        }

        return new OrderResponse.OrderResponseBuilder().buildSuccess();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @Facade
    @DistributeLock(keyExpression = "#orderConfirmRequest.orderId",scene = "NORMAL_BUY_ORDER")
    public OrderResponse confirmOrder(OrderConfirmRequest orderConfirmRequest) {
        TransactionConfirmResponse transactionConfirmResponse = transactionLogService.confirmTransaction(new TccRequest(orderConfirmRequest.getOrderId(), "normalBuy", "ORDER"));
        Assert.isTrue(transactionConfirmResponse.getSuccess(), "transaction confirm failed");

        if(transactionConfirmResponse.getTransConfirmSuccessType() == TransConfirmSuccessType.CONFIRM_SUCCESS){
            OrderResponse orderResponse = orderManageService.confirm(orderConfirmRequest);
            Assert.isTrue(orderResponse.getSuccess(), () -> new BizException(OrderErrorCode.CREATE_ORDER_FAILED));

            return orderResponse;
        }

        return new OrderResponse.OrderResponseBuilder().buildSuccess();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @Facade
    @DistributeLock(keyExpression = "#orderDiscardRequest.orderId",scene = "NORMAL_BUY_ORDER")
    public OrderResponse cancelOrder(OrderDiscardRequest orderDiscardRequest) {

        TransactionCancelResponse transactionCancelResponse = transactionLogService.cancelTransaction(new TccRequest(orderDiscardRequest.getOrderId(), "normalBuy", "ORDER"));
        Assert.isTrue(transactionCancelResponse.getSuccess(), "transaction cancel failed");

        //如果发生空回滚，或者回滚幂等，则不进行废弃订单操作
        if (transactionCancelResponse.getTransCancelSuccessType() == TransCancelSuccessType.CANCEL_AFTER_TRY_SUCCESS
                || transactionCancelResponse.getTransCancelSuccessType() == TransCancelSuccessType.CANCEL_AFTER_CONFIRM_SUCCESS) {
            OrderResponse orderResponse = orderManageService.discard(orderDiscardRequest);
            Assert.isTrue(orderResponse.getSuccess(), () -> new BizException(OrderErrorCode.UPDATE_ORDER_FAILED));
            return orderResponse;
        }

        return new OrderResponse.OrderResponseBuilder().buildSuccess();
    }
}
