package cn.time.nft.turbo.inventory.domain.response;

import cn.time.nft.turbo.api.goods.constant.GoodsType;
import cn.time.nft.turbo.base.response.BaseResponse;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class InventoryResponse extends BaseResponse {

    private String goodsId;

    private GoodsType goodsType;

    private String identifier;

    private Integer inventory;
}
