package cn.hollis.nft.turbo.api.collection.request;

import java.math.BigDecimal;

import cn.hollis.nft.turbo.api.goods.constant.GoodsEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CollectionModifyPriceRequest extends BaseCollectionRequest {

    private BigDecimal price;

    @Override
    public GoodsEvent getEventType() {
        return GoodsEvent.MODIFY_PRICE;
    }
}
