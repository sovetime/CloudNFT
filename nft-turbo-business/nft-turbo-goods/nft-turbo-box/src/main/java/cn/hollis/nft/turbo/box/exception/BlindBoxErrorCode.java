package cn.hollis.nft.turbo.box.exception;

import cn.hollis.nft.turbo.base.exception.ErrorCode;

/**
 * 盲盒相关错误码
 *
 * @author hollis
 */
public enum BlindBoxErrorCode implements ErrorCode {
    /**
     * 盲盒信息保存失败
     */
    BLIND_BOX_SAVE_FAILED("BLIND_BOX_SAVE_FAILED", "盲盒信息保存失败"),

    /**
     * 盲盒条目信息保存失败
     */
    BLIND_BOX_ITEM_SAVE_FAILED("BLIND_BOX_ITEM_SAVE_FAILED", "盲盒条目信息保存失败"),
    /**
     * 盲盒条目分配失败
     */
    BLIND_BOX_ITEM_ALLOCATE_FAILED("BLIND_BOX_ITEM_ALLOCATE_FAILED", "盲盒条目分配失败"),

    /**
     * 盲盒开盒错误
     */
    BLIND_BOX_OPEN_FAILED("BLIND_BOX_OPEN_FAILED", "盲盒开盒错误"),

    /**
     * 盲盒信息更新失败
     */
    BLIND_BOX_UPDATE_FAILED("BLIND_BOX_UPDATE_FAILED", "盲盒信息更新失败"),

    /**
     * 盲盒流水信息保存失败
     */
    BLIND_BOX_STREAM_SAVE_FAILED("BLIND_BOX_STREAM_SAVE_FAILED", "盲盒流水信息保存失败"),
    /**
     * 盲盒库存相关错误
     */
    BLIND_BOX_INVENTORY_UPDATE_FAILED("BLIND_BOX_INVENTORY_UPDATE_FAILED", "盲盒库存更新失败"),

    /**
     * 盲盒条目不存在
     */
    BLIND_BOX_ITEM_NOT_EXIST("BLIND_BOX_ITEM_NOT_EXIST", "盲盒条目不存在"),

    /**
     * 库存解冻失败
     */
    INVENTORY_UNFREEZE_FAILED("INVENTORY_UNFREEZE_FAILED", "库存解冻失败"),
    /**
     * 没有盲盒条目的打开权限
     */
    BLIND_BOX_ITEM_OPEN_PERMISSION_CHECK_FAILED("BLIND_BOX_ITEM_OPEN_PERMISSION_CHECK_FAILED", "没有 blindBoxItemId 的打开权限");


    private String code;

    private String message;

    BlindBoxErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public String getCode() {
        return this.code;
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}
