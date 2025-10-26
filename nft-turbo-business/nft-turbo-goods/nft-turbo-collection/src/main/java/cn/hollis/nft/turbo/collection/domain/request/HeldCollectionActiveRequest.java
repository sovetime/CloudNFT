package cn.hollis.nft.turbo.collection.domain.request;

import cn.hollis.nft.turbo.collection.domain.constant.HeldCollectionEventType;
import lombok.*;


@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class HeldCollectionActiveRequest extends BaseHeldCollectionRequest {

    private String nftId;

    private String txHash;

    @Override
    public HeldCollectionEventType getEventType() {
        return HeldCollectionEventType.ACTIVE;
    }
}
