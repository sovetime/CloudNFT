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
public class CollectionDestroyRequest extends BaseCollectionRequest {

    //持有藏品id
    private Long heldCollectionId;

    @Override
    public GoodsEvent getEventType() {
        return GoodsEvent.DESTROY;
    }
}
