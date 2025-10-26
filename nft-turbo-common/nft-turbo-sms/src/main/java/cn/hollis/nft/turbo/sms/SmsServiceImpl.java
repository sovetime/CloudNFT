package cn.hollis.nft.turbo.sms;

import cn.hollis.nft.turbo.base.utils.RestClientUtils;
import cn.hollis.nft.turbo.lock.DistributeLock;
import cn.hollis.nft.turbo.sms.response.SmsSendResponse;
import com.google.common.collect.Maps;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;

import java.util.Map;

import static cn.hollis.nft.turbo.base.response.ResponseCode.SYSTEM_ERROR;


//短信服务
@Slf4j
@Setter
public class SmsServiceImpl implements SmsService{

    private String host;

    private String path;

    private String appcode;

    private String smsSignId;

    private String templateId;

    @DistributeLock(scene = "SEND_SMS", keyExpression = "#phoneNumber")
    @Override
    public SmsSendResponse sendMsg(String phoneNumber, String code) {

        SmsSendResponse smsSendResponse = new SmsSendResponse();

        String method = "POST";
        Map<String, String> headers = Maps.newHashMapWithExpectedSize(1);
        //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
        headers.put("Authorization", "APPCODE " + appcode);
        Map<String, String> querys = Maps.newHashMapWithExpectedSize(4);
        querys.put("mobile", phoneNumber);
        querys.put("param", "**code**:" + code + ",**minute**:5");

        //smsSignId（短信前缀）和templateId（短信模板），可登录国阳云控制台自助申请。参考文档：http://help.guoyangyun.com/Problem/Qm.html

        querys.put("smsSignId", smsSignId);
        querys.put("templateId", templateId);
        Map<String, String> bodys = Maps.newHashMapWithExpectedSize(2);

        try {
            ResponseEntity response = RestClientUtils.doPost(host, path, headers, querys, bodys);
            if (response.getStatusCode().is2xxSuccessful()) {
                smsSendResponse.setSuccess(true);
            }
        } catch (Exception e) {
            log.error("sendMsg error", e);
            smsSendResponse.setSuccess(false);
            smsSendResponse.setResponseCode(SYSTEM_ERROR.name());
            smsSendResponse.setResponseMessage(StringUtils.substring(e.toString(), 0, 1000));
        }
        return smsSendResponse;
    }
}
