package cn.hollis.nft.turbo.pay.infrastructure.channel.common.response;

import cn.hollis.nft.turbo.base.response.BaseResponse;
import cn.hollis.nft.turbo.pay.infrastructure.channel.wechat.entity.WxPayNotifyEntity;
import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
public class PayResultQueryResponse extends BaseResponse {
    protected WxPayNotifyEntity wxPayNotifyEntity;
}
