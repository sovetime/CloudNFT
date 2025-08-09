package cn.hollis.nft.turbo.trade.controller;

import cn.dev33.satoken.stp.StpUtil;
import cn.hollis.nft.turbo.api.order.OrderFacadeService;
import cn.hollis.nft.turbo.api.order.constant.TradeOrderState;
import cn.hollis.nft.turbo.api.order.model.TradeOrderVO;
import cn.hollis.nft.turbo.api.order.request.OrderPageQueryRequest;
import cn.hollis.nft.turbo.api.order.request.OrderTimeoutRequest;
import cn.hollis.nft.turbo.api.pay.model.PayOrderVO;
import cn.hollis.nft.turbo.api.pay.service.PayFacadeService;
import cn.hollis.nft.turbo.base.response.PageResponse;
import cn.hollis.nft.turbo.base.response.SingleResponse;
import cn.hollis.nft.turbo.web.util.MultiResultConvertor;
import cn.hollis.nft.turbo.web.vo.MultiResult;
import cn.hollis.nft.turbo.web.vo.Result;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.UUID;

import static cn.hollis.nft.turbo.api.user.constant.UserType.PLATFORM;

/**
 * @author Hollis
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("order")
public class OrderController {

    @Autowired
    private OrderFacadeService orderFacadeService;

    @Autowired
    private PayFacadeService payFacadeService;

    /**
     * 订单列表
     *
     * @param
     * @return 结果
     */
    @GetMapping("/orderList")
    public MultiResult<TradeOrderVO> orderList(String state, int pageSize, int currentPage) {
        String userId = (String) StpUtil.getLoginId();
        OrderPageQueryRequest orderPageQueryRequest = new OrderPageQueryRequest();
        orderPageQueryRequest.setBuyerId(userId);
        orderPageQueryRequest.setState(state);
        orderPageQueryRequest.setCurrentPage(currentPage);
        orderPageQueryRequest.setPageSize(pageSize);
        PageResponse<TradeOrderVO> pageResponse = orderFacadeService.pageQuery(orderPageQueryRequest);
        return MultiResultConvertor.convert(pageResponse);
    }

    @GetMapping("/orderDetail")
    public Result<TradeOrderVO> orderDetail(@NotNull String orderId) {
        String userId = (String) StpUtil.getLoginId();
        SingleResponse<TradeOrderVO> singleResponse = orderFacadeService.getTradeOrder(orderId, userId);
        if (singleResponse.getSuccess()) {
            TradeOrderVO tradeOrderVO = singleResponse.getData();
            if(tradeOrderVO == null){
                return Result.error("ORDER_NOT_EXIST", "订单不存在");
            }
            if (tradeOrderVO.getTimeout() && tradeOrderVO.getOrderState() == TradeOrderState.CONFIRM) {
                //如果订单已经超时，并且尚未关闭，则执行一次关单后再返回数据
                OrderTimeoutRequest timeoutRequest = new OrderTimeoutRequest();
                timeoutRequest.setOperatorType(PLATFORM);
                timeoutRequest.setOperator(PLATFORM.getDesc());
                timeoutRequest.setOrderId(tradeOrderVO.getOrderId());
                timeoutRequest.setOperateTime(new Date());
                timeoutRequest.setIdentifier(UUID.randomUUID().toString());
                orderFacadeService.timeout(timeoutRequest);
                singleResponse = orderFacadeService.getTradeOrder(orderId, userId);
            }
            return Result.success(singleResponse.getData());
        } else {
            return Result.error(singleResponse.getResponseCode(), singleResponse.getResponseMessage());
        }
    }

    /**
     * 订单列表
     *
     * @param
     * @return 结果
     */
    @GetMapping("/getPayStatus")
    public Result<PayOrderVO> getPayStatus(@NotNull String payOrderId) {
        String userId = (String) StpUtil.getLoginId();
        SingleResponse<PayOrderVO> singleResponse = payFacadeService.queryPayOrder(payOrderId, userId);
        return new Result(singleResponse);
    }

}
