package cn.hollis.nft.turbo.collection.domain.entity;

import cn.hollis.nft.turbo.api.goods.constant.GoodsEvent;
import cn.hollis.nft.turbo.api.collection.constant.CollectionStateEnum;
import cn.hollis.nft.turbo.datasource.domain.entity.BaseEntity;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson2.annotation.JSONField;
import com.google.common.collect.Maps;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 藏品库存流水信息
 * </p>
 *
 * @author Hollis
 * @since 2024-01-19
 */
@Getter
@Setter
@NoArgsConstructor
public class CollectionInventoryStream extends BaseEntity {

    /**
     * 流水类型
     */
    private GoodsEvent streamType;

    /**
     * '幂等号'
     */
    private String identifier;

    /**
     * '变更数量'
     */
    private Integer changedQuantity;

    /**
     * 藏品id
     */
    private Long collectionId;

    /**
     * '价格'
     */
    private BigDecimal price;

    /**
     * '藏品数量'
     */
    private Integer quantity;

    /**
     * '可售库存'
     */
    private Long saleableInventory;

    /**
     * '已占库存'
     * @deprecated 这个字段不再使用，详见 CollecitonSerivce.confirmSale
     */
    @Deprecated
    private Long occupiedInventory;

    /**
     * '冻结库存'
     */
    private Long frozenInventory;

    /**
     * '状态'
     */
    private CollectionStateEnum state;

    /**
     * 扩展信息
     */
    private String extendInfo;

    @SuppressWarnings("AliDeprecation")
    public CollectionInventoryStream(Collection collection, String identifier, GoodsEvent streamType, Integer quantity) {
        this.collectionId = collection.getId();
        this.price = collection.getPrice();
        this.quantity = collection.getQuantity();
        this.saleableInventory = collection.getSaleableInventory();
        ///  被废弃字段： this.occupiedInventory = collection.getOccupiedInventory();
        this.frozenInventory = collection.getFrozenInventory();
        this.state = collection.getState();
        this.streamType = streamType;
        this.identifier = identifier;
        this.changedQuantity = quantity;
        super.setLockVersion(collection.getLockVersion());
        super.setDeleted(collection.getDeleted());
    }

    public void addHeldCollectionId(Long heldCollectionId) {
        Map<String, Serializable> extendMap;
        if (this.extendInfo == null) {
            extendMap = Maps.newHashMapWithExpectedSize(1);
        } else {

            extendMap = JSON.parseObject(this.extendInfo, HashMap.class);
        }
        extendMap.put("heldCollectionId", heldCollectionId);
        this.extendInfo = JSON.toJSONString(extendMap);
    }

    @JSONField(serialize = false)
    public Long getHeldCollectionId() {
        if (this.extendInfo != null) {
            return ((Integer)JSON.parseObject(this.extendInfo, HashMap.class).get("heldCollectionId")).longValue();
        }

        return null;
    }
}
