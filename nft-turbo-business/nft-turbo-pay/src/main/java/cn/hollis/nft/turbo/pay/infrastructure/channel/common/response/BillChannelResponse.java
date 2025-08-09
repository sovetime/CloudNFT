package cn.hollis.nft.turbo.pay.infrastructure.channel.common.response;

import cn.hollis.nft.turbo.base.response.BaseResponse;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Hollis
 */
@Setter
@Getter
public class BillChannelResponse extends BaseResponse {
    /**
     * 下载地址
     * 供下一步请求账单文件的下载地址，该地址5min内有效
     */
    protected String downloadUrl;

    /**
     * 哈希类型
     * 固定为SHA1
     */
    protected String hashType;

    /**
     * 哈希值
     * 账单文件的SHA1摘要值，用于商户侧校验文件的一致性
     */
    protected String hashValue;

}
