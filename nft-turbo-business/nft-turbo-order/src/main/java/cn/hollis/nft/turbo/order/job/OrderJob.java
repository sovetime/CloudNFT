package cn.hollis.nft.turbo.order.job;

import cn.hollis.nft.turbo.api.common.constant.BizOrderType;
import cn.hollis.nft.turbo.api.common.constant.BusinessCode;
import cn.hollis.nft.turbo.api.order.OrderFacadeService;
import cn.hollis.nft.turbo.api.order.request.OrderConfirmRequest;
import cn.hollis.nft.turbo.api.order.request.OrderTimeoutRequest;
import cn.hollis.nft.turbo.api.pay.constant.PayOrderState;
import cn.hollis.nft.turbo.api.pay.model.PayOrderVO;
import cn.hollis.nft.turbo.api.pay.request.PayQueryByBizNo;
import cn.hollis.nft.turbo.api.pay.request.PayQueryRequest;
import cn.hollis.nft.turbo.api.pay.service.PayFacadeService;
import cn.hollis.nft.turbo.api.user.constant.UserType;
import cn.hollis.nft.turbo.base.response.MultiResponse;
import cn.hollis.nft.turbo.order.domain.entity.TradeOrder;
import cn.hollis.nft.turbo.order.domain.service.OrderReadService;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shardingsphere.infra.hint.HintManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.LinkedBlockingQueue;


@Component
@Slf4j
public class OrderJob {

    @Autowired
    private OrderFacadeService orderFacadeService;

    @Autowired
    private OrderReadService orderReadService;

    @Resource
    private PayFacadeService payFacadeService;

    private static final int CAPACITY = 2000;

    //订单确认队列
    private final BlockingQueue<TradeOrder> orderConfirmBlockingQueue = new LinkedBlockingQueue<>(CAPACITY);

    //订单超时队列
    private final BlockingQueue<TradeOrder> orderTimeoutBlockingQueue = new LinkedBlockingQueue<>(CAPACITY);

    //使用动态线程池
    private final ForkJoinPool forkJoinPool = new ForkJoinPool(10);

    private static final int PAGE_SIZE = 500;

    //结果表示对象
    private static final TradeOrder POISON = new TradeOrder();

    private static int MAX_TAIL_NUMBER = 99;

    //订单超时处理
    @XxlJob("orderTimeOutExecute")
    public ReturnT<String> orderTimeOutExecute() {
        try {
            //分片总数
            int shardIndex = XxlJobHelper.getShardIndex();
            //分片序号
            int shardTotal = XxlJobHelper.getShardTotal();

            log.info("orderTimeOutExecute start to execute , shardIndex is {} , shardTotal is {}", shardIndex, shardTotal);

            //计算当前分片负责的buyerId 尾号存放到链表中
            List<String> buyerIdTailNumberList = new ArrayList<>();
            for (int i = 0; i <= MAX_TAIL_NUMBER; i++) {
                if (i % shardTotal == shardIndex) {
                    buyerIdTailNumberList.add(StringUtils.leftPad(String.valueOf(i), 2, "0"));
                }
            }

            //遍历尾号处理超时订单
            buyerIdTailNumberList.forEach(buyerIdTailNumber -> {
                try {
                    //分页查询已经超时的订单
                    List<TradeOrder> tradeOrders = orderReadService.pageQueryTimeoutOrders(PAGE_SIZE, buyerIdTailNumber, null);
                    //其实这里用put更好一点，可以避免因为队列满了而导致异常而提前结束。
                    orderTimeoutBlockingQueue.addAll(tradeOrders);
                    //启动一个线程执行
                    forkJoinPool.execute(this::executeTimeout);

                    //循环分页处理，获取当前页最大订单id，查询下一页
                    while (CollectionUtils.isNotEmpty(tradeOrders)) {
                        //获取当前页最大订单id
                        long maxId = tradeOrders.stream().mapToLong(TradeOrder::getId).max().orElse(Long.MAX_VALUE);
                        //分页查询已经超时的订单
                        tradeOrders = orderReadService.pageQueryTimeoutOrders(PAGE_SIZE, buyerIdTailNumber, maxId + 1);
                        orderTimeoutBlockingQueue.addAll(tradeOrders);
                    }
                } finally {
                    //向阻塞队列中添加一个特殊标记对象，表示已经结束
                    orderTimeoutBlockingQueue.add(POISON);
                    log.debug("POISON added to blocking queue ，buyerIdTailNumber is {}", buyerIdTailNumber);
                }
            });

            return ReturnT.SUCCESS;
        } catch (Exception e) {
            log.error("orderTimeOutExecute failed", e);
            throw e;
        }
    }

    // 订单确认
    @XxlJob("orderConfirmExecute")
    public ReturnT<String> orderConfirmExecute() {

        int shardIndex = XxlJobHelper.getShardIndex();
        int shardTotal = XxlJobHelper.getShardTotal();

        log.info("orderConfirmExecute start to execute , shardIndex is {} , shardTotal is {}", shardIndex, shardTotal);

        //计算当前分片负责的buyerId 尾号存放到链表中
        List<String> buyerIdTailNumberList = new ArrayList<>();
        for (int i = 0; i <= MAX_TAIL_NUMBER; i++) {
            if (i % shardTotal == shardIndex) {
                buyerIdTailNumberList.add(StringUtils.leftPad(String.valueOf(i), 2, "0"));
            }
        }

        buyerIdTailNumberList.forEach(buyerIdTailNumber -> {
            try {
                List<TradeOrder> tradeOrders = orderReadService.pageQueryNeedConfirmOrders(PAGE_SIZE, buyerIdTailNumber, null);
                orderConfirmBlockingQueue.addAll(tradeOrders);
                forkJoinPool.execute(this::executeConfirm);

                while (CollectionUtils.isNotEmpty(tradeOrders)) {
                    long maxId = tradeOrders.stream().mapToLong(TradeOrder::getId).max().orElse(Long.MAX_VALUE);
                    tradeOrders = orderReadService.pageQueryNeedConfirmOrders(PAGE_SIZE, buyerIdTailNumber, maxId + 1);
                    orderConfirmBlockingQueue.addAll(tradeOrders);
                }
            } finally {
                orderConfirmBlockingQueue.add(POISON);
                log.debug("POISON added to blocking queue ，buyerIdTailNumber is {}", buyerIdTailNumber);
            }
        });

        return ReturnT.SUCCESS;
    }

    //执行
    private void executeConfirm() {
        TradeOrder tradeOrder = null;
        try {
            while (true) {
                tradeOrder = orderConfirmBlockingQueue.take();
                if (tradeOrder == POISON) {
                    log.debug("POISON toked from blocking queue");
                    break;
                }
                executeConfirmSingle(tradeOrder);
            }
        } catch (InterruptedException e) {
            log.error("executeConfirm failed", e);
        }
        log.debug("executeConfirm finish");
    }

    // 订单超时处理
    private void executeTimeout() {
        TradeOrder tradeOrder = null;
        try {
            while (true) {
                //从阻塞队列中获取订单
                tradeOrder = orderTimeoutBlockingQueue.take();
                if (tradeOrder == POISON) {
                    log.debug("POISON toked from blocking queue");
                    break;
                }
                log.info("executeTimeout tradeOrderId = {}", tradeOrder.getId());

                //执行超时关单逻辑
                executeTimeoutSingle(tradeOrder);
            }
        } catch (InterruptedException e) {
            log.error("executeTimeout failed", e);
        }
        log.debug("executeTimeout finish");
    }

    // 订单超时处理 -- hint
    @XxlJob("orderTimeOutExecuteWithHint")
    @Deprecated
    public ReturnT<String> orderTimeOutExecuteWithHint() {
        try {
            int shardIndex = XxlJobHelper.getShardIndex();
            int shardTotal = XxlJobHelper.getShardTotal();

            log.info("orderTimeOutExecute start to execute , shardIndex is {} , shardTotal is {}", shardIndex, shardTotal);

            // 分表处理
            int shardingTableCount = BusinessCode.TRADE_ORDER.tableCount();

            if (shardIndex >= shardingTableCount) {
                return ReturnT.SUCCESS;
            }

            List<Integer> shardingTableIndexes = new ArrayList<>();
            for (int realTableIndex = 0; realTableIndex < shardingTableCount; realTableIndex++) {
                if (realTableIndex % shardTotal == shardIndex) {
                    shardingTableIndexes.add(realTableIndex);
                }
            }

            shardingTableIndexes.forEach(index -> {

                try (HintManager hintManager = HintManager.getInstance()) {
                    log.info("shardIndex {} is execute", index);
                    hintManager.addTableShardingValue("trade_order", "000" + index);
                    List<TradeOrder> tradeOrders = orderReadService.pageQueryTimeoutOrders(PAGE_SIZE, null, null);

                    while (CollectionUtils.isNotEmpty(tradeOrders)) {
                        tradeOrders.forEach(this::executeTimeoutSingle);
                        long maxId = tradeOrders.stream().mapToLong(TradeOrder::getId).max().orElse(Long.MAX_VALUE);
                        tradeOrders = orderReadService.pageQueryTimeoutOrders(PAGE_SIZE, null, maxId + 1);
                    }
                }
            });

            return ReturnT.SUCCESS;
        } catch (Exception e) {
            log.error("orderTimeOutExecute failed", e);
            throw e;
        }
    }

    //订单确认 -- hint
    @XxlJob("orderConfirmExecuteWithHint")
    @Deprecated
    public ReturnT<String> orderConfirmExecuteWithHint() {

        int shardIndex = XxlJobHelper.getShardIndex();
        int shardTotal = XxlJobHelper.getShardTotal();

        int shardingTableCount = BusinessCode.TRADE_ORDER.tableCount();

        if (shardIndex >= shardingTableCount) {
            return ReturnT.SUCCESS;
        }

        List<Integer> shardingTableIndexes = new ArrayList<>();
        for (int realTableIndex = 0; realTableIndex < shardingTableCount; realTableIndex++) {
            if (realTableIndex % shardTotal == shardIndex) {
                shardingTableIndexes.add(realTableIndex);
            }
        }

        shardingTableIndexes.parallelStream().forEach(index -> {
            HintManager hintManager = HintManager.getInstance();
            hintManager.addTableShardingValue("trade_order", "000" + index);

            List<TradeOrder> tradeOrders = orderReadService.pageQueryNeedConfirmOrders(PAGE_SIZE, null, null);
            while (CollectionUtils.isNotEmpty(tradeOrders)) {
                tradeOrders.forEach(this::executeConfirmSingle);
                long maxId = tradeOrders.stream().mapToLong(TradeOrder::getId).max().orElse(Long.MAX_VALUE);
                tradeOrders = orderReadService.pageQueryNeedConfirmOrders(PAGE_SIZE, null, maxId + 1);
            }
        });

        return ReturnT.SUCCESS;
    }

    //执行超时关单逻辑
    private void executeTimeoutSingle(TradeOrder tradeOrder) {
        //查询支付单，判断是否已经支付成功。
        PayQueryRequest request = new PayQueryRequest();
        request.setPayerId(tradeOrder.getBuyerId());
        request.setPayOrderState(PayOrderState.PAID);

        PayQueryByBizNo payQueryByBizNo = new PayQueryByBizNo();
        payQueryByBizNo.setBizNo(tradeOrder.getOrderId());
        payQueryByBizNo.setBizType(BizOrderType.TRADE_ORDER.name());
        request.setPayQueryCondition(payQueryByBizNo);

        //查询支付订单
        MultiResponse<PayOrderVO> payQueryResponse = payFacadeService.queryPayOrders(request);

        //支付查询调用成功，但是查询结果为null，说明没有支付，需要进行关单
        if (payQueryResponse.getSuccess() && CollectionUtils.isEmpty(payQueryResponse.getDatas())) {
            log.info("start to execute order timeout , orderId is {}", tradeOrder.getOrderId());
            OrderTimeoutRequest orderTimeoutRequest = new OrderTimeoutRequest();
            orderTimeoutRequest.setOrderId(tradeOrder.getOrderId());
            orderTimeoutRequest.setOperateTime(new Date());
            orderTimeoutRequest.setOperator(UserType.PLATFORM.name());
            orderTimeoutRequest.setOperatorType(UserType.PLATFORM);
            orderTimeoutRequest.setIdentifier(tradeOrder.getOrderId());

            //订单超时关单
            orderFacadeService.timeout(orderTimeoutRequest);
        }
    }

    //执行实际的订单确认逻辑
    private void executeConfirmSingle(TradeOrder tradeOrder) {
        OrderConfirmRequest confirmRequest = new OrderConfirmRequest();
        confirmRequest.setOperator(UserType.PLATFORM.name());
        confirmRequest.setOperatorType(UserType.PLATFORM);
        confirmRequest.setOrderId(tradeOrder.getOrderId());
        confirmRequest.setIdentifier(tradeOrder.getIdentifier());
        confirmRequest.setOperateTime(new Date());
        confirmRequest.setOrderId(tradeOrder.getOrderId());
        confirmRequest.setBuyerId(tradeOrder.getBuyerId());
        confirmRequest.setItemCount(tradeOrder.getItemCount());
        confirmRequest.setGoodsId(tradeOrder.getGoodsId());
        confirmRequest.setGoodsType(tradeOrder.getGoodsType());

        // 确认订单
        orderFacadeService.confirm(confirmRequest);
    }
}
