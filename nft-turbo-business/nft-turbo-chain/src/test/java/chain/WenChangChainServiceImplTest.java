package chain;

import cn.hollis.nft.turbo.chain.domain.constant.WenChangChainConfiguration;
import cn.hollis.nft.turbo.api.chain.request.ChainProcessRequest;
import cn.hollis.nft.turbo.api.chain.request.ChainQueryRequest;
import cn.hollis.nft.turbo.api.chain.response.ChainProcessResponse;
import cn.hollis.nft.turbo.api.chain.response.data.ChainCreateData;
import cn.hollis.nft.turbo.chain.domain.entity.WenChangCreateBody;
import cn.hollis.nft.turbo.chain.domain.response.ChainResponse;
import cn.hollis.nft.turbo.chain.domain.service.ChainService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestClient;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSON;

import static cn.hollis.nft.turbo.chain.infrastructure.utils.WenChangChainUtils.configureHeaders;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

public class WenChangChainServiceImplTest extends ChainBaseTest {
    @Autowired
    @Qualifier("wenChangChainService")
    private ChainService wenChangChainService;

    @Autowired
    WenChangChainConfiguration wenChangChainConfiguration;

    @Before
    public void init() {
        when(slidingWindowRateLimiter.tryAcquire(anyString(), anyInt(), anyInt())).thenReturn(true);
    }

    @Test
    public void testGetConfig(){
        System.out.println(wenChangChainConfiguration.apiKey());
    }

    @Test
    public void createAddrTest() {
        ChainProcessRequest request = new ChainProcessRequest();
        request.setIdentifier(String.valueOf(new Date().getTime()));
        request.setUserId(String.valueOf(new Date().getTime()));
        ChainProcessResponse<ChainCreateData> res = wenChangChainService.createAddr(request);
        Assert.assertTrue(res.getSuccess());
        res = wenChangChainService.createAddr(request);
        Assert.assertTrue(res.getSuccess());

    }


    @Test
    public void chainTest() throws InterruptedException {
        ChainProcessRequest request = new ChainProcessRequest();
        request.setIdentifier(String.valueOf(new Date().getTime()));
        request.setClassId("id" + new Date().getTime());
        request.setClassName("name" + new Date().getTime());
        var res = wenChangChainService.chain(request);
        Assert.assertTrue(res.getSuccess());
        Thread.sleep(6000);
        //查询藏品
        ChainQueryRequest queryRequest = new ChainQueryRequest(request.getIdentifier(),String.valueOf(new Date().getTime()));
        var queryRes = wenChangChainService.queryChainResult(queryRequest);
        Assert.assertTrue(queryRes.getData().getTxHash() != null);
        System.out.println(request.getClassId());
    }

    @Test
    public void mintTest() {
        ChainProcessRequest request = new ChainProcessRequest();
        request.setIdentifier(String.valueOf(new Date().getTime()));
        request.setRecipient("iaa1mrkyyn4slya8x3rw7qez5ujdaemtxl6kyymqjf");
        request.setClassId("id1707124288146");
        request.setClassName("name1707124288146");
        request.setSerialNo("1");
        var res = wenChangChainService.mint(request);
        Assert.assertTrue(res.getSuccess());

    }

    @Test
    public void transferTest() throws InterruptedException {
        String identifier = String.valueOf(new Date().getTime());
        //创建一个藏品
        ChainProcessRequest request = new ChainProcessRequest();
        request.setIdentifier(identifier);
        request.setRecipient("iaa1mrkyyn4slya8x3rw7qez5ujdaemtxl6kyymqjf");
        request.setClassName("id1707124288146");
        request.setClassId("id1707124288146");
        request.setSerialNo(identifier);
        var res = wenChangChainService.mint(request);
        Assert.assertTrue(res.getSuccess());
        Thread.sleep(6000);
        //查询藏品
        ChainQueryRequest queryRequest = new ChainQueryRequest(identifier,identifier);
        var queryRes = wenChangChainService.queryChainResult(queryRequest);
        Assert.assertTrue(queryRes.getData().getNftId() != null);
        //交易藏品
        ChainProcessRequest transferReq = new ChainProcessRequest();
        transferReq.setIdentifier(String.valueOf(new Date().getTime()));
        transferReq.setRecipient(wenChangChainConfiguration.chainAddrSuper());
        transferReq.setNtfId(queryRes.getData().getNftId());
        transferReq.setClassId("id1707124288146");
        transferReq.setOwner("iaa1mrkyyn4slya8x3rw7qez5ujdaemtxl6kyymqjf");
        var transferRes = wenChangChainService.transfer(transferReq);
        Assert.assertTrue(transferRes.getSuccess());
        Thread.sleep(6000);
        queryRes = wenChangChainService.queryChainResult(queryRequest);
        Assert.assertTrue(queryRes.getData().getNftId() != null);

    }

    @Test
    public void destroyTest() throws InterruptedException {
        String identifier = String.valueOf(new Date().getTime());
        //创建一个藏品
        ChainProcessRequest request = new ChainProcessRequest();
        request.setIdentifier(identifier);
        request.setRecipient("iaa1mrkyyn4slya8x3rw7qez5ujdaemtxl6kyymqjf");
        request.setClassId("id1707124288146");
        var res = wenChangChainService.mint(request);
        Assert.assertTrue(res.getSuccess());
        Thread.sleep(6000);
        //查询藏品
        ChainQueryRequest queryRequest = new ChainQueryRequest(identifier,identifier);
        var queryRes = wenChangChainService.queryChainResult(queryRequest);
        Assert.assertTrue(queryRes.getData().getNftId() != null);
        //交易藏品
        ChainProcessRequest transferReq = new ChainProcessRequest();
        transferReq.setIdentifier(String.valueOf(new Date().getTime()));
        transferReq.setNtfId(queryRes.getData().getNftId());
        transferReq.setClassId("id1707124288146");
        transferReq.setOwner("iaa1mrkyyn4slya8x3rw7qez5ujdaemtxl6kyymqjf");
        var transferRes = wenChangChainService.destroy(transferReq);
        Assert.assertTrue(transferRes.getSuccess());
        Thread.sleep(6000);
        //查询藏品
        queryRes = wenChangChainService.queryChainResult(queryRequest);
        Assert.assertTrue(queryRes.getData().getNftId() != null);

    }


}
