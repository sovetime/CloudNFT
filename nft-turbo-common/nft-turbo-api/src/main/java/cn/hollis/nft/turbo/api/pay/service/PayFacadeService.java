package cn.hollis.nft.turbo.api.pay.service;

import cn.hollis.nft.turbo.api.pay.model.PayOrderVO;
import cn.hollis.nft.turbo.api.pay.request.PayCreateRequest;
import cn.hollis.nft.turbo.api.pay.request.PayQueryRequest;
import cn.hollis.nft.turbo.api.pay.response.PayCreateResponse;
import cn.hollis.nft.turbo.api.pay.response.PayQueryResponse;
import cn.hollis.nft.turbo.base.response.MultiResponse;
import cn.hollis.nft.turbo.base.response.SingleResponse;


public interface PayFacadeService {

    //生成支付链接
    public PayCreateResponse generatePayUrl(PayCreateRequest payCreateRequest);

    //查询支付订单
    public MultiResponse<PayOrderVO> queryPayOrders(PayQueryRequest payQueryRequest);

    //查询支付订单
    public SingleResponse<PayOrderVO> queryPayOrder(String payOrderId);

    //查询支付订单
    public SingleResponse<PayOrderVO> queryPayOrder(String payOrderId, String payerId);

}
