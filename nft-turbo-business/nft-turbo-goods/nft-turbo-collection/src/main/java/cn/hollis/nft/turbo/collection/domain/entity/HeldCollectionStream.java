package cn.hollis.nft.turbo.collection.domain.entity;

import cn.hollis.nft.turbo.collection.domain.constant.HeldCollectionEventType;
import cn.hollis.nft.turbo.datasource.domain.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import static cn.hollis.nft.turbo.api.user.constant.UserType.PLATFORM;

/**
 * 持有藏品流水表实体
 *
 * @author Hollis
 */
@Setter
@Getter
public class HeldCollectionStream extends BaseEntity {

    /**
     * 持有藏品ID
     */
    private Long heldCollectionId;

    /**
     * 流水类型
     */
    private String streamType;

    /**
     * 操作人
     */
    private String operator;

    /**
     * 幂等号
     */
    private String identifier;

    public HeldCollectionStream generateForCreate(Long heldCollectionId, String identifier) {
        this.heldCollectionId = heldCollectionId;
        this.streamType = HeldCollectionEventType.CREATE.name();
        this.operator = PLATFORM.name();
        this.identifier = identifier;
        return this;
    }

    public HeldCollectionStream generateForActive(Long heldCollectionId, String identifier) {
        this.heldCollectionId = heldCollectionId;
        this.streamType = HeldCollectionEventType.ACTIVE.name();
        this.operator = PLATFORM.name();
        this.identifier = identifier;
        return this;
    }

    public HeldCollectionStream generateForDestroy(Long heldCollectionId, String identifier, String userId) {
        this.heldCollectionId = heldCollectionId;
        this.streamType = HeldCollectionEventType.DESTROY.name();
        this.operator = userId;
        this.identifier = identifier;
        return this;
    }

    public HeldCollectionStream generateForTransferOut(Long heldCollectionId, String identifier, String userId) {
        this.heldCollectionId = heldCollectionId;
        this.streamType = HeldCollectionEventType.TRANSFER.name() + "_OUT";
        this.operator = userId;
        this.identifier = identifier;
        return this;
    }

    public HeldCollectionStream generateForTransferIn(Long heldCollectionId, String identifier, String userId) {
        this.heldCollectionId = heldCollectionId;
        this.streamType = HeldCollectionEventType.TRANSFER.name() + "_IN";
        this.operator = userId;
        this.identifier = identifier;
        return this;
    }
}