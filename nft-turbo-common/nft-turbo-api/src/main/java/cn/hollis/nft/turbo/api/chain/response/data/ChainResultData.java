package cn.hollis.nft.turbo.api.chain.response.data;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
public class ChainResultData implements Serializable {

    private static final long serialVersionUID = 1L;
    //ntf唯一编号
    private String nftId;
    //交易哈希
    private String txHash;
    //状态
    private String state;
    //藏品编号
    private String serialNo;


}
