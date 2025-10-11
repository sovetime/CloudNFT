package cn.time.nft.turbo.api.goods.request;

import cn.time.nft.turbo.api.goods.constant.GoodsEvent;


public record GoodsUnfreezeAndSaleRequest(String identifier, Long goodsId, Integer quantity) {

    public GoodsEvent eventType() {
        return GoodsEvent.UNFREEZE_AND_SALE;
    }
}
