package cn.hollis.nft.turbo.api.box.model;

import cn.hollis.nft.turbo.api.box.constant.BlindBoxItemStateEnum;
import cn.hollis.nft.turbo.api.collection.constant.CollectionRarity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;


//盲盒条目信息
@Getter
@Setter
@ToString
public class BlindBoxItemVO implements Serializable {
    //主键ID
    private Long id;
    //盲盒id
    private Long blindBoxId;
    //盲盒名称
    private String name;
    //盲盒封面
    private String cover;
    //藏品Id
    private Long collectionId;
    //藏品名称
    private String collectionName;
    //藏品封面
    private String collectionCover;
    //藏品详情
    private String collectionDetail;
    //藏品序列号
    private String collectionSerialNo;
    //状态
    private BlindBoxItemStateEnum state;
    //持有人id
    private String userId;
    //购入价格
    private BigDecimal purchasePrice;
    //订单号
    private String orderId;
    //参考价格
    private BigDecimal referencePrice;
    //稀有度
    private CollectionRarity rarity;
}
