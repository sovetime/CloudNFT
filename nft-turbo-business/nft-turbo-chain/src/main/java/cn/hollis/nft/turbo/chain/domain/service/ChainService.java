package cn.hollis.nft.turbo.chain.domain.service;

import cn.hollis.nft.turbo.api.chain.request.ChainProcessRequest;
import cn.hollis.nft.turbo.api.chain.request.ChainQueryRequest;
import cn.hollis.nft.turbo.api.chain.response.ChainProcessResponse;
import cn.hollis.nft.turbo.api.chain.response.data.ChainCreateData;
import cn.hollis.nft.turbo.api.chain.response.data.ChainOperationData;
import cn.hollis.nft.turbo.api.chain.response.data.ChainResultData;
import cn.hollis.nft.turbo.chain.domain.entity.ChainOperateInfo;

/**
 * 交易链服务
 *
 * @author Hollis
 */
public interface ChainService {

    /**
     * 创建交易链地址
     *
     * @param request
     * @return
     */
    ChainProcessResponse<ChainCreateData> createAddr(ChainProcessRequest request);


    /**
     * 上链藏品
     *
     * @param request
     * @return
     */
    ChainProcessResponse<ChainOperationData> chain(ChainProcessRequest request);

    /**
     * 铸造藏品
     *
     * @param request
     * @return
     */
    ChainProcessResponse<ChainOperationData> mint(ChainProcessRequest request);

    /**
     * 交易藏品
     *
     * @param request
     * @return
     */
    ChainProcessResponse<ChainOperationData> transfer(ChainProcessRequest request);

    /**
     * 销毁藏品
     *
     * @param request
     * @return
     */
    ChainProcessResponse<ChainOperationData> destroy(ChainProcessRequest request);

    /**
     * 查询上链交易结果
     *
     * @param request
     * @return
     */
    ChainProcessResponse<ChainResultData> queryChainResult(ChainQueryRequest request);

    /**
     * 发消息
     *
     * @param chainOperateInfo
     * @param chainResultData
     */
    public void sendMsg(ChainOperateInfo chainOperateInfo, ChainResultData chainResultData);

}
