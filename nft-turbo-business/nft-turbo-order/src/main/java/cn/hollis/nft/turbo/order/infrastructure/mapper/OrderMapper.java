package cn.hollis.nft.turbo.order.infrastructure.mapper;

import cn.hollis.nft.turbo.order.domain.entity.TradeOrder;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import jakarta.validation.constraints.NotNull;
import org.apache.ibatis.annotations.Mapper;

/**
 * 订单Mapper
 *
 * @author Hollis
 */
@Mapper
public interface OrderMapper extends BaseMapper<TradeOrder> {

    /**
     * 根据订单号查询订单
     *
     * @param orderId 订单号
     * @return 订单
     */
    TradeOrder selectByOrderId(@NotNull String orderId);

    /**
     * 根据订单号和买家ID查询订单
     *
     * @param orderId 订单号
     * @param buyerId 买家ID
     * @return 订单
     */
    TradeOrder selectByOrderIdAndBuyer(@NotNull String orderId, @NotNull String buyerId);

    /**
     * 根据幂等号查询订单
     *
     * @param identifier 幂等号
     * @param buyerId    买家ID
     * @return 订单
     */
    TradeOrder selectByIdentifier(String identifier, String buyerId);

    /**
     * 更新订单
     *
     * @param entity 订单
     * @return 影响行数
     */
    int updateByOrderId(TradeOrder entity);
}
