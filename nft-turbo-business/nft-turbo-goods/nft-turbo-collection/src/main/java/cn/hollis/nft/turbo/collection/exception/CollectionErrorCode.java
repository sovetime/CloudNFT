package cn.hollis.nft.turbo.collection.exception;

import cn.hollis.nft.turbo.base.exception.ErrorCode;
import lombok.AllArgsConstructor;


//藏品相关错误码
@AllArgsConstructor
public enum CollectionErrorCode implements ErrorCode {

    COLLECTION_SAVE_FAILED("COLLECTION_SAVE_FAILED", "藏品信息保存失败"),

    COLLECTION_UPDATE_FAILED("COLLECTION_UPDATE_FAILED", "藏品信息更新失败"),

    COLLECTION_USER_QUERY_FAIL("COLLECTION_USER_QUERY_FAIL", "查询用户信息失败"),

    HELD_COLLECTION_SAVE_FAILED("HELD_COLLECTION_SAVE_FAILED", "藏品持有信息保存失败"),

    HELD_COLLECTION_OWNER_CHECK_ERROR("HELD_COLLECTION_OWNER_CHECK_ERROR", "藏品持有用户校验错误"),

    HELD_COLLECTION_STATE_CHECK_ERROR("HELD_COLLECTION_STATE_CHECK_ERROR", "藏品状态校验错误"),

    COLLECTION_QUERY_FAIL("COLLECTION_QUERY_FAIL", "查询藏品信息失败"),

    HELD_COLLECTION_QUERY_FAIL("HELD_COLLECTION_QUERY_FAIL", "查询持有藏品信息失败"),

    COLLECTION_INVENTORY_UPDATE_FAILED("COLLECTION_INVENTORY_UPDATE_FAILED", "藏品库存更新失败"),

    COLLECTION_STREAM_SAVE_FAILED("COLLECTION_STREAM_SAVE_FAILED", "藏品流水信息保存失败"),

    COLLECTION_SNAPSHOT_SAVE_FAILED("COLLECTION_SNAPSHOT_SAVE_FAILED", "藏品快照信息保存失败"),

    HELD_COLLECTION_STREAM_SAVE_FAILED("HELD_COLLECTION_STREAM_SAVE_FAILED", "藏品持有流水信息保存失败"),

    COLLECTION_STREAM_EXIST("COLLECTION_STREAM_EXIST", "藏品流水信息已存在"),

    INVENTORY_UNFREEZE_FAILED("INVENTORY_UNFREEZE_FAILED", "未冻结库存无法解冻"),

    COLLECTION_AIRDROP_STREAM_UPDATE_FAILED("COLLECTION_AIRDROP_STREAM_UPDATE_FAILED", "藏品空投流水更新失败");


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
