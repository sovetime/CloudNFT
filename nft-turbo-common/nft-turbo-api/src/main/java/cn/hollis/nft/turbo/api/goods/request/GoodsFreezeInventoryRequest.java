package cn.hollis.nft.turbo.api.goods.request;

import cn.hollis.nft.turbo.api.goods.constant.GoodsEvent;


//冻结库存
public record GoodsFreezeInventoryRequest(String identifier, Long goodsId, Integer quantity) {

    public GoodsEvent eventType() {
        return GoodsEvent.FREEZE_INVENTORY;
    }
}
