package cn.hollis.nft.turbo.collection.domain.request;

import cn.hollis.nft.turbo.collection.domain.constant.HeldCollectionEventType;
import lombok.*;

/**
 * @author wswyb001
 * @date 2024/01/17
 */

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class HeldCollectionDestroyRequest extends BaseHeldCollectionRequest {

    /**
     * 操作人Id
     */
    private String operatorId;

    @Override
    public HeldCollectionEventType getEventType() {
        return HeldCollectionEventType.DESTROY;
    }
}
