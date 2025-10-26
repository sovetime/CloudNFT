package cn.hollis.nft.turbo.api.collection.request;

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
public class CollectionModifyInventoryRequest extends BaseCollectionRequest {

    //藏品数量
    private Integer quantity;

    @Override
    public GoodsEvent getEventType() {
        return GoodsEvent.MODIFY_INVENTORY;
    }
}
