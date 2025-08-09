package cn.hollis.nft.turbo.api.chain.request;

import cn.hollis.nft.turbo.base.request.BaseRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


//链处理参数
@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ChainQueryRequest extends BaseRequest {

    // 操作id
    private String operationId;

    // 操作信息的主键 ID
    private String operationInfoId;

}
