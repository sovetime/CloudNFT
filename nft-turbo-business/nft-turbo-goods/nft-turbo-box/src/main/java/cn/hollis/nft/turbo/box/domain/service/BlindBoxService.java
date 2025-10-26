package cn.hollis.nft.turbo.box.domain.service;

import cn.hollis.nft.turbo.api.box.request.BlindBoxCreateRequest;
import cn.hollis.nft.turbo.api.goods.request.*;
import cn.hollis.nft.turbo.api.goods.response.GoodsSaleResponse;
import cn.hollis.nft.turbo.base.response.PageResponse;
import cn.hollis.nft.turbo.box.domain.entity.BlindBox;
import cn.hollis.nft.turbo.box.domain.request.BlindBoxAssignRequest;
import com.baomidou.mybatisplus.extension.service.IService;


//盲盒服务
public interface BlindBoxService extends IService<BlindBox> {

    //创建
    public BlindBox create(BlindBoxCreateRequest request);

    //售卖
    public Boolean sale(GoodsTrySaleRequest request);

    //售卖-无hint版
    public Boolean saleWithoutHint(GoodsTrySaleRequest request);

    //确认售卖，废弃，这个方法之前是依赖数据库做的藏品的序号的生成，但是这里存在并发问题。当然也可以基于乐观锁/悲观锁的方式解决，但是会影响吞吐量，所以改用其他方式实现
    @Deprecated
    public GoodsSaleResponse confirmSale(GoodsConfirmSaleRequest request);

    //冻结库存
    public Boolean freezeInventory(GoodsFreezeInventoryRequest request);

    //冻结库存并售卖
    public Boolean unfreezeAndSale(GoodsUnfreezeAndSaleRequest request);

    //解冻库存
    public Boolean unfreezeInventory(GoodsUnfreezeInventoryRequest request);

    //盲盒分配
    public Boolean assign(BlindBoxAssignRequest request);

    //取消售卖
    public Boolean cancel(GoodsCancelSaleRequest request);

    //查询
    public BlindBox queryById(Long blindBoxId);

    //分页查询
    public PageResponse<BlindBox> pageQueryByState(String keyWord, String state, int currentPage, int pageSize);
}
