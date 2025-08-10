package cn.hollis.nft.turbo.trade.application;

import cn.hollis.nft.turbo.api.goods.request.GoodsSaleRequest;
import cn.hollis.nft.turbo.api.goods.service.GoodsTransactionFacadeService;
import cn.hollis.nft.turbo.api.order.OrderTransactionFacadeService;
import cn.hollis.nft.turbo.api.order.request.OrderConfirmRequest;
import cn.hollis.nft.turbo.api.order.request.OrderCreateAndConfirmRequest;
import cn.hollis.nft.turbo.api.order.response.OrderResponse;
import cn.hollis.turbo.stream.producer.StreamProducer;
import cn.hutool.core.lang.Assert;
import com.alibaba.fastjson.JSON;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static cn.hollis.nft.turbo.trade.exception.TradeErrorCode.NORMAL_BUY_TCC_CANCEL_FAILED;
import static cn.hollis.nft.turbo.trade.exception.TradeErrorCode.NORMAL_BUY_TCC_CONFIRM_FAILED;
import static cn.hollis.turbo.stream.producer.StreamProducer.DELAY_LEVEL_1_M;


@Service
@Slf4j
public class TradeApplicationService {

    private static final int MAX_RETRY_TIMES = 2;

    @Resource
    private OrderTransactionFacadeService orderTransactionFacadeService;

    @Resource
    private GoodsTransactionFacadeService goodsTransactionFacadeService;

    @Autowired
    private StreamProducer streamProducer;

    //普通交易，基于TCC实现分布式一致性
    //Try -> Confirm ：Try成功，执行Confirm
    //Try --> Cancel ： Try失败，执行Cancel
    //Try -> Confirm --> Cancel ：Try成功，Confirm失败，执行Cancel
    public OrderResponse normalBuy(OrderCreateAndConfirmRequest orderCreateRequest) {

        boolean isTrySuccess = true;

        //Try
        try {
            GoodsSaleRequest goodsSaleRequest = new GoodsSaleRequest(orderCreateRequest);
            boolean result = goodsTransactionFacadeService.tryDecreaseInventory(goodsSaleRequest).getSuccess();
            Assert.isTrue(result, "decrease inventory failed");

            result = orderTransactionFacadeService.tryOrder(orderCreateRequest).getSuccess();
            Assert.isTrue(result, "order create failed");
        } catch (Exception e) {
            isTrySuccess = false;
            log.error("normalBuy try failed, ", e);
        }

        //Try失败，发【废单消息】，异步进行逆向补偿
        if (!isTrySuccess) {
            //消息监听： NormalBuyMsgListener
            streamProducer.send("normalBuyCancel-out-0", orderCreateRequest.getGoodsType().name(), JSON.toJSONString(orderCreateRequest));
            return new OrderResponse.OrderResponseBuilder().buildFail(NORMAL_BUY_TCC_CANCEL_FAILED.getCode(), NORMAL_BUY_TCC_CANCEL_FAILED.getMessage());
        }

        //Confirm
        boolean isConfirmSuccess = false;
        int retryConfirmCount = 0;

        //最大努力执行，失败最多尝试2次.（Dubbo也会有重试机制，在服务突然不可用、超时等情况下会重试2次）
        while (!isConfirmSuccess && retryConfirmCount < MAX_RETRY_TIMES) {
            try {
                GoodsSaleRequest goodsSaleRequest = new GoodsSaleRequest(orderCreateRequest);
                isConfirmSuccess = goodsTransactionFacadeService.confirmDecreaseInventory(goodsSaleRequest).getSuccess();
                Assert.isTrue(isConfirmSuccess, "confirmDecreaseInventory failed");

                OrderConfirmRequest orderConfirmRequest = new OrderConfirmRequest();
                BeanUtils.copyProperties(orderCreateRequest, orderConfirmRequest);
                isConfirmSuccess = orderTransactionFacadeService.confirmOrder(orderConfirmRequest).getSuccess();
                Assert.isTrue(isConfirmSuccess, "confirmOrder failed");
            } catch (Exception e) {
                retryConfirmCount++;
                isConfirmSuccess = false;
                log.error("normalBuy confirm failed, ", e);
            }
        }

        //Confirm失败，发【疑似废单消息】进行延迟检查
        if (!isConfirmSuccess) {
            //消息监听： NormalBuyMsgListener
            streamProducer.send("normalBuyPreCancel-out-0", orderCreateRequest.getGoodsType().name(), JSON.toJSONString(orderCreateRequest), DELAY_LEVEL_1_M);
            return new OrderResponse.OrderResponseBuilder().buildFail(NORMAL_BUY_TCC_CONFIRM_FAILED.getCode(), NORMAL_BUY_TCC_CONFIRM_FAILED.getMessage());
        }

        return new OrderResponse.OrderResponseBuilder().orderId(orderCreateRequest.getOrderId()).buildSuccess();
    }
}
