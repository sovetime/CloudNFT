package cn.hollis.nft.turbo.chain.infrastructure.exception;

import cn.hollis.nft.turbo.base.exception.ErrorCode;

/**
 * 区块链错误码
 *
 * @author Hollis
 */
public enum ChainErrorCode implements ErrorCode {
    /**
     * 区块链查询失败
     */
    CHAIN_QUERY_FAIL("CHAIN_QUERY_FAIL", "区块链查询失败"),
    /**
     * 区块链状态不是成功
     */
    CHAIN_PROCESS_STATE_ERROR("CHAIN_PROCESS_STATE_ERROR", "区块链状态不是成功"),
    ;

    private String code;


    private String message;

    ChainErrorCode(String code, String message) {
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
