package cn.hollis.nft.turbo.api.order.constant;

import cn.hollis.nft.turbo.base.exception.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

//支付错误码
@Getter
@AllArgsConstructor
public enum OrderErrorCode implements ErrorCode {

    ORDER_NOT_EXIST("ORDER_NOT_EXIST", "订单不存在"),

    PERMISSION_DENIED("PERMISSION_DENIED", "无权限操作"),

    UPDATE_ORDER_FAILED("UPDATE_ORDER_FAILED", "更新订单失败"),

    CREATE_ORDER_FAILED("CREATE_ORDER_FAILED", "创建订单失败"),

    ORDER_ALREADY_PAID("ORDER_ALREADY_PAID", "订单已支付"),

    ORDER_ALREADY_CLOSED("ORDER_ALREADY_CLOSED", "订单已关闭"),

    ORDER_STATE_TRANSFER_ILLEGAL("ORDER_STATE_TRANSFER_ILLEGAL", "订单状态转移非法"),

    INVENTORY_DECREASE_FAILED("INVENTORY_DECREASE_FAILED", "库存扣减失败"),

    INVENTORY_INCREASE_FAILED("INVENTORY_INCREASE_FAILED", "库存增加失败"),

    ORDER_CREATE_VALID_FAILED("ORDER_CREATE_VALID_FAILED", "订单创建校验失败"),

    ORDER_CREATE_PRE_VALID_FAILED("ORDER_CREATE_PRE_VALID_FAILED", "订单创建前置校验失败"),

    ORDER_IS_EXPIRED("OEDER_IS_EXPIRED", "订单已过期"),

    BUYER_IS_PLATFORM_USER("BUYER_IS_PLATFORM_USER", "买家不能是平台用户"),

    USER_NOT_EXIST("USER_NOT_EXIST", "买家不存在"),

    TRANSFER_SELF_ERROR("TRANSFER_SELF_ERROR", "藏品不能转让给自己"),

    BUYER_STATUS_ABNORMAL("BUYER_STATUS_ABNORMAL", "买家状态异常"),

    BUYER_NOT_AUTH("BUYER_NOT_AUTH", "买家未完成实名认证"),

    INVENTORY_NOT_ENOUGH("INVENTORY_NOT_ENOUGH", "库存不足"),


    GOODS_NOT_AVAILABLE("GOODS_NOT_AVAILABLE", "商品不可用"),

    DUPLICATED_BUY("DUPLICATED_BUY", "重复下单"),

    GOODS_NOT_BOOKED("GOODS_NOT_BOOKED", "未预约无法购买该商品"),

    GOODS_NOT_BOOKED_BUY("GOODS_NOT_BOOKED_BUY", "商品还未开放购买"),

    GOODS_PRICE_CHANGED("GOODS_PRICE_CHANGED", "商品价格发生变化"),

    COLLECTION_NO_EXIST("COLLECTION_NO_EXIST","藏品不存在" );


    private String code;

    private String message;

}
