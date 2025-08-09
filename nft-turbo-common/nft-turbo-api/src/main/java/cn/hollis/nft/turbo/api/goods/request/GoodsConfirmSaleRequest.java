package cn.hollis.nft.turbo.api.goods.request;

import cn.hollis.nft.turbo.api.goods.constant.GoodsEvent;

import java.math.BigDecimal;

/**
 * @param identifier    幂等号
 * @param goodsId       藏品 id
 * @param quantity      本次占用的库存量
 * @param bizNo         占用的业务单号，如订单号
 * @param bizType       占用的业务类型，如一级市场交易
 * @param userId        用户ID
 * @param name          藏品名称
 * @param cover         藏品封面
 * @param purchasePrice 购买价格
 */
public record GoodsConfirmSaleRequest(String identifier, Long goodsId, Integer quantity, String bizNo, String bizType,
                                      String userId,
                                      String name, String cover, BigDecimal purchasePrice) {

    public GoodsEvent eventType() {
        return GoodsEvent.CONFIRM_SALE;
    }
}
