package cn.time.nft.turbo.pay.infrastructure.channel.common.response;

import cn.time.nft.turbo.base.response.BaseResponse;
import lombok.Getter;
import lombok.Setter;

import java.io.InputStream;


@Setter
@Getter
public class BillDownloadChannelResponse extends BaseResponse {

    //账单
    protected InputStream file;


}
