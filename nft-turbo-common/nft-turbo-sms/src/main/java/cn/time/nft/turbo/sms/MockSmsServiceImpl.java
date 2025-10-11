package cn.time.nft.turbo.sms;

import cn.time.nft.turbo.lock.DistributeLock;
import cn.time.nft.turbo.sms.response.SmsSendResponse;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;


//Mock短信服务
@Slf4j
@Setter
public class MockSmsServiceImpl implements SmsService {

    @DistributeLock(scene = "SEND_SMS", keyExpression = "#phoneNumber")
    @Override
    public SmsSendResponse sendMsg(String phoneNumber, String code) {
        SmsSendResponse smsSendResponse = new SmsSendResponse();
        smsSendResponse.setSuccess(true);
        return smsSendResponse;
    }
}
