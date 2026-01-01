
package cn.time.nft.turbo.base.exception;


//限流错误码
public enum BlockErrorCode implements ErrorCode {

    //请求被限流
    REQUEST_IS_BLOCKED("REQUEST_IS_BLOCKED", "请求被限流啦~");


    private String code;


    private String message;

    BlockErrorCode(String code, String message) {
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
