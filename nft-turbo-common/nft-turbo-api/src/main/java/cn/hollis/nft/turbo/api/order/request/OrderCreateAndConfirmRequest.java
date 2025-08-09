package cn.hollis.nft.turbo.api.order.request;

import cn.hollis.nft.turbo.api.order.constant.TradeOrderEvent;
import cn.hollis.nft.turbo.api.user.constant.UserType;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class OrderCreateAndConfirmRequest extends OrderCreateRequest {

    @NotNull(message = "操作时间不能为空")
    private Date operateTime;

    @NotNull(message = "操作人不能为空")
    private String operator;

    @NotNull(message = "操作人类型不能为空")
    private UserType operatorType;

    @Override
    public TradeOrderEvent getOrderEvent() {
        return TradeOrderEvent.CREATE_AND_CONFIRM;
    }
}
