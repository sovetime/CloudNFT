package cn.hollis.nft.turbo.inventory.domain.response;

import cn.hollis.nft.turbo.api.goods.constant.GoodsType;
import cn.hollis.nft.turbo.base.response.BaseResponse;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Hollis
 */
@Getter
@Setter
public class InventoryResponse extends BaseResponse {

    private String goodsId;

    private GoodsType goodsType;

    private String identifier;

    private Integer inventory;
}
