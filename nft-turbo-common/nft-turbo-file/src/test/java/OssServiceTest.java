import cn.hollis.nft.turbo.file.OssServiceImpl;
import cn.hollis.nft.turbo.file.config.OssConfiguration;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.ByteArrayInputStream;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {OssConfiguration.class})
@ActiveProfiles("test")
public class OssServiceTest {

    @Autowired
    private OssServiceImpl ossService;

    @Test
    @Ignore
    public void testUploadFile() {
        // 填写字符串。
        String content = "Hello OSS，你好世界";
        //https://nfturbo-file.oss-cn-hangzhou.aliyuncs.com/img/test.txt
        boolean res = ossService.upload("img/123.txt", new ByteArrayInputStream(content.getBytes()));
        Assert.assertTrue(res);
    }
}
