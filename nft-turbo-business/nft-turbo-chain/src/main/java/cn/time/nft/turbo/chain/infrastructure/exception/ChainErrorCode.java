package cn.time.nft.turbo.chain.infrastructure.exception;

import cn.time.nft.turbo.base.exception.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;


//区块链错误码
@AllArgsConstructor
@Getter
public enum ChainErrorCode implements ErrorCode {

    CHAIN_QUERY_FAIL("CHAIN_QUERY_FAIL", "区块链查询失败"),

    CHAIN_PROCESS_STATE_ERROR("CHAIN_PROCESS_STATE_ERROR", "区块链状态不是成功")
    ;

    private String code;

    private String message;

}
