package cn.hollis.nft.turbo.api.order;


import cn.hollis.nft.turbo.api.order.request.OrderConfirmRequest;
import cn.hollis.nft.turbo.api.order.request.OrderCreateRequest;
import cn.hollis.nft.turbo.api.order.request.OrderDiscardRequest;
import cn.hollis.nft.turbo.api.order.response.OrderResponse;


//订单事务门面服务
public interface OrderTransactionFacadeService {

    //创建订单
    public OrderResponse tryOrder(OrderCreateRequest orderCreateRequest, String businessScene);


    //确认订单
    public OrderResponse confirmOrder(OrderConfirmRequest orderConfirmRequest, String businessScene);


    //撤销订单
    public OrderResponse cancelOrder(OrderDiscardRequest orderDiscardRequest, String businessScene);

}
