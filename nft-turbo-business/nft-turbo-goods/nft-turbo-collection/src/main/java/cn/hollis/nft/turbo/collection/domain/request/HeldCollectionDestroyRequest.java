package cn.hollis.nft.turbo.collection.domain.request;

import cn.hollis.nft.turbo.collection.domain.constant.HeldCollectionEventType;
import lombok.*;


@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class HeldCollectionDestroyRequest extends BaseHeldCollectionRequest {

    //操作人Id
    private String operatorId;

    @Override
    public HeldCollectionEventType getEventType() {
        return HeldCollectionEventType.DESTROY;
    }
}
