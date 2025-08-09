package cn.hollis.nft.turbo.api.chain.service;

import cn.hollis.nft.turbo.api.chain.request.ChainProcessRequest;
import cn.hollis.nft.turbo.api.chain.response.ChainProcessResponse;
import cn.hollis.nft.turbo.api.chain.response.data.ChainCreateData;
import cn.hollis.nft.turbo.api.chain.response.data.ChainOperationData;


public interface ChainFacadeService {

    //创建链账户
    ChainProcessResponse<ChainCreateData> createAddr(ChainProcessRequest request);

    //上链藏品
    ChainProcessResponse<ChainOperationData> chain(ChainProcessRequest request);

    //铸造藏品
    ChainProcessResponse<ChainOperationData> mint(ChainProcessRequest request);

    //交易藏品
    ChainProcessResponse<ChainOperationData> transfer(ChainProcessRequest request);

    // 销毁藏品
    ChainProcessResponse<ChainOperationData> destroy(ChainProcessRequest request);
}
