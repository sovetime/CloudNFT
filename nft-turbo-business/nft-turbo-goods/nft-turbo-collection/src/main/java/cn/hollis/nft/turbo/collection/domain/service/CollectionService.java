package cn.hollis.nft.turbo.collection.domain.service;

import cn.hollis.nft.turbo.api.collection.request.*;
import cn.hollis.nft.turbo.api.collection.response.CollectionAirdropResponse;
import cn.hollis.nft.turbo.api.collection.response.CollectionInventoryModifyResponse;
import cn.hollis.nft.turbo.api.goods.request.*;
import cn.hollis.nft.turbo.api.goods.response.GoodsSaleResponse;
import cn.hollis.nft.turbo.base.response.PageResponse;
import cn.hollis.nft.turbo.collection.domain.entity.Collection;
import cn.hollis.nft.turbo.collection.domain.request.HeldCollectionCreateRequest;
import com.baomidou.mybatisplus.extension.service.IService;


//藏品服务
public interface CollectionService extends IService<Collection> {

    //创建
    public Collection create(CollectionCreateRequest request);

    //更新库存
    public CollectionInventoryModifyResponse modifyInventory(CollectionModifyInventoryRequest request);

    //更新价格
    public Boolean modifyPrice(CollectionModifyPriceRequest request);

    //下架
    public Boolean remove(CollectionRemoveRequest request);

    //售卖
    public Boolean sale(GoodsTrySaleRequest request);

    //冻结库存
    public Boolean freezeInventory(GoodsFreezeInventoryRequest request);

    //解冻库存
    public Boolean unfreezeInventory(GoodsUnfreezeInventoryRequest request);

    //冻结库存
    public Boolean unfreezeAndSale(GoodsUnfreezeAndSaleRequest request);

    //售卖-无hint版
    public Boolean saleWithoutHint(GoodsTrySaleRequest request);

    // 取消
    public Boolean cancel(GoodsCancelSaleRequest request);

    //确认，废弃，这个方法之前是依赖数据库做的藏品的序号的生成，但是这里存在并发问题。
    //当然也可以基于乐观锁/悲观锁的方式解决，但是会影响吞吐量，所以改用其他方式实现
    @Deprecated
    public GoodsSaleResponse confirmSale(GoodsConfirmSaleRequest request);

    //空投
    public CollectionAirdropResponse airDrop(CollectionAirDropRequest request,Collection collection);

    //查询
    public Collection queryById(Long collectionId);

    //分页查询
    public PageResponse<Collection> pageQueryByState(String keyWord, String state, int currentPage, int pageSize);
}
