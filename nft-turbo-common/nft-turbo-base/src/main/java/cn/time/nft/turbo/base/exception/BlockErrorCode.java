
package cn.time.nft.turbo.base.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

//限流错误码
@AllArgsConstructor
@Getter
public enum BlockErrorCode implements ErrorCode {

    REQUEST_IS_BLOCKED("REQUEST_IS_BLOCKED", "请求被限流啦~");

    private String code;

    private String message;
}
