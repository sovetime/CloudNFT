package cn.hollis.nft.turbo.collection.domain.request;

import cn.hollis.nft.turbo.collection.domain.constant.HeldCollectionEventType;
import lombok.*;

/**
 * @author Hollis
 */
@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class HeldCollectionActiveRequest extends BaseHeldCollectionRequest {

    /**
     * 'nftId'
     */
    private String nftId;

    /**
     * 'txHash'
     */
    private String txHash;

    @Override
    public HeldCollectionEventType getEventType() {
        return HeldCollectionEventType.ACTIVE;
    }
}
