package cn.hollis.nft.turbo.check.facade;

import cn.hollis.nft.turbo.api.check.request.InventoryCheckRequest;
import cn.hollis.nft.turbo.api.check.response.InventoryCheckResponse;
import cn.hollis.nft.turbo.api.check.service.InventoryCheckFacadeService;
import cn.hollis.nft.turbo.api.goods.model.GoodsStreamVO;
import cn.hollis.nft.turbo.api.goods.service.GoodsFacadeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.DubboService;


//库存旁路验证
@Slf4j
@DubboService(version = "1.0.0")
public class InventoryCheckFacadeServiceImpl implements InventoryCheckFacadeService {

    @DubboReference(version = "1.0.0")
    private GoodsFacadeService goodsFacadeService;

    //库存核对
    @Override
    public InventoryCheckResponse check(InventoryCheckRequest request) {
        InventoryCheckResponse response = new InventoryCheckResponse();
        boolean checkResult = doInventoryCheck(request);

        response.setSuccess(true);
        response.setCheckResult(checkResult);
        return response;
    }

    //做库存检查，检查库存变更数量
    private boolean doInventoryCheck(InventoryCheckRequest inventoryCheckRequest) {
        //获取商品流水
        GoodsStreamVO goodsStreamVO = goodsFacadeService.getGoodsInventoryStream(
                                                inventoryCheckRequest.getGoodsId(),
                                                inventoryCheckRequest.getGoodsType(),
                                                inventoryCheckRequest.getGoodsEvent(),
                                                inventoryCheckRequest.getIdentifier());

        if (goodsStreamVO == null) {
            return false;
        }

        return goodsStreamVO.getChangedQuantity().equals(inventoryCheckRequest.getChangedQuantity());
    }
}
