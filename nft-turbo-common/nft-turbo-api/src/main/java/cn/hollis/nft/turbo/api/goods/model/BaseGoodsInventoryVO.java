package cn.hollis.nft.turbo.api.goods.model;

import java.io.Serializable;


// 通用的商品库存VO
public abstract class BaseGoodsInventoryVO implements Serializable {

    //可售库存
    public abstract Long getInventory();

    //库存总量
    public abstract Long getQuantity();
}
