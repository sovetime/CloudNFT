package cn.hollis.nft.turbo.chain.domain.service.impl;

import cn.hollis.nft.turbo.api.chain.constant.ChainOperateBizTypeEnum;
import cn.hollis.nft.turbo.api.chain.constant.ChainOperateTypeEnum;
import cn.hollis.nft.turbo.api.chain.constant.ChainType;
import cn.hollis.nft.turbo.api.chain.request.ChainProcessRequest;
import cn.hollis.nft.turbo.api.chain.request.ChainQueryRequest;
import cn.hollis.nft.turbo.api.chain.response.ChainProcessResponse;
import cn.hollis.nft.turbo.api.chain.response.data.ChainCreateData;
import cn.hollis.nft.turbo.api.chain.response.data.ChainOperationData;
import cn.hollis.nft.turbo.api.chain.response.data.ChainResultData;
import cn.hollis.nft.turbo.chain.domain.constant.ChainCodeEnum;
import cn.hollis.nft.turbo.chain.domain.constant.ChainOperateStateEnum;
import cn.hollis.nft.turbo.chain.domain.entity.ChainRequest;
import cn.hollis.nft.turbo.chain.domain.response.ChainResponse;
import cn.hollis.nft.turbo.chain.domain.service.AbstractChainService;
import com.alibaba.fastjson2.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

/**
 * mock链服务
 *
 * @author hollis
 */
@Service("mockChainService")
public class MockChainServiceImpl extends AbstractChainService {

    @Override
    public ChainProcessResponse<ChainCreateData> createAddr(ChainProcessRequest request) {
        return new ChainProcessResponse.Builder().responseCode(ChainCodeEnum.SUCCESS.name()).data(
                new ChainCreateData(request.getIdentifier(), UUID.randomUUID().toString(), "mockBlockChainName",
                        ChainType.MOCK.name())).buildSuccess();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ChainProcessResponse<ChainOperationData> mint(ChainProcessRequest request) {
        return doPostExecute(request, ChainOperateTypeEnum.COLLECTION_MINT, chainRequest -> {
        });
    }

    @Override
    public ChainProcessResponse<ChainOperationData> chain(ChainProcessRequest chainProcessRequest) {
        if (StringUtils.equals(ChainOperateBizTypeEnum.BLIND_BOX.name(), chainProcessRequest.getBizType())) {
            return doPostExecute(chainProcessRequest, ChainOperateTypeEnum.BLIND_BOX_CHAIN, chainRequest -> {
            });
        } else {
            return doPostExecute(chainProcessRequest, ChainOperateTypeEnum.COLLECTION_CHAIN, chainRequest -> {
            });
        }
    }

    @Override
    public ChainProcessResponse<ChainOperationData> transfer(ChainProcessRequest request) {
        return doPostExecute(request, ChainOperateTypeEnum.COLLECTION_TRANSFER, chainRequest -> {
        });
    }

    @Override
    public ChainProcessResponse<ChainOperationData> destroy(ChainProcessRequest request) {
        return doPostExecute(request, ChainOperateTypeEnum.COLLECTION_DESTROY, chainRequest -> {
        });
    }

    @Override
    public ChainProcessResponse<ChainResultData> queryChainResult(ChainQueryRequest request) {
        ChainProcessResponse<ChainResultData> response = new ChainProcessResponse<>();
        response.setSuccess(true);
        response.setResponseCode("200");
        response.setResponseMessage("SUCCESS");
        ChainResultData data = new ChainResultData();
        data.setTxHash(UUID.randomUUID().toString());
        data.setNftId("nftId");
        data.setState(ChainOperateStateEnum.SUCCEED.name());
        response.setData(data);
        return response;
    }

    @Override
    protected ChainResponse doPost(ChainRequest chainRequest) {
        ChainResponse chainResponse = new ChainResponse();
        chainResponse.setSuccess(true);
        JSONObject data = new JSONObject();
        data.put("success",true);
        data.put("chainType","mock");
        chainResponse.setData(data);
        return chainResponse;
    }

    @Override
    protected ChainResponse doDelete(ChainRequest chainRequest) {
        ChainResponse chainResponse = new ChainResponse();
        chainResponse.setSuccess(true);
        JSONObject data = new JSONObject();
        data.put("success",true);
        data.put("chainType","mock");
        chainResponse.setData(data);
        return chainResponse;
    }

    @Override
    protected ChainResponse doGetQuery(ChainRequest chainRequest) {
        ChainResponse chainResponse = new ChainResponse();
        chainResponse.setSuccess(true);
        JSONObject data = new JSONObject();
        data.put("success",true);
        data.put("chainType","mock");
        chainResponse.setData(data);
        return chainResponse;
    }

    @Override
    protected String chainType() {
        return ChainType.MOCK.name();
    }
}
