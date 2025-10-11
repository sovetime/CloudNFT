package cn.time.nft.turbo.pay.infrastructure.channel.wechat.response;

import cn.time.nft.turbo.pay.infrastructure.channel.common.response.RefundChannelResponse;
import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
public class WxRefundChannelResponse extends RefundChannelResponse {

    private WxPayRefundBody wxPayRefundBody;
}
