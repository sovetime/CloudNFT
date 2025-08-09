package cn.hollis.nft.turbo.api.goods.request;

import cn.hollis.nft.turbo.api.goods.constant.GoodsEvent;
import cn.hollis.nft.turbo.base.request.BaseRequest;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


//通用的商品请求参数
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public abstract class BaseGoodsRequest extends BaseRequest {

    //幂等号
    @NotNull(message = "identifier is not null")
    private String identifier;

    //藏品id
    private Long goodsId;

    //藏品类型
    private String goodsType;

    //获取事件类型
    public abstract GoodsEvent getEventType();
}
