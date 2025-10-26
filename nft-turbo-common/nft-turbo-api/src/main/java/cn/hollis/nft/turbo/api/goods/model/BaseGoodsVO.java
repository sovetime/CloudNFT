package cn.hollis.nft.turbo.api.goods.model;

import cn.hollis.nft.turbo.api.goods.constant.GoodsState;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Setter
@Getter
public abstract class BaseGoodsVO implements Serializable {

    //状态
    private GoodsState state;

    //商品名称
    public abstract String getGoodsName();

    //商品图片
    public abstract String getGoodsPicUrl();

    //卖家id
    public abstract String getSellerId();

    //版本
    public abstract Integer getVersion();

    //是否可用
    public Boolean available() {
        return this.state == GoodsState.SELLING;
    }

    //价格
    public abstract BigDecimal getPrice();

    //是否预约商品
    public abstract Boolean canBook();

    //当前是否可预约
    public abstract Boolean canBookNow();

    //是否已预约过
    public abstract Boolean hasBooked();

    //商品预约开始时间
    public abstract Date getBookStartTime();

    //商品预约结束时间
    public abstract Date getBookEndTime();

}
