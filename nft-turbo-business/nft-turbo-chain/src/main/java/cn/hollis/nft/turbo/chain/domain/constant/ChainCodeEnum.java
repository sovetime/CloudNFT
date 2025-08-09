package cn.hollis.nft.turbo.chain.domain.constant;

/**
 * @author whatisgod
 * @date 2024/01/17
 */
public enum ChainCodeEnum {
    /**
     * 上链成功
     */
    SUCCESS,
    /**
     * 上链中
     */
    PROCESSING,

    /**
     * 上链请求异常
     */
    CHAIN_POST_ERROR,

    /**
     * 上链返回结果不是json
     */
    CHAIN_RESULT_NOT_JSON,

    /**
     * 上链返回结果错误
     */
    CHAIN_RESULT_ERROR,
}
