
package cn.hollis.nft.turbo.base.exception;


import lombok.AllArgsConstructor;

//业务通用错误码
@AllArgsConstructor
public enum BizErrorCode implements ErrorCode {

    HTTP_CLIENT_ERROR("HTTP_CLIENT_ERROR", "HTTP 客户端错误"),

    HTTP_SERVER_ERROR("HTTP_SERVER_ERROR", "HTTP 服务端错误"),

    SEND_NOTICE_DUPLICATED("SEND_NOTICE_DUPLICATED", "不允许重复发送通知"),

    NOTICE_SAVE_FAILED("NOTICE_SAVE_FAILED", "通知保存失败"),

    STATE_MACHINE_TRANSITION_FAILED("STATE_MACHINE_TRANSITION_FAILED", "状态机转换失败"),

    DUPLICATED("DUPLICATED", "重复请求"),

    REMOTE_CALL_RESPONSE_IS_NULL("REMOTE_CALL_RESPONSE_IS_NULL", "远程调用返回结果为空"),

    REMOTE_CALL_RESPONSE_IS_FAILED("REMOTE_CALL_RESPONSE_IS_FAILED", "远程调用返回结果失败");


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
