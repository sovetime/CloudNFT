package cn.hollis.nft.turbo.api.collection.service;

import cn.hollis.nft.turbo.api.collection.request.*;
import cn.hollis.nft.turbo.api.collection.response.CollectionAirdropResponse;
import cn.hollis.nft.turbo.api.collection.response.CollectionChainResponse;
import cn.hollis.nft.turbo.api.collection.response.CollectionModifyResponse;
import cn.hollis.nft.turbo.api.collection.response.CollectionRemoveResponse;


// 藏品管理门面服务
public interface CollectionManageFacadeService {

    //创建藏品
    public CollectionChainResponse create(CollectionCreateRequest request);

    // 藏品下架
    public CollectionRemoveResponse remove(CollectionRemoveRequest request);

    //空投
    public CollectionAirdropResponse airDrop(CollectionAirDropRequest request);

    //藏品库存修改
    public CollectionModifyResponse modifyInventory(CollectionModifyInventoryRequest request);

    //藏品价格修改
    public CollectionModifyResponse modifyPrice(CollectionModifyPriceRequest request);
}
