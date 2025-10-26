package cn.hollis.nft.turbo.api.collection.model;

import cn.hollis.nft.turbo.api.collection.constant.CollectionRarity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


//藏品信息
@Getter
@Setter
@ToString
public class HeldCollectionVO implements Serializable {

    private static final long serialVersionUID = 1L;
    //id
    private String id;
    //藏品名称
    private String name;
    //藏品封面
    private String cover;
    // 购入价格
    private BigDecimal purchasePrice;
    //参考价格
    private BigDecimal referencePrice;
    //稀有度
    private CollectionRarity rarity;
    //藏品id
    private Long collectionId;
    //藏品编号
    private String serialNo;
    //nft唯一编号
    private String nftId;
    //上一个持有人id
    private String preId;
    //持有人id
    private String userId;
    //状态
    private String state;
    //交易hash
    private String txHash;
    //藏品持有时间
    private Date holdTime;
    //藏品同步时间
    private Date syncChainTime;
    //藏品销毁时间
    private Date deleteTime;
    //业务单号
    private String bizNo;
    //业务类型
    private String bizType;

}
