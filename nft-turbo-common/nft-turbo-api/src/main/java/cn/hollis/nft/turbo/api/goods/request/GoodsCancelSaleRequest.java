package cn.hollis.nft.turbo.api.goods.request;

import cn.hollis.nft.turbo.api.goods.constant.GoodsEvent;


//商品取消售卖请求
public record GoodsCancelSaleRequest(String identifier, Long collectionId, Integer quantity) {

    public GoodsEvent eventType() {
        return GoodsEvent.CANCEL_SALE;
    }
}
