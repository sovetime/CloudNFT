package cn.hollis.nft.turbo.box.domain.entity;

import cn.hollis.nft.turbo.api.box.constant.BlindBoxStateEnum;
import cn.hollis.nft.turbo.api.goods.constant.GoodsEvent;
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
 * @author wswyb001
 * @since 2024-01-19
 */
@Getter
@Setter
@NoArgsConstructor
public class BlindBoxInventoryStream extends BaseEntity {

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
     * 盲盒id
     */
    private Long blindBoxId;

    /**
     * '价格'
     */
    private BigDecimal price;

    /**
     * '盲盒数量'
     */
    private Long quantity;

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
    private BlindBoxStateEnum state;

    /**
     * 扩展信息
     */
    private String extendInfo;

    @SuppressWarnings("deprecation")
    public BlindBoxInventoryStream(BlindBox blindBox, String identifier, GoodsEvent streamType, Integer quantity) {
        this.blindBoxId = blindBox.getId();
        this.price = blindBox.getPrice();
        this.quantity = blindBox.getQuantity();
        this.saleableInventory = blindBox.getSaleableInventory();
        this.occupiedInventory = blindBox.getOccupiedInventory();
        this.frozenInventory = blindBox.getFrozenInventory();
        this.state = blindBox.getState();
        this.streamType = streamType;
        this.identifier = identifier;
        this.changedQuantity = quantity;
        super.setLockVersion(blindBox.getLockVersion());
        super.setDeleted(blindBox.getDeleted());
    }

    public void addBlindBoxItemId(Long blindBoxItemId) {
        Map<String, Serializable> extendMap;
        if (this.extendInfo == null) {
            extendMap = Maps.newHashMapWithExpectedSize(1);
        } else {

            extendMap = JSON.parseObject(this.extendInfo, HashMap.class);
        }
        extendMap.put("blindBoxItemId", blindBoxItemId);
        this.extendInfo = JSON.toJSONString(extendMap);
    }

    @JSONField(serialize = false)
    public Long getBlindBoxItemId() {
        if (this.extendInfo != null) {
            return ((Integer)JSON.parseObject(this.extendInfo, HashMap.class).get("blindBoxItemId")).longValue();
        }

        return null;
    }
}
