package cn.hollis.nft.turbo.inventory.exception;

import cn.hollis.nft.turbo.base.exception.ErrorCode;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum InventoryErrorCode implements ErrorCode {


    INVENTORY_UPDATE_FAILED("INVENTORY_UPDATE_FAILED", "库存更新失败");

    private String code;

    private String message;

    @Override
    public String getCode() {
        return this.code;
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}
