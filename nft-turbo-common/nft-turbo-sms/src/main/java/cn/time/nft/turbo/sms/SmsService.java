package cn.time.nft.turbo.sms;

import cn.time.nft.turbo.sms.response.SmsSendResponse;


//短信服务
public interface SmsService {

    //发送短信
    public SmsSendResponse sendMsg(String phoneNumber, String code);
}
