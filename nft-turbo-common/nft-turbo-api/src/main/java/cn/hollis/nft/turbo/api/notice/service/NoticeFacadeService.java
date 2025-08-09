package cn.hollis.nft.turbo.api.notice.service;


import cn.hollis.nft.turbo.api.notice.response.NoticeResponse;


public interface NoticeFacadeService {

    //生成并发送短信验证码
    public NoticeResponse generateAndSendSmsCaptcha(String telephone);
}
