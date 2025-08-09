package cn.hollis.nft.turbo.chain.domain.service;

import cn.hollis.nft.turbo.api.chain.constant.ChainOperateBizTypeEnum;
import cn.hollis.nft.turbo.api.chain.constant.ChainOperateTypeEnum;
import cn.hollis.nft.turbo.api.chain.constant.ChainType;
import cn.hollis.nft.turbo.api.chain.model.ChainOperateBody;
import cn.hollis.nft.turbo.api.chain.request.ChainProcessRequest;
import cn.hollis.nft.turbo.api.chain.request.ChainQueryRequest;
import cn.hollis.nft.turbo.api.chain.response.ChainProcessResponse;
import cn.hollis.nft.turbo.api.chain.response.data.ChainCreateData;
import cn.hollis.nft.turbo.api.chain.response.data.ChainOperationData;
import cn.hollis.nft.turbo.api.chain.response.data.ChainResultData;
import cn.hollis.nft.turbo.base.exception.RepoErrorCode;
import cn.hollis.nft.turbo.base.exception.SystemException;
import cn.hollis.nft.turbo.base.utils.BeanValidator;
import cn.hollis.nft.turbo.chain.domain.constant.ChainCodeEnum;
import cn.hollis.nft.turbo.chain.domain.constant.ChainOperateStateEnum;
import cn.hollis.nft.turbo.chain.domain.entity.ChainOperateInfo;
import cn.hollis.nft.turbo.chain.domain.entity.ChainRequest;
import cn.hollis.nft.turbo.chain.domain.response.ChainResponse;
import cn.hollis.nft.turbo.limiter.SlidingWindowRateLimiter;
import cn.hollis.turbo.stream.producer.StreamProducer;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.function.Function;

import static cn.hollis.nft.turbo.api.chain.constant.ChainOperateTypeEnum.COLLECTION_DESTROY;
import static java.util.Objects.requireNonNull;


//链服务
@Slf4j
public abstract class AbstractChainService implements ChainService {

    @Autowired
    protected ChainOperateInfoService chainOperateInfoService;

    @Autowired
    protected SlidingWindowRateLimiter slidingWindowRateLimiter;

    @Autowired
    private StreamProducer streamProducer;

    private static ThreadFactory chainResultProcessFactory = new ThreadFactoryBuilder()
            .setNameFormat("chain-result-process-pool-%d").build();

    ScheduledExecutorService scheduler = new ScheduledThreadPoolExecutor(10, chainResultProcessFactory);

    protected ChainProcessResponse doPostExecute(ChainProcessRequest chainProcessRequest, ChainOperateTypeEnum chainOperateTypeEnum,
                                                 Consumer<ChainRequest> consumer) {
        return handle(chainProcessRequest, request -> {
            Boolean rateLimitResult = slidingWindowRateLimiter.tryAcquire(
                    "limit#" + chainProcessRequest.getBizType() + chainProcessRequest.getIdentifier(), 1, 60);
            if (!rateLimitResult) {
                return new ChainProcessResponse.Builder().responseCode(ChainCodeEnum.PROCESSING.name()).data(
                        new ChainOperationData(chainProcessRequest.getIdentifier())).buildSuccess();
            }
            ChainOperateInfo chainOperateInfo = chainOperateInfoService.queryByOutBizId(chainProcessRequest.getBizId(), chainProcessRequest.getBizType(),
                    chainProcessRequest.getIdentifier());
            if (null != chainOperateInfo) {
                return duplicateResponse(chainProcessRequest, chainOperateTypeEnum, chainOperateInfo);
            }

            ChainRequest chainRequest = new ChainRequest();

            var operateInfoId = chainOperateInfoService.insertInfo(chainType(),
                    chainProcessRequest.getBizId(), chainProcessRequest.getBizType(), chainOperateTypeEnum.name(),
                    JSON.toJSONString(chainProcessRequest), chainProcessRequest.getIdentifier());
            //核心逻辑执行
            consumer.accept(chainRequest);

            ChainResponse result = doPost(chainRequest);
            log.info("wen chang post result:{}", JSON.toJSONString(result));

            boolean updateResult = chainOperateInfoService.updateResult(operateInfoId, null,
                    result.getSuccess() ? result.getData().toString() : result.getError().toString());

            if (!updateResult) {
                throw new SystemException(RepoErrorCode.UPDATE_FAILED);
            }

            ChainProcessResponse response = buildResult(result, chainProcessRequest, chainOperateTypeEnum);
            if (response.getSuccess() && chainOperateTypeEnum != ChainOperateTypeEnum.USER_CREATE) {
                //延迟5秒钟之后查询状态并发送 MQ 消息通知上游
                scheduler.schedule(() -> {
                    try {
                        ChainOperateInfo operateInfo = chainOperateInfoService.queryByOutBizId(chainProcessRequest.getBizId(), chainProcessRequest.getBizType(),
                                chainProcessRequest.getIdentifier());
                        ChainProcessResponse<ChainResultData> queryChainResult = queryChainResult(
                                new ChainQueryRequest(chainProcessRequest.getIdentifier(), operateInfoId.toString()));
                        if (queryChainResult.getSuccess() && queryChainResult.getData() != null) {
                            if (StringUtils.equals(queryChainResult.getData().getState(), ChainOperateStateEnum.SUCCEED.name())) {
                                this.sendMsg(operateInfo, queryChainResult.getData());

                                chainOperateInfoService.updateResult(operateInfoId,
                                        ChainOperateStateEnum.SUCCEED, null);
                            }
                        }
                    } catch (Exception e) {
                        log.error("query chain result failed,", e);
                    }
                }, 5, TimeUnit.SECONDS);
            }

            return response;
        });
    }

    private ChainProcessResponse duplicateResponse(ChainProcessRequest chainProcessRequest, ChainOperateTypeEnum chainOperateTypeEnum, ChainOperateInfo chainOperateInfo) {
        if (chainOperateTypeEnum == ChainOperateTypeEnum.USER_CREATE) {
            JSONObject jsonObject = JSON.parseObject(chainOperateInfo.getResult(), JSONObject.class);
            String blockChainAddr = (String) jsonObject.get("native_address");
            String blockChainName = chainProcessRequest.getUserId();
            return new ChainProcessResponse.Builder().responseCode(ChainCodeEnum.SUCCESS.name()).data(
                    new ChainCreateData(chainProcessRequest.getIdentifier(), blockChainAddr, blockChainName,
                            chainType())).buildSuccess();
        } else {
            return new ChainProcessResponse.Builder().responseCode(ChainCodeEnum.PROCESSING.name()).data(
                    new ChainOperationData(chainProcessRequest.getIdentifier())).buildSuccess();
        }
    }

    protected ChainProcessResponse doDeleteExecute(ChainProcessRequest chainProcessRequest,
                                                   Consumer<ChainRequest> consumer) {
        return handle(chainProcessRequest, request -> {

            Boolean rateLimitResult = slidingWindowRateLimiter.tryAcquire(
                    "limit#" + chainProcessRequest.getBizType() + chainProcessRequest.getIdentifier(), 1, 60);
            if (!rateLimitResult) {
                return new ChainProcessResponse.Builder().responseCode(ChainCodeEnum.PROCESSING.name()).data(
                        new ChainOperationData(chainProcessRequest.getIdentifier())).buildSuccess();
            }
            ChainOperateInfo chainOperateInfo = chainOperateInfoService.queryByOutBizId(chainProcessRequest.getBizId(), chainProcessRequest.getBizType(),
                    chainProcessRequest.getIdentifier());
            if (null != chainOperateInfo) {
                return new ChainProcessResponse.Builder().responseCode(ChainCodeEnum.PROCESSING.name()).data(
                        new ChainOperationData(chainProcessRequest.getIdentifier())).buildSuccess();
            }

            ChainRequest chainRequest = new ChainRequest();

            var operateInfoId = chainOperateInfoService.insertInfo(chainType(),
                    chainProcessRequest.getBizId(), chainProcessRequest.getBizType(), COLLECTION_DESTROY.name(),
                    JSON.toJSONString(chainProcessRequest), chainProcessRequest.getIdentifier());
            //核心逻辑执行
            consumer.accept(chainRequest);

            ChainResponse result = doDelete(chainRequest);
            log.info("wen chang delete result:{}", result);

            boolean updateResult = chainOperateInfoService.updateResult(operateInfoId, null,
                    result.getSuccess() ? result.getData().toString() : result.getError().toString());

            if (!updateResult) {
                throw new SystemException(RepoErrorCode.UPDATE_FAILED);
            }

            ChainProcessResponse response = buildResult(result, chainProcessRequest, COLLECTION_DESTROY);

            if (response.getSuccess()) {
                //延迟5秒钟之后查询状态并发送 MQ 消息通知上游
                scheduler.schedule(() -> {
                    try {
                        ChainOperateInfo operateInfo = chainOperateInfoService.queryByOutBizId(chainProcessRequest.getBizId(), chainProcessRequest.getBizType(),
                                chainProcessRequest.getIdentifier());
                        ChainProcessResponse<ChainResultData> queryChainResult = queryChainResult(
                                new ChainQueryRequest(chainProcessRequest.getIdentifier(), operateInfoId.toString()));
                        if (queryChainResult.getSuccess() && queryChainResult.getData() != null) {
                            if (StringUtils.equals(queryChainResult.getData().getState(), ChainOperateStateEnum.SUCCEED.name())) {
                                this.sendMsg(operateInfo, queryChainResult.getData());

                                chainOperateInfoService.updateResult(operateInfoId,
                                        ChainOperateStateEnum.SUCCEED, null);
                            }
                        }
                    } catch (Exception e) {
                        log.error("query chain result failed,", e);
                    }
                }, 5, TimeUnit.SECONDS);
            }
            return response;
        });
    }

    /**
     * 结果构造
     *
     * @param result
     * @param chainProcessRequest
     * @param chainOperateTypeEnum
     * @return
     */
    private ChainProcessResponse buildResult(ChainResponse result, ChainProcessRequest chainProcessRequest, ChainOperateTypeEnum chainOperateTypeEnum) {

        if (result.getSuccess()) {
            if (chainOperateTypeEnum == ChainOperateTypeEnum.USER_CREATE) {
                JSONObject dataJsonObject = result.getData();
                String blockChainAddr = (String) dataJsonObject.get("native_address");
                String blockChainName = chainProcessRequest.getUserId();
                return new ChainProcessResponse.Builder().data(
                        new ChainCreateData(chainProcessRequest.getIdentifier(), blockChainAddr, blockChainName,
                                chainType())).buildSuccess();
            } else {
                return new ChainProcessResponse.Builder().responseCode(ChainCodeEnum.PROCESSING.name()).data(
                        new ChainOperationData(chainProcessRequest.getIdentifier())).buildSuccess();
            }

        }
        return new ChainProcessResponse.Builder().responseCode(result.getResponseCode()).responseMessage(
                result.getResponseMessage()).buildFailed();
    }

    /**
     * 异步发送消息
     *
     * @param chainOperateInfo
     * @param chainResultData
     */
    @Override
    public void sendMsg(ChainOperateInfo chainOperateInfo, ChainResultData chainResultData) {
        ChainOperateBody chainOperateBody = new ChainOperateBody();
        chainOperateBody.setBizId(chainOperateInfo.getBizId());
        chainOperateBody.setBizType(ChainOperateBizTypeEnum.valueOf(chainOperateInfo.getBizType()));
        chainOperateBody.setOperateInfoId(chainOperateInfo.getId());
        chainOperateBody.setOperateType(ChainOperateTypeEnum.valueOf(chainOperateInfo.getOperateType()));
        chainOperateBody.setChainType(ChainType.valueOf(chainOperateInfo.getChainType()));
        chainOperateBody.setChainResultData(chainResultData);
        //消息监听：ChainOperateResultListener
        streamProducer.send("chain-out-0", chainOperateInfo.getBizType(), JSON.toJSONString(chainOperateBody));
    }

    /**
     * 执行post方法
     *
     * @param chainRequest
     * @return
     */
    protected abstract ChainResponse doPost(ChainRequest chainRequest);

    /**
     * 执行delete方法
     *
     * @param chainRequest
     * @return
     */
    protected abstract ChainResponse doDelete(ChainRequest chainRequest);

    /**
     * 执行get方法
     *
     * @param chainRequest
     * @return
     */
    protected abstract ChainResponse doGetQuery(ChainRequest chainRequest);

    public static <T, R extends ChainProcessResponse> ChainProcessResponse handle(T request, Function<T, R> function) {
        requireNonNull(request);
        BeanValidator.validateObject(request);
        return function.apply(request);
    }

    /**
     * 返回chainType
     *
     * @return
     */
    protected abstract String chainType();

}
