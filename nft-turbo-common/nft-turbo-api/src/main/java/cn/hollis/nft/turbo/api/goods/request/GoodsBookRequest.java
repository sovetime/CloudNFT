package cn.hollis.nft.turbo.api.goods.request;

import cn.hollis.nft.turbo.api.goods.constant.GoodsType;
import cn.hollis.nft.turbo.api.user.constant.UserType;
import cn.hollis.nft.turbo.base.request.BaseRequest;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GoodsBookRequest extends BaseRequest {

    @NotNull(message = "操作幂等号不能为空")
    private String identifier;

    @NotNull(message = "买家id不能为空")
    private String buyerId;

    //买家id类型
    private UserType buyerType = UserType.CUSTOMER;

    @NotNull(message = "商品Id不能为空")
    private String goodsId;

    //商品类型
    private GoodsType goodsType;

}

