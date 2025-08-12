package cn.hollis.nft.turbo.order.validator;

import cn.hollis.nft.turbo.api.goods.constant.GoodsState;
import cn.hollis.nft.turbo.api.goods.model.BaseGoodsVO;
import cn.hollis.nft.turbo.api.goods.service.GoodsFacadeService;
import cn.hollis.nft.turbo.api.order.request.OrderCreateRequest;
import cn.hollis.nft.turbo.order.OrderException;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import static cn.hollis.nft.turbo.api.order.constant.OrderErrorCode.GOODS_NOT_AVAILABLE;
import static cn.hollis.nft.turbo.api.order.constant.OrderErrorCode.GOODS_PRICE_CHANGED;


//订单校验器
@AllArgsConstructor
@NoArgsConstructor
public class GoodsValidator extends BaseOrderCreateValidator {

    private GoodsFacadeService goodsFacadeService;

    @Override
    protected void doValidate(OrderCreateRequest request) throws OrderException {
        //获取商品信息
        BaseGoodsVO baseGoodsVO = goodsFacadeService.getGoods(request.getGoodsId(), request.getGoodsType());

        //商品需要是可售状态以及还有库存
        if (baseGoodsVO.getState() != GoodsState.SELLING && baseGoodsVO.getState() != GoodsState.SOLD_OUT) {
            throw new OrderException(GOODS_NOT_AVAILABLE);
        }

        //商品价格校验
        if (baseGoodsVO.getPrice().compareTo(request.getItemPrice()) != 0) {
            throw new OrderException(GOODS_PRICE_CHANGED);
        }
    }

}
