package cn.hollis.nft.turbo.admin.infrastructure.exception;

import cn.hollis.nft.turbo.base.exception.ErrorCode;
import lombok.AllArgsConstructor;


//后台错误码
@AllArgsConstructor
public enum AdminErrorCode implements ErrorCode {

    ADMIN_UPLOAD_PICTURE_FAIL("ADMIN_UPLOAD_PICTURE_FAIL", "后台上传图片失败"),
    ADMIN_USER_NOT_EXIST("ADMIN_USER_NOT_EXIST", "后台用户不存在"),
    ADMIN_USER_PASSWORD_ERROR("ADMIN_USER_PASSWORD_ERROR", "后台用户密码错误"),
    USER_NOT_LOGIN("USER_NOT_LOGIN", "后台用户未登录");

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
