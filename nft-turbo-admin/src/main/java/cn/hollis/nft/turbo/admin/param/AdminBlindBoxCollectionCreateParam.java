package cn.hollis.nft.turbo.admin.param;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;


// 盲盒藏品参数
@Setter
@Getter
public class AdminBlindBoxCollectionCreateParam {

    //藏品名称
    private String collectionName;

    //藏品封面
    private String collectionCover;

    //藏品详情
    private String collectionDetail;

    //藏品数量
    private Long quantity;

    //参考价格
    private BigDecimal referencePrice;

    //稀有度
    private String rarity;
}
