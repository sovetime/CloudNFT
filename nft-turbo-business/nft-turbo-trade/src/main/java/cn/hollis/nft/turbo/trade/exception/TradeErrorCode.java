package cn.hollis.nft.turbo.trade.exception;

import cn.hollis.nft.turbo.base.exception.ErrorCode;
import lombok.AllArgsConstructor;


@AllArgsConstructor
public enum TradeErrorCode implements ErrorCode {

    ORDER_CREATE_FAILED("ORDER_CREATE_FAILED", "订单创建失败"),

    PAY_PERMISSION_DENIED("PAY_PERMISSION_DENIED", "无支付权限"),

    PAY_CREATE_FAILED("PAY_CREATE_FAILED", "支付创建失败"),

    GOODS_NOT_FOR_SALE("GOODS_NOT_FOR_SALE", "商品不可售卖"),

    GOODS_NOT_EXIST("GOODS_NOT_EXIST", "商品不存在"),

    ORDER_IS_CANNOT_PAY("ORDER_IS_CANNOT_PAY", "订单不可支付"),

    ORDER_CANCEL_FAILED("ORDER_CANCEL_FAILED", "订单取消失败"),

    GOODS_BOOK_FAILED("GOODS_BOOK_FAILED", "商品预约失败"),

    INVENTORY_ROLLBACK_FAILED("INVENTORY_ROLLBACK_FAILED", "库存回滚失败"),

    NORMAL_BUY_TCC_CONFIRM_FAILED("NORMAL_BUY_TCC_CONFIRM_FAILED","订单创建失败"),

    NORMAL_BUY_TCC_CANCEL_FAILED("NORMAL_BUY_TCC_CANCEL_FAILED","订单创建失败");

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
