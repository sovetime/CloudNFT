package cn.hollis.nft.turbo.trade.exception;

import cn.hollis.nft.turbo.base.exception.BizException;
import cn.hollis.nft.turbo.base.exception.ErrorCode;

/**
 * @author Hollis
 */
public class TradeException extends BizException {
    public TradeException(ErrorCode errorCode) {
        super(errorCode);
    }

    public TradeException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }

    public TradeException(String message, Throwable cause, ErrorCode errorCode) {
        super(message, cause, errorCode);
    }

    public TradeException(Throwable cause, ErrorCode errorCode) {
        super(cause, errorCode);
    }

    public TradeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, ErrorCode errorCode) {
        super(message, cause, enableSuppression, writableStackTrace, errorCode);
    }
}
