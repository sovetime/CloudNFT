package cn.hollis.nft.turbo.auth.exception;

import cn.hollis.nft.turbo.base.exception.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;


//认证错误码
@AllArgsConstructor
@Getter
public enum AuthErrorCode implements ErrorCode {

    USER_STATUS_IS_NOT_ACTIVE("USER_STATUS_IS_NOT_ACTIVE", "用户状态不可用"),

    VERIFICATION_CODE_WRONG("VERIFICATION_CODE_WRONG", "验证码错误"),

    USER_QUERY_FAILED("USER_QUERY_FAILED", "用户信息查询失败"),

    USER_NOT_LOGIN("USER_NOT_LOGIN", "用户未登录"),

    USER_NOT_EXIST("USER_NOT_EXIST", "用户不存在");

    private String code;

    private String message;

}
