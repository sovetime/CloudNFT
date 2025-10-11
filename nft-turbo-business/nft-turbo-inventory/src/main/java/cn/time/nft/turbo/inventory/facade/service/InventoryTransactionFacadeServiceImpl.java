package cn.time.nft.turbo.inventory.facade.service;

import cn.time.nft.turbo.api.inventory.InventoryTransactionFacadeService;
import cn.time.nft.turbo.api.inventory.request.InventoryRequest;
import cn.time.nft.turbo.api.inventory.service.InventoryFacadeService;
import cn.time.nft.turbo.base.response.SingleResponse;
import cn.time.nft.turbo.rpc.facade.Facade;
import cn.time.nft.turbo.tcc.entity.TransCancelSuccessType;
import cn.time.nft.turbo.tcc.entity.TransTrySuccessType;
import cn.time.nft.turbo.tcc.request.TccRequest;
import cn.time.nft.turbo.tcc.response.TransactionCancelResponse;
import cn.time.nft.turbo.tcc.response.TransactionConfirmResponse;
import cn.time.nft.turbo.tcc.response.TransactionTryResponse;
import cn.time.nft.turbo.tcc.service.TransactionLogService;
import cn.hutool.core.lang.Assert;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@DubboService(version = "1.0.0")
public class InventoryTransactionFacadeServiceImpl implements InventoryTransactionFacadeService {

    @Autowired
    private TransactionLogService transactionLogService;

    @Autowired
    InventoryFacadeService inventoryFacadeService;

    @Override
    public Boolean tryDecrease(InventoryRequest inventoryRequest) {
        TransactionTryResponse transactionTryResponse = transactionLogService.tryTransaction(new TccRequest(inventoryRequest.getIdentifier(), "newBuyPlus", inventoryRequest.getGoodsType().name()));
        Assert.isTrue(transactionTryResponse.getSuccess(), "transaction try failed");

        if (transactionTryResponse.getTransTrySuccessType() == TransTrySuccessType.TRY_SUCCESS) {
            return inventoryFacadeService.decrease(inventoryRequest).getData();
        }
        return false;
    }

    @Override
    public Boolean confirmDecrease(InventoryRequest inventoryRequest) {
        TransactionConfirmResponse transactionConfirmResponse = transactionLogService.confirmTransaction(new TccRequest(inventoryRequest.getIdentifier(), "newBuyPlus", inventoryRequest.getGoodsType().name()));
        return transactionConfirmResponse.getSuccess();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @Facade
    public Boolean cancelDecrease(InventoryRequest inventoryRequest) {
        TransactionCancelResponse transactionCancelResponse = transactionLogService.cancelTransaction(new TccRequest(inventoryRequest.getIdentifier(), "newBuyPlus", inventoryRequest.getGoodsType().name()));
        Assert.isTrue(transactionCancelResponse.getSuccess(), "transaction cancel failed");

        if (transactionCancelResponse.getTransCancelSuccessType() == TransCancelSuccessType.CANCEL_AFTER_TRY_SUCCESS
        || transactionCancelResponse.getTransCancelSuccessType() == TransCancelSuccessType.CANCEL_AFTER_CONFIRM_SUCCESS) {

            SingleResponse<String> response = inventoryFacadeService.getInventoryDecreaseLog(inventoryRequest);
            //如果try或者confirm失败了，则把库存加回去
            if (response.getSuccess() && response.getData() != null) {
                return inventoryFacadeService.increase(inventoryRequest).getData();
            }
            //如果try失败了，则直接拿返回
            return true;
        }
        return true;
    }
}
