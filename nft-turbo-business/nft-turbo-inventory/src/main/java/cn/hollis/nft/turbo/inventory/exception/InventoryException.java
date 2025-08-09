package cn.hollis.nft.turbo.inventory.exception;

import cn.hollis.nft.turbo.base.exception.BizException;
import cn.hollis.nft.turbo.base.exception.ErrorCode;

/**
 * 库存异常
 *
 * @author hollis
 */
public class InventoryException extends BizException {

    public InventoryException(ErrorCode errorCode) {
        super(errorCode);
    }

    public InventoryException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }

    public InventoryException(String message, Throwable cause, ErrorCode errorCode) {
        super(message, cause, errorCode);
    }

    public InventoryException(Throwable cause, ErrorCode errorCode) {
        super(cause, errorCode);
    }

    public InventoryException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, ErrorCode errorCode) {
        super(message, cause, enableSuppression, writableStackTrace, errorCode);
    }

}
