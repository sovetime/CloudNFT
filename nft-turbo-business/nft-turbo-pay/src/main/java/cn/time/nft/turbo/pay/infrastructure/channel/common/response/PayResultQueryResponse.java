package cn.time.nft.turbo.pay.infrastructure.channel.common.response;

import cn.time.nft.turbo.base.response.BaseResponse;
import cn.time.nft.turbo.pay.infrastructure.channel.wechat.entity.WxPayNotifyEntity;
import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
public class PayResultQueryResponse extends BaseResponse {
    protected WxPayNotifyEntity wxPayNotifyEntity;
}
