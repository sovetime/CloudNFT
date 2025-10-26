package cn.hollis.nft.turbo.api.goods.service;

import cn.hollis.nft.turbo.api.goods.request.GoodsSaleRequest;
import cn.hollis.nft.turbo.api.goods.response.GoodsSaleResponse;

public interface GoodsTransactionFacadeService {

    //锁定库存
    public GoodsSaleResponse tryDecreaseInventory(GoodsSaleRequest request);

    //解锁并扣减库存
    public GoodsSaleResponse confirmDecreaseInventory(GoodsSaleRequest request);

    //解锁库存
    public GoodsSaleResponse cancelDecreaseInventory(GoodsSaleRequest request);
}
