package cn.hollis.nft.turbo.collection.domain.entity;

import cn.hollis.nft.turbo.api.goods.constant.GoodsEvent;
import cn.hollis.nft.turbo.api.collection.constant.CollectionStateEnum;
import cn.hollis.nft.turbo.datasource.domain.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 藏品流水信息
 * </p>
 *
 * @author wswyb001
 * @since 2024-01-19
 */
@Getter
@Setter
public class CollectionStream extends BaseEntity {

    /**
     * 流水类型
     */
    private GoodsEvent streamType;

    /**
     * 藏品id
     */
    private Long collectionId;

    /**
     * '藏品名称'
     */
    private String name;

    /**
     * '藏品封面'
     */
    private String cover;

    /**
     * '藏品类目id'
     */
    private String classId;

    /**
     * '价格'
     */
    private BigDecimal price;

    /**
     * '藏品数量'
     */
    private Integer quantity;

    /**
     * '藏品详情'
     */
    private String detail;

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
     * '藏品创建时间'
     */
    private Date createTime;

    /**
     * '藏品发售时间'
     */
    private Date saleTime;

    /**
     * '藏品上链时间'
     */
    private Date syncChainTime;

    /**
     * '幂等号'
     */
    private String identifier;

    @SuppressWarnings("deprecation")
    public CollectionStream(Collection collection, String identifier, GoodsEvent streamType) {
        this.collectionId = collection.getId();
        this.name = collection.getName();
        this.cover = collection.getCover();
        this.classId = collection.getClassId();
        this.price = collection.getPrice();
        this.quantity = collection.getQuantity();
        this.detail = collection.getDetail();
        this.saleableInventory = collection.getSaleableInventory();
        this.occupiedInventory = collection.getOccupiedInventory();
        this.state = collection.getState();
        this.createTime = collection.getCreateTime();
        this.streamType = streamType;
        this.saleTime = collection.getSaleTime();
        this.syncChainTime = collection.getSyncChainTime();
        this.identifier = identifier;
        super.setLockVersion(collection.getLockVersion());
        super.setDeleted(collection.getDeleted());

    }
}
