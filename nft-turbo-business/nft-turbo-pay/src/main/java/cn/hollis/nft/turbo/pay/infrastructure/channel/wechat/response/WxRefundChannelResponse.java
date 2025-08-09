package cn.hollis.nft.turbo.pay.infrastructure.channel.wechat.response;

import cn.hollis.nft.turbo.pay.infrastructure.channel.common.response.RefundChannelResponse;
import lombok.Getter;
import lombok.Setter;

/**
 * @author wswyb001
 */
@Setter
@Getter
public class WxRefundChannelResponse extends RefundChannelResponse {

    private WxPayRefundBody wxPayRefundBody;
}
