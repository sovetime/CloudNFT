package cn.hollis.nft.turbo.api.collection.request;

import cn.hollis.nft.turbo.api.goods.constant.GoodsEvent;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Setter
@Getter
@ToString
@NoArgsConstructor
public class CollectionRemoveRequest extends BaseCollectionRequest {

    @Override
    public GoodsEvent getEventType() {
        return GoodsEvent.REMOVE;
    }
}
