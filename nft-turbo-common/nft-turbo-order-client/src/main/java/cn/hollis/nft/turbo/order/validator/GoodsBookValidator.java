package cn.hollis.nft.turbo.order.validator;

import cn.hollis.nft.turbo.api.goods.model.BaseGoodsVO;
import cn.hollis.nft.turbo.api.goods.service.GoodsFacadeService;
import cn.hollis.nft.turbo.api.order.request.OrderCreateRequest;
import cn.hollis.nft.turbo.order.OrderException;

import static cn.hollis.nft.turbo.api.order.constant.OrderErrorCode.GOODS_NOT_BOOKED;

/**
 * 商品预约校验器
 *
 * @author hollis
 */
public class GoodsBookValidator extends BaseOrderCreateValidator {

    private GoodsFacadeService goodsFacadeService;

    @Override
    protected void doValidate(OrderCreateRequest request) throws OrderException {
        BaseGoodsVO baseGoodsVO = goodsFacadeService.getGoods(request.getGoodsId(), request.getGoodsType());
        if(baseGoodsVO.canBook()){
            Boolean hasBooked = goodsFacadeService.isGoodsBooked(request.getGoodsId(), request.getGoodsType(), request.getBuyerId());

            if (!hasBooked) {
                throw new OrderException(GOODS_NOT_BOOKED);
            }
        }
    }

    public GoodsBookValidator(GoodsFacadeService goodsFacadeService) {
        this.goodsFacadeService = goodsFacadeService;
    }

    public GoodsBookValidator() {
    }
}
