package chain.job;

import chain.ChainBaseTest;
import cn.hollis.nft.turbo.api.chain.constant.ChainOperateBizTypeEnum;
import cn.hollis.nft.turbo.api.chain.request.ChainProcessRequest;
import cn.hollis.nft.turbo.api.chain.response.ChainProcessResponse;
import cn.hollis.nft.turbo.api.chain.response.data.ChainOperationData;
import cn.hollis.nft.turbo.chain.domain.constant.ChainOperateStateEnum;
import cn.hollis.nft.turbo.chain.domain.service.ChainOperateInfoService;
import cn.hollis.nft.turbo.chain.domain.service.ChainService;
import cn.hollis.nft.turbo.chain.job.ChainProcessJob;
import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.Date;

import static cn.hollis.nft.turbo.api.chain.constant.ChainOperateBizTypeEnum.CHAIN_OPERATION;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

public class ChainProcessJobTest extends ChainBaseTest {
    @Autowired
    private ChainProcessJob chainProcessJob;

    @Autowired
    @Qualifier("wenChangChainService")
    private ChainService wenChangChainService;

    @Autowired
    private ChainOperateInfoService chainOperateInfoService;

    @Before
    public void init() {
        when(slidingWindowRateLimiter.tryAcquire(anyString(), anyInt(), anyInt())).thenReturn(true);
    }

    @Test
    @Ignore
    public void jobRunTest() throws InterruptedException {
        ChainProcessRequest request = new ChainProcessRequest();
        request.setIdentifier(String.valueOf(new Date().getTime()));
        request.setClassId("id" + new Date().getTime());
        request.setClassName("name" + new Date().getTime());
        request.setBizId("9");
        request.setBizType(ChainOperateBizTypeEnum.COLLECTION.name());
        var result = this.chain(request);
        System.out.println("runResult : " + JSON.toJSONString(result));
        Thread.sleep(6000);
        chainProcessJob.execute();
        var res = chainOperateInfoService.queryByOutBizId(result.getData().getOperationId(), CHAIN_OPERATION.name(), result.getData().getOperationId());
        Assert.assertTrue(res.getState()==ChainOperateStateEnum.SUCCEED);
    }

    private ChainProcessResponse<ChainOperationData> chain(ChainProcessRequest request) {
        return wenChangChainService.chain(request);
    }
}
