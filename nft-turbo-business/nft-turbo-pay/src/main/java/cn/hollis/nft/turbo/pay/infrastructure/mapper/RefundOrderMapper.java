package cn.hollis.nft.turbo.pay.infrastructure.mapper;

import cn.hollis.nft.turbo.pay.domain.entity.RefundOrder;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import jakarta.validation.constraints.NotNull;
import org.apache.ibatis.annotations.Mapper;

/**
 * 退款订单mapper
 *
 * @author hollis
 */
@Mapper
public interface RefundOrderMapper extends BaseMapper<RefundOrder> {

    /**
     * 根据幂等条件查询退款单
     *
     * @param payOrderId
     * @param identifier
     * @param refundChannel
     * @return
     */
    RefundOrder selectByIdentifier(String payOrderId, String identifier, String refundChannel);

    /**
     * 根据refundOrderId查询
     *
     * @param refundOrderId
     * @return
     */
    RefundOrder selectByRefundOrderId(@NotNull String refundOrderId);
}
