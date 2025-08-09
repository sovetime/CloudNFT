package cn.hollis.nft.turbo.order.validator;

import cn.hollis.nft.turbo.api.goods.constant.GoodsState;
import cn.hollis.nft.turbo.api.goods.model.BaseGoodsVO;
import cn.hollis.nft.turbo.api.goods.service.GoodsFacadeService;
import cn.hollis.nft.turbo.api.order.request.OrderCreateRequest;
import cn.hollis.nft.turbo.order.OrderException;

import static cn.hollis.nft.turbo.api.order.constant.OrderErrorCode.GOODS_NOT_AVAILABLE;
import static cn.hollis.nft.turbo.api.order.constant.OrderErrorCode.GOODS_PRICE_CHANGED;

/**
 * 商品校验器
 *
 * @author hollis
 */
public class GoodsValidator extends BaseOrderCreateValidator {

    private GoodsFacadeService goodsFacadeService;

    @Override
    protected void doValidate(OrderCreateRequest request) throws OrderException {
        BaseGoodsVO baseGoodsVO = goodsFacadeService.getGoods(request.getGoodsId(), request.getGoodsType());

        // 如果商品不是可售状态，则返回失败
        // PS：可售状态为什么要包含SOLD_OUT呢？因为商品查询的接口中去查询了 Redis 的最新库存，而 Redis 的库存在下单时可能已经扣减过刚好为0了，所以这里要包含 SOLD_OUT
        if (baseGoodsVO.getState() != GoodsState.SELLING && baseGoodsVO.getState() != GoodsState.SOLD_OUT) {
            throw new OrderException(GOODS_NOT_AVAILABLE);
        }

        if (baseGoodsVO.getPrice().compareTo(request.getItemPrice()) != 0) {
            throw new OrderException(GOODS_PRICE_CHANGED);
        }
    }

    public GoodsValidator(GoodsFacadeService goodsFacadeService) {
        this.goodsFacadeService = goodsFacadeService;
    }

    public GoodsValidator() {
    }
}
