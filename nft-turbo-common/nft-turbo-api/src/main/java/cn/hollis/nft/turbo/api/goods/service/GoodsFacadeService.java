package cn.hollis.nft.turbo.api.goods.service;

import cn.hollis.nft.turbo.api.goods.constant.GoodsEvent;
import cn.hollis.nft.turbo.api.goods.constant.GoodsType;
import cn.hollis.nft.turbo.api.goods.model.BaseGoodsVO;
import cn.hollis.nft.turbo.api.goods.model.GoodsStreamVO;
import cn.hollis.nft.turbo.api.goods.request.GoodsBookRequest;
import cn.hollis.nft.turbo.api.goods.request.GoodsSaleRequest;
import cn.hollis.nft.turbo.api.goods.response.GoodsBookResponse;
import cn.hollis.nft.turbo.api.goods.response.GoodsSaleResponse;

import java.util.List;

//商品服务
public interface GoodsFacadeService {

    //获取商品
    public BaseGoodsVO getGoods(String goodsId, GoodsType goodsType);

    //获取商品流水
    public GoodsStreamVO getGoodsInventoryStream(String goodsId, GoodsType goodsType, GoodsEvent goodsEvent, String identifier);

    //藏品出售的try阶段，做库存预占用
    public GoodsSaleResponse trySale(GoodsSaleRequest request);


    // 藏品出售的try阶段，做库存预占用-无hint
    public GoodsSaleResponse saleWithoutHint(GoodsSaleRequest request);


    //@deprecated 废弃，这个方法之前是依赖数据库做的藏品的序号的生成，但是这里存在并发问题。当然也可以基于乐观锁/悲观锁的方式解决，但是会影响吞吐量，所以改用其他方式实现
    //藏品出售的confirm阶段，做真正售出
    @Deprecated
    public GoodsSaleResponse confirmSale(GoodsSaleRequest request);

    //支付成功
    GoodsSaleResponse paySuccess(GoodsSaleRequest request);

    //藏品出售的cancel阶段，做库存退还
    public GoodsSaleResponse cancelSale(GoodsSaleRequest request);

    //商品预约
    public GoodsBookResponse book(GoodsBookRequest request);

    //查询是否预约过
    public Boolean isGoodsBooked(String goodsId, GoodsType goodsType, String buyerId);

    //添加热门商品
    public Boolean addHotGoods(String goodsId, String goodsType);

    //是否是热门商品
    public Boolean isHotGoods(String goodsId, String goodsType);

    //获取热门商品id列表
    public List<String> getHotGoods(String goodsType);
}
