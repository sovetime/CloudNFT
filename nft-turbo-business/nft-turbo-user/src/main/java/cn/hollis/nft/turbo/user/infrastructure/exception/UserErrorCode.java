package cn.hollis.nft.turbo.user.infrastructure.exception;

import cn.hollis.nft.turbo.base.exception.ErrorCode;
import lombok.AllArgsConstructor;


//用户错误码
@AllArgsConstructor
public enum UserErrorCode implements ErrorCode {

    DUPLICATE_TELEPHONE_NUMBER("DUPLICATE_TELEPHONE_NUMBER", "重复电话号码"),

    USER_STATUS_IS_NOT_INIT("USER_STATUS_IS_NOT_INIT", "用户状态不能进行实名认证"),

    USER_NOT_EXIST("USER_NOT_EXIST", "用户不存在"),

    USER_STATUS_CANT_OPERATE("USER_STATUS_CANT_OPERATE", "用户状态不能进行操作"),

    USER_STATUS_IS_NOT_ACTIVE("USER_STATUS_IS_NOT_ACTIVE", "用户状态未激活成功"),

    USER_OPERATE_FAILED("USER_OPERATE_FAILED", "用户操作失败"),

    USER_AUTH_FAIL("USER_AUTH_FAIL", "用户实名认证失败"),

    USER_PASSWD_CHECK_FAIL("USER_PASSWD_CHECK_FAIL", "用户密码校验失败"),

    USER_QUERY_FAIL("USER_QUERY_FAIL", "用户查询失败"),

    NICK_NAME_EXIST("NICK_NAME_EXIST", "用户名已存在"),

    USER_CREATE_CHAIN_FAIL("USER_CREATE_CHAIN_FAIL", "用户创建链账号失败"),

    USER_STATUS_IS_NOT_AUTH("USER_STATUS_IS_NOT_AUTH", "用户状态不能进行激活"),

    USER_UPLOAD_PICTURE_FAIL("USER_UPLOAD_PICTURE_FAIL", "用户上传图片失败");

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
