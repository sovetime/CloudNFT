package cn.hollis.nft.turbo.api.goods.model;

import cn.hollis.nft.turbo.api.goods.constant.GoodsType;
import cn.hollis.nft.turbo.api.user.constant.UserType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;


@Setter
@Getter
@ToString
public class GoodsBookVO implements Serializable {

    //主键id
    private String id;

    //商品Id
    private String goodsId;

    //商品类型
    private GoodsType goodsType;

    //买家id
    private String buyerId;

    //买家id类型
    private UserType buyerType;

    //卖家id
    private String sellerId;

    //卖家id类型
    private UserType sellerType;

    //幂等号
    private String identifier;

    //预约成功时间
    private Date bookSucceedTime;

}
