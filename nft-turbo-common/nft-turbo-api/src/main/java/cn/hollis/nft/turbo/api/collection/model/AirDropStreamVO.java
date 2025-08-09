package cn.hollis.nft.turbo.api.collection.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;


//空投记录信息
@Getter
@Setter
@ToString
public class AirDropStreamVO implements Serializable {

    private static final long serialVersionUID = 1L;

    //藏品id
    private Long collectionId;

    //接收用户ID
    private String recipientUserId;

    //空投数量
    private Integer quantity;

    //空投时间
    private Date airDropTime;
}
