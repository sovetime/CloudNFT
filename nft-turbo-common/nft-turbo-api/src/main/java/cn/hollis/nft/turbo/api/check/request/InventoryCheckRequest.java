package cn.hollis.nft.turbo.api.check.request;

import cn.hollis.nft.turbo.api.goods.constant.GoodsEvent;
import cn.hollis.nft.turbo.api.goods.constant.GoodsType;
import cn.hollis.nft.turbo.base.request.BaseRequest;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
// 商品库存检查请求
public class InventoryCheckRequest extends BaseRequest {

    @NotNull(message = "商品ID不能为空")
    private String goodsId;

    @NotNull(message = "商品类型不能为空")
    private GoodsType goodsType;

    @NotNull(message = "标识符不能为空")
    private String identifier;

    @NotNull(message = "变更数量不能为空")
    private Integer changedQuantity;

    @NotNull(message = "商品事件不能为空")
    private GoodsEvent goodsEvent;
}
