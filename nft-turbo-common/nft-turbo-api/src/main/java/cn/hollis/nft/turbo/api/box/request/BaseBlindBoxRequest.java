package cn.hollis.nft.turbo.api.box.request;

import cn.hollis.nft.turbo.api.goods.constant.GoodsEvent;
import cn.hollis.nft.turbo.base.request.BaseRequest;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


//盲盒通用请求参数
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public abstract class BaseBlindBoxRequest extends BaseRequest {

    //幂等号
    @NotNull(message = "identifier is not null")
    private String identifier;
    //盲盒id
    private Long blindBoxId;
    //获取事件类型
    public abstract GoodsEvent getEventType();
}
