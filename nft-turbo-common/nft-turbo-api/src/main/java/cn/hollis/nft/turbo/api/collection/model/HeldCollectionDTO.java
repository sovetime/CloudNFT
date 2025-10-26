package cn.hollis.nft.turbo.api.collection.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;


// 持有藏品 DTO
@Getter
@Setter
@ToString
public class HeldCollectionDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    //主键 Id
    private Long id;
    //藏品id
    private Long collectionId;
    //持有人id
    private String userId;
    //状态
    private String state;
    //业务单号
    private String bizNo;
    // 业务类型
    private String bizType;

}
