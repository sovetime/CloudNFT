package cn.hollis.nft.turbo.order.domain.service;

import cn.hollis.nft.turbo.api.order.constant.TradeOrderState;
import cn.hollis.nft.turbo.order.domain.entity.TradeOrder;
import cn.hollis.nft.turbo.order.infrastructure.mapper.OrderMapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.util.Date;
import java.util.List;

/**
 * @author Hollis
 */
@Service
public class OrderReadService extends ServiceImpl<OrderMapper, TradeOrder> {

    @Autowired
    private OrderMapper orderMapper;

    /**
     * 按照订单号查询订单信息
     *
     * @param orderId
     * @return
     */
    public TradeOrder getOrder(String orderId) {
        return orderMapper.selectByOrderId(orderId);
    }

    public TradeOrder getOrder(String orderId, String buyerId) {
        return orderMapper.selectByOrderIdAndBuyer(orderId, buyerId);
    }

    public Page<TradeOrder> pageQueryByState(String buyerId, String state, int currentPage, int pageSize) {
        Page<TradeOrder> page = new Page<>(currentPage, pageSize);
        QueryWrapper<TradeOrder> wrapper = new QueryWrapper<>();
        wrapper.eq("buyer_id", buyerId);
        if (state != null) {
            wrapper.eq("order_state", state);
        } else {
            //不查询 CREATE 的订单
            wrapper.in("order_state", TradeOrderState.CONFIRM.name(), TradeOrderState.PAID.name(), TradeOrderState.FINISH.name(), TradeOrderState.CLOSED.name());
        }
        wrapper.orderBy(true, false, "gmt_create");

        return this.page(page, wrapper);
    }

    /**
     * 分页查询已经超时的订单
     *
     * @param pageSize
     * @param buyerIdTailNumber 买家 ID 的尾号
     * @param minId
     * @return
     */
    public List<TradeOrder> pageQueryTimeoutOrders(int pageSize, @Nullable String buyerIdTailNumber, Long minId) {
        QueryWrapper<TradeOrder> wrapper = new QueryWrapper<>();
        wrapper.in("order_state", TradeOrderState.CONFIRM.name(), TradeOrderState.CREATE.name());
        wrapper.lt("gmt_create", DateUtils.addMinutes(new Date(), -TradeOrder.DEFAULT_TIME_OUT_MINUTES));
        if (buyerIdTailNumber != null) {
            wrapper.likeRight("reverse_buyer_id", buyerIdTailNumber);
        }
        if (minId != null) {
            wrapper.ge("id", minId);
        }
        wrapper.orderBy(true, true, "gmt_create");
        wrapper.last("limit " + pageSize);

        return this.list(wrapper);
    }

    /**
     * 分页查询待Confirm订单
     *
     * @param pageSize
     * @param buyerIdTailNumber
     * @param minId
     * @return
     */
    public List<TradeOrder> pageQueryNeedConfirmOrders(int pageSize, @Nullable String buyerIdTailNumber, Long minId) {

        QueryWrapper<TradeOrder> wrapper = new QueryWrapper<>();
        wrapper.isNull("order_confirmed_time");
        if (buyerIdTailNumber != null) {
            wrapper.likeLeft("buyer_id", buyerIdTailNumber);
        }
        if (minId != null) {
            wrapper.ge("id", minId);
        }
        wrapper.orderBy(true, true, "gmt_create");
        wrapper.last("limit " + pageSize);

        return this.list(wrapper);
    }

}
