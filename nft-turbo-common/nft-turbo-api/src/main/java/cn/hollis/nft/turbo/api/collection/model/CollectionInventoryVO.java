package cn.hollis.nft.turbo.api.collection.model;

import cn.hollis.nft.turbo.api.goods.model.BaseGoodsInventoryVO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


//藏品库存VO
@Getter
@Setter
@ToString
public class CollectionInventoryVO extends BaseGoodsInventoryVO {

    //可售库存
    private Long saleableInventory;

    //已占库存
    @Deprecated
    private Long occupiedInventory;

    //藏品数量
    private Long quantity;


    @Override
    public Long getInventory() {
        return saleableInventory;
    }

    @Override
    public Long getQuantity() {
        return quantity;
    }

}
