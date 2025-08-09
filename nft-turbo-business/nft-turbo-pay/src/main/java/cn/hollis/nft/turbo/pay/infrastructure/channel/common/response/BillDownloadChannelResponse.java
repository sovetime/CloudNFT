package cn.hollis.nft.turbo.pay.infrastructure.channel.common.response;

import cn.hollis.nft.turbo.base.response.BaseResponse;
import lombok.Getter;
import lombok.Setter;

import java.io.InputStream;

/**
 * @author Hollis
 */
@Setter
@Getter
public class BillDownloadChannelResponse extends BaseResponse {
    /**
     * 账单
     */
    protected InputStream file;


}
