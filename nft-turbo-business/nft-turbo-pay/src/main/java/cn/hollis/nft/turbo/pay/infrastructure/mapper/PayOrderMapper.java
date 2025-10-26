package cn.hollis.nft.turbo.pay.infrastructure.mapper;

import cn.hollis.nft.turbo.pay.domain.entity.PayOrder;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import jakarta.validation.constraints.NotNull;
import org.apache.ibatis.annotations.Mapper;


//支付订单mapper
@Mapper
public interface PayOrderMapper extends BaseMapper<PayOrder> {

    //根据bizNo和payer查询
    PayOrder selectByBizNoAndPayer(String payerId, String bizNo, String bizType, String payChannel);

    //根据payOrderId查询
    PayOrder selectByPayOrderId(@NotNull String payOrderId);
}
