package cn.hollis.nft.turbo.goods.entity;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
public class HotGoods implements Serializable {

    private static final long serialVersionUID = 1L;

    //商品id
    private String goodsId;

    //商品类型
    private String goodsType;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        HotGoods hotGoods = (HotGoods) o;
        return Objects.equals(goodsId, hotGoods.goodsId) && Objects.equals(goodsType, hotGoods.goodsType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(goodsId, goodsType);
    }
}
