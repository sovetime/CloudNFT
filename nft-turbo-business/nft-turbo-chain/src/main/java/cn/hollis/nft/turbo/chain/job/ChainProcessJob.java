package cn.hollis.nft.turbo.chain.job;

import cn.hollis.nft.turbo.api.chain.constant.ChainType;
import cn.hollis.nft.turbo.api.chain.request.ChainQueryRequest;
import cn.hollis.nft.turbo.api.chain.response.ChainProcessResponse;
import cn.hollis.nft.turbo.api.chain.response.data.ChainResultData;
import cn.hollis.nft.turbo.base.exception.BizException;
import cn.hollis.nft.turbo.base.exception.RepoErrorCode;
import cn.hollis.nft.turbo.base.exception.SystemException;
import cn.hollis.nft.turbo.chain.domain.constant.ChainOperateStateEnum;
import cn.hollis.nft.turbo.chain.domain.entity.ChainOperateInfo;
import cn.hollis.nft.turbo.chain.domain.service.ChainOperateInfoService;
import cn.hollis.nft.turbo.chain.domain.service.ChainService;
import cn.hollis.nft.turbo.chain.domain.service.ChainServiceFactory;
import cn.hollis.nft.turbo.chain.infrastructure.exception.ChainErrorCode;
import cn.hollis.nft.turbo.chain.infrastructure.exception.ChainException;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.annotation.XxlJob;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 链上处理任务
 *
 * @author Hollis
 */
@Component
public class ChainProcessJob {

    @Autowired
    private ChainOperateInfoService chainOperateInfoService;

    @Autowired
    private ChainServiceFactory chainServiceFactory;

    private static final int PAGE_SIZE = 5;

    private static final Logger LOG = LoggerFactory.getLogger(ChainProcessJob.class);

    @XxlJob("unFinishOperateExecute")
    public ReturnT<String> execute() {

        Long minId = chainOperateInfoService.queryMinIdByState(ChainOperateStateEnum.PROCESSING.name());

        List<ChainOperateInfo> chainOperateInfos = chainOperateInfoService.pageQueryOperateInfoByState(
                ChainOperateStateEnum.PROCESSING.name(), PAGE_SIZE, minId);

        while (CollectionUtils.isNotEmpty(chainOperateInfos)) {
            chainOperateInfos.forEach(this::executeSingle);
            Long maxId = chainOperateInfos.stream().mapToLong(ChainOperateInfo::getId).max().orElse(Long.MAX_VALUE);
            chainOperateInfos = chainOperateInfoService.pageQueryOperateInfoByState(ChainOperateStateEnum.PROCESSING.name()
                    , PAGE_SIZE, maxId + 1);
        }

        return ReturnT.SUCCESS;
    }

    private void executeSingle(ChainOperateInfo chainOperateInfo) {

        LOG.info("start to execute unfinish operate , id is {}", chainOperateInfo.getId());
        try {
            ChainService chainService = chainServiceFactory.get(ChainType.valueOf(chainOperateInfo.getChainType()));
            ChainQueryRequest query = new ChainQueryRequest();
            query.setOperationId(chainOperateInfo.getOutBizId());
            ChainProcessResponse<ChainResultData> chainProcessResponse = chainService.queryChainResult(query);
            if (!chainProcessResponse.getSuccess()) {
                throw new ChainException(ChainErrorCode.CHAIN_QUERY_FAIL);
            }
            ChainResultData chainResultData = chainProcessResponse.getData();
            //异常情况判断
            if (null == chainResultData) {
                throw new ChainException(ChainErrorCode.CHAIN_QUERY_FAIL);
            }
            if (!StringUtils.equals(chainResultData.getState(), ChainOperateStateEnum.SUCCEED.name())) {
                throw new BizException(ChainErrorCode.CHAIN_PROCESS_STATE_ERROR);
            }
            //成功情况处理
            if (StringUtils.equals(chainResultData.getState(), ChainOperateStateEnum.SUCCEED.name())) {
                //发送消息
                chainService.sendMsg(chainOperateInfo, chainResultData);
                //更新操作表状态
                //需要做核对，如果操作表状态成功，相应业务表状态处理中，需要核对出来
                boolean updateResult = chainOperateInfoService.updateResult(chainOperateInfo.getId(),
                        ChainOperateStateEnum.SUCCEED, null);
                if (!updateResult) {
                    throw new SystemException(RepoErrorCode.UPDATE_FAILED);
                }
            }
        } catch (Exception e) {
            LOG.error("start to execute unfinish operate error, id is {}, error is {}", chainOperateInfo.getId(), e);
        }
    }


}
