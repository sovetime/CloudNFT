package cn.hollis.nft.turbo.api.order.request;

import cn.hollis.nft.turbo.api.user.constant.UserType;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;


@Setter
@Getter
public abstract class BaseOrderUpdateRequest extends BaseOrderRequest {

    @NotNull(message = "订单id不能为空")
    private String orderId;

    @NotNull(message = "操作时间不能为空")
    private Date operateTime;

    @NotNull(message = "操作人不能为空")
    private String operator;

    @NotNull(message = "操作人类型不能为空")
    private UserType operatorType;
}
