package cn.hollis.nft.turbo.api.chain.request;

import cn.hollis.nft.turbo.base.request.BaseRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


// 链处理参数
@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ChainProcessRequest extends BaseRequest {

    //幂等号
    private String identifier;
    //藏品类别ID
    private String classId;
    //藏品类别名称
    private String className;
    // 藏品序列号
    private String serialNo;
    //接收者地址
    private String recipient;
    //持有者地址
    private String owner;
    //ntf唯一id
    private String ntfId;
    //用户id
    private String userId;
    // 业务id
    private String bizId;
    //业务类型
    private String bizType;


}
