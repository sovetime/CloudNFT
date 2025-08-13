package cn.hollis.nft.turbo.api.order;

import cn.hollis.nft.turbo.api.order.model.TradeOrderVO;
import cn.hollis.nft.turbo.api.order.request.*;
import cn.hollis.nft.turbo.api.order.request.OrderCreateRequest;
import cn.hollis.nft.turbo.api.order.request.OrderPayRequest;
import cn.hollis.nft.turbo.api.order.response.OrderResponse;
import cn.hollis.nft.turbo.base.response.PageResponse;
import cn.hollis.nft.turbo.base.response.SingleResponse;


public interface OrderFacadeService {

    //创建订单
    public OrderResponse create(OrderCreateRequest request);

    //取消订单
    public OrderResponse cancel(OrderCancelRequest request);

    //订单超时
    public OrderResponse timeout(OrderTimeoutRequest request);

    //订单确认
    public OrderResponse confirm(OrderConfirmRequest request);

    //确认订单
    public OrderResponse createAndConfirm(OrderCreateAndConfirmRequest request);

    //订单支付成功
    public OrderResponse paySuccess(OrderPayRequest request);

    // 订单详情
    public SingleResponse<TradeOrderVO> getTradeOrder(String orderId);

    //订单详情
    public SingleResponse<TradeOrderVO> getTradeOrder(String orderId, String userId);

    //订单分页查询
    public PageResponse<TradeOrderVO> pageQuery(OrderPageQueryRequest request);
}
