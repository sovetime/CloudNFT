package cn.time.nft.turbo.api.goods.request;

import cn.time.nft.turbo.api.goods.constant.GoodsEvent;


public record GoodsTrySaleRequest(String identifier, Long goodsId, Integer quantity) {

    public GoodsEvent eventType() {
        return GoodsEvent.TRY_SALE;
    }
}
