package cn.hollis.nft.turbo.collection.domain.request;

import cn.hollis.nft.turbo.base.request.BaseRequest;
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
public abstract class BaseHeldCollectionRequest extends BaseRequest {
    /**
     * 幂等号
     */
    private String identifier;

    /**
     * '持有藏品id'
     */
    private String heldCollectionId;

    /**
     * 事件类型
     *
     * @return
     */
    public abstract HeldCollectionEventType getEventType();
}
