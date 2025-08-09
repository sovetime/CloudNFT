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

/**
 * 藏品服务
 *
 * @author Hollis
 */
public interface CollectionService extends IService<Collection> {
    /**
     * 创建
     *
     * @param request
     * @return
     */
    public Collection create(CollectionCreateRequest request);

    /**
     * 更新库存
     *
     * @param request
     * @return
     */
    public CollectionInventoryModifyResponse modifyInventory(CollectionModifyInventoryRequest request);

    /**
     * 更新价格
     *
     * @param request
     * @return
     */
    public Boolean modifyPrice(CollectionModifyPriceRequest request);

    /**
     * 下架
     *
     * @param request
     * @return
     */
    public Boolean remove(CollectionRemoveRequest request);


    /**
     * 售卖
     *
     * @param request
     * @return
     */
    public Boolean sale(GoodsTrySaleRequest request);


    /**
     * 冻结库存
     *
     * @param request
     * @return
     */
    public Boolean freezeInventory(GoodsFreezeInventoryRequest request);

    /**
     * 解冻库存
     * @param request
     * @return
     */
    public Boolean unfreezeInventory(GoodsUnfreezeInventoryRequest request);

    /**
     * 冻结库存
     *
     * @param request
     * @return
     */
    public Boolean unfreezeAndSale(GoodsUnfreezeAndSaleRequest request);

    /**
     * 售卖-无hint版
     *
     * @param request
     * @return
     */
    public Boolean saleWithoutHint(GoodsTrySaleRequest request);

    /**
     * 取消
     *
     * @param request
     * @return
     */
    public Boolean cancel(GoodsCancelSaleRequest request);

    /**
     * 确认
     *
     * @param request
     * @return
     * @deprecated 废弃，这个方法之前是依赖数据库做的藏品的序号的生成，但是这里存在并发问题。
     * 当然也可以基于乐观锁/悲观锁的方式解决，但是会影响吞吐量，所以改用其他方式实现
     * 新的实现方式见 {@link cn.hollis.nft.turbo.collection.domain.service.impl.HeldCollectionService#create(HeldCollectionCreateRequest)}
     */
    @Deprecated
    public GoodsSaleResponse confirmSale(GoodsConfirmSaleRequest request);


    /**
     * 空投
     * @param request
     * @param collection
     * @return
     */
    public CollectionAirdropResponse airDrop(CollectionAirDropRequest request,Collection collection);

    /**
     * 查询
     *
     * @param collectionId
     * @return
     */
    public Collection queryById(Long collectionId);

    /**
     * 分页查询
     *
     * @param keyWord
     * @param state
     * @param currentPage
     * @param pageSize
     * @return
     */
    public PageResponse<Collection> pageQueryByState(String keyWord, String state, int currentPage, int pageSize);
}
