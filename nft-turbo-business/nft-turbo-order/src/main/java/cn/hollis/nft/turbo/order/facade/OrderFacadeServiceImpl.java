package cn.hollis.nft.turbo.order.facade;

import cn.hollis.nft.turbo.api.goods.request.GoodsSaleRequest;
import cn.hollis.nft.turbo.api.goods.response.GoodsSaleResponse;
import cn.hollis.nft.turbo.api.goods.service.GoodsFacadeService;
import cn.hollis.nft.turbo.api.inventory.request.InventoryRequest;
import cn.hollis.nft.turbo.api.inventory.service.InventoryFacadeService;
import cn.hollis.nft.turbo.api.order.OrderFacadeService;
import cn.hollis.nft.turbo.api.order.constant.OrderErrorCode;
import cn.hollis.nft.turbo.api.order.model.TradeOrderVO;
import cn.hollis.nft.turbo.api.order.request.*;
import cn.hollis.nft.turbo.api.order.response.OrderResponse;
import cn.hollis.nft.turbo.api.user.constant.UserType;
import cn.hollis.nft.turbo.api.user.request.UserQueryRequest;
import cn.hollis.nft.turbo.api.user.response.UserQueryResponse;
import cn.hollis.nft.turbo.api.user.response.data.UserInfo;
import cn.hollis.nft.turbo.api.user.service.UserFacadeService;
import cn.hollis.nft.turbo.base.response.BaseResponse;
import cn.hollis.nft.turbo.base.response.PageResponse;
import cn.hollis.nft.turbo.base.response.SingleResponse;
import cn.hollis.nft.turbo.lock.DistributeLock;
import cn.hollis.nft.turbo.order.OrderException;
import cn.hollis.nft.turbo.order.domain.entity.TradeOrder;
import cn.hollis.nft.turbo.order.domain.entity.convertor.TradeOrderConvertor;
import cn.hollis.nft.turbo.order.domain.service.OrderManageService;
import cn.hollis.nft.turbo.order.domain.service.OrderReadService;
import cn.hollis.nft.turbo.order.validator.OrderCreateValidator;
import cn.hollis.nft.turbo.rpc.facade.Facade;
import cn.hollis.turbo.stream.producer.StreamProducer;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import jakarta.annotation.Resource;
import org.apache.dubbo.config.annotation.DubboService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static cn.hollis.nft.turbo.api.order.constant.OrderErrorCode.ORDER_CREATE_VALID_FAILED;


@DubboService(version = "1.0.0")
public class OrderFacadeServiceImpl implements OrderFacadeService {

    @Autowired
    private OrderManageService orderService;

    @Autowired
    private OrderReadService orderReadService;

    @Resource
    private InventoryFacadeService inventoryFacadeService;

    @Autowired
    private StreamProducer streamProducer;

    @Resource
    private UserFacadeService userFacadeService;

    @Resource
    private GoodsFacadeService goodsFacadeService;

    @Autowired
    private OrderCreateValidator orderValidatorChain;

    @Autowired
    private OrderCreateValidator orderConfirmValidatorChain;

    @Override
    @DistributeLock(keyExpression = "#request.identifier", scene = "ORDER_CREATE")
    @Facade
    public OrderResponse create(OrderCreateRequest request) {
        try {
            orderValidatorChain.validate(request);
        } catch (OrderException e) {
            return new OrderResponse.OrderResponseBuilder().buildFail(ORDER_CREATE_VALID_FAILED.getCode(), e.getErrorCode().getMessage());
        }

        InventoryRequest inventoryRequest = new InventoryRequest(request);
        SingleResponse<Boolean> decreaseResult = inventoryFacadeService.decrease(inventoryRequest);

        if (decreaseResult.getSuccess()) {
            return orderService.createAndAsyncConfirm(request);
        }
        throw new OrderException(OrderErrorCode.INVENTORY_DECREASE_FAILED);
    }

    @Override
    @Facade
    public OrderResponse cancel(OrderCancelRequest request) {
        return sendTransactionMsgForClose(request);
    }

    @Override
    @Facade
    public OrderResponse timeout(OrderTimeoutRequest request) {
        return sendTransactionMsgForClose(request);
    }

    @Override
    @Facade
    public OrderResponse confirm(OrderConfirmRequest request) {
        GoodsSaleRequest goodsSaleRequest = new GoodsSaleRequest();
        goodsSaleRequest.setUserId(request.getBuyerId());
        goodsSaleRequest.setGoodsId(Long.valueOf(request.getGoodsId()));
        goodsSaleRequest.setGoodsType(request.getGoodsType().name());
        goodsSaleRequest.setIdentifier(request.getOrderId());
        goodsSaleRequest.setQuantity(request.getItemCount());
        BaseResponse response = goodsFacadeService.trySale(goodsSaleRequest);

        if (response.getSuccess()) {
            return orderService.confirm(request);
        }

        return new OrderResponse.OrderResponseBuilder().orderId(request.getOrderId()).buildFail(response.getResponseCode(), response.getResponseMessage());
    }

    //确认订单
    @Override
    @DistributeLock(keyExpression = "#request.identifier", scene = "ORDER_CREATE")
    @Facade
    public OrderResponse createAndConfirm(OrderCreateAndConfirmRequest request) {
        try {
            //订单校验
            orderConfirmValidatorChain.validate(request);
        } catch (OrderException e) {
            return new OrderResponse.OrderResponseBuilder().orderId(request.getOrderId()).buildFail(ORDER_CREATE_VALID_FAILED.getCode(), e.getErrorCode().getMessage());
        }
        GoodsSaleRequest goodsSaleRequest = new GoodsSaleRequest(request);

        // 藏品出售的try阶段，做库存预占用-无hint
        GoodsSaleResponse response = goodsFacadeService.saleWithoutHint(goodsSaleRequest);

        if (!response.getSuccess()) {
            return new OrderResponse.OrderResponseBuilder().buildFail(response.getResponseMessage(), response.getResponseCode());
        }
        return orderService.createAndConfirm(request);
    }

    //发送事务消息关单
    @NotNull
    private OrderResponse sendTransactionMsgForClose(BaseOrderUpdateRequest request) {
        //因为RocketMQ 的事务消息中，如果本地事务发生了异常，这里返回也会是个 true，所以就需要做一下反查进行二次判断，才能知道关单操作是否成功
        //消息监听：TradeOrderListener
        streamProducer.send("orderClose-out-0", null, JSON.toJSONString(request), "CLOSE_TYPE", request.getOrderEvent().name());
        TradeOrder tradeOrder = orderReadService.getOrder(request.getOrderId());
        OrderResponse orderResponse = new OrderResponse();
        if (tradeOrder.isClosed()) {
            orderResponse.setSuccess(true);
        } else {
            orderResponse.setSuccess(false);
        }
        return orderResponse;
    }

    @Override
    @Facade
    public OrderResponse paySuccess(OrderPayRequest request) {
        OrderResponse response = orderService.paySuccess(request);
        if (!response.getSuccess()) {
            TradeOrder existOrder = orderReadService.getOrder(request.getOrderId());
            if (existOrder != null && existOrder.isClosed()) {
                return new OrderResponse.OrderResponseBuilder().orderId(existOrder.getOrderId()).buildFail(OrderErrorCode.ORDER_ALREADY_CLOSED.getCode(), OrderErrorCode.ORDER_ALREADY_CLOSED.getMessage());
            }
            if (existOrder != null && existOrder.isPaid()) {
                if (existOrder.getPayStreamId().equals(request.getPayStreamId()) && existOrder.getPayChannel() == request.getPayChannel()) {
                    return new OrderResponse.OrderResponseBuilder().orderId(existOrder.getOrderId()).buildSuccess();
                } else {
                    return new OrderResponse.OrderResponseBuilder().orderId(existOrder.getOrderId()).buildFail(OrderErrorCode.ORDER_ALREADY_PAID.getCode(), OrderErrorCode.ORDER_ALREADY_PAID.getMessage());
                }
            }
        }
        return response;
    }

    @Override
    public SingleResponse<TradeOrderVO> getTradeOrder(String orderId) {
        return SingleResponse.of(TradeOrderConvertor.INSTANCE.mapToVo(orderReadService.getOrder(orderId)));
    }

    @Override
    @Facade
    public SingleResponse<TradeOrderVO> getTradeOrder(String orderId, String userId) {
        return SingleResponse.of(TradeOrderConvertor.INSTANCE.mapToVo(orderReadService.getOrder(orderId, userId)));
    }

    @Override
    @Facade
    public PageResponse<TradeOrderVO> pageQuery(OrderPageQueryRequest request) {
        //分页查询不同状态的订单
        Page<TradeOrder> tradeOrderPage = orderReadService.pageQueryByState(request.getBuyerId(), request.getState(), request.getCurrentPage(), request.getPageSize());
        //转换为VO
        List<TradeOrderVO> tradeOrderVos = TradeOrderConvertor.INSTANCE.mapToVo(tradeOrderPage.getRecords());
        //设置卖家名称
        tradeOrderVos.forEach(tradeOrderVO -> tradeOrderVO.setSellerName(getSellerName(tradeOrderVO)));

        return PageResponse.of(tradeOrderVos, (int) tradeOrderPage.getTotal(), request.getPageSize(), request.getCurrentPage());
    }

    private String getSellerName(TradeOrderVO tradeOrderVO) {
        if (tradeOrderVO.getSellerType() == UserType.PLATFORM) {
            return "平台";
        }
        UserQueryRequest userQueryRequest = new UserQueryRequest(Long.valueOf(tradeOrderVO.getSellerId()));

        UserQueryResponse<UserInfo> userQueryResponse = userFacadeService.query(userQueryRequest);
        if (userQueryResponse.getSuccess()) {
            return userQueryResponse.getData().getNickName();
        }

        return "-";
    }
}
