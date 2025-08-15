package cn.hollis.nft.turbo.pay.infrastructure.channel.common.request;

import cn.hollis.nft.turbo.base.request.BaseRequest;
import lombok.*;


//账单下载参数
@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class DownloadBillChannelRequest extends BaseRequest {

    //账单token
    private String token;
}
