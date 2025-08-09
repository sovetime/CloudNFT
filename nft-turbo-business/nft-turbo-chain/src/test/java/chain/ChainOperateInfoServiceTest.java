package chain;

import cn.hollis.nft.turbo.chain.domain.service.ChainOperateInfoService;
import cn.hollis.nft.turbo.chain.infrastructure.mapper.ChainOperateInfoMapper;
import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class ChainOperateInfoServiceTest extends ChainBaseTest {
    @Autowired
    private ChainOperateInfoService chainOperateInfoService;
    @Autowired
    private ChainOperateInfoMapper mapper;

    @Test
    public void serviceTest(){
        var id=chainOperateInfoService.insertInfo("TEST","12345","TYPE", "chain", "chain","35465");
        var info=chainOperateInfoService.queryByOutBizId("12345","TYPE","35465");
        Assert.assertTrue(StringUtils.equals("35465",info.getOutBizId()));
        chainOperateInfoService.delete(id);
        var retList=mapper.scanAll();
        Assert.assertTrue(retList.get(0).getDeleted()==1);

    }

}
