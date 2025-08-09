package cn.hollis.nft.turbo.api.goods.model;

import cn.hollis.nft.turbo.api.box.constant.BlindBoxStateEnum;
import cn.hollis.nft.turbo.api.goods.constant.GoodsEvent;
import cn.hollis.nft.turbo.api.goods.constant.GoodsType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;


// 商品流水
@Getter
@Setter
@ToString
public class GoodsStreamVO implements Serializable {

    //流水类型
    private GoodsEvent streamType;

    //幂等号
    private String identifier;

    //变更数量
    private Integer changedQuantity;

    //商品id
    private Long goodsId;

    // 商品类型
    private GoodsType goodsType;

    //价格
    private BigDecimal price;

    //数量
    private Long quantity;

    //可售库存
    private Long saleableInventory;

    //冻结库存
    private Long frozenInventory;

    //状态
    private BlindBoxStateEnum state;

    //扩展信息
    private String extendInfo;
}
