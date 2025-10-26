package cn.hollis.nft.turbo.base.exception;


import lombok.AllArgsConstructor;

//错误码
@AllArgsConstructor
public enum RepoErrorCode implements ErrorCode {

    UNKNOWN_ERROR("UNKNOWN_ERROR", "未知错误"),

    INSERT_FAILED("INSERT_FAILED", "数据库插入失败"),

    UPDATE_FAILED("UPDATE_FAILED", "数据库更新失败");

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
