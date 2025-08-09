package cn.hollis.nft.turbo.collection.domain.entity;

import cn.hollis.nft.turbo.api.collection.constant.CollectionRarity;
import cn.hollis.nft.turbo.api.collection.constant.GoodsSaleBizType;
import cn.hollis.nft.turbo.api.collection.constant.HeldCollectionState;
import cn.hollis.nft.turbo.api.common.constant.BusinessCode;
import cn.hollis.nft.turbo.collection.domain.request.HeldCollectionCreateRequest;
import cn.hollis.nft.turbo.datasource.domain.entity.BaseEntity;
import cn.hutool.core.util.IdUtil;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 藏品信息
 * </p>
 *
 * @author wswyb001
 * @since 2024-01-19
 */
@Getter
@Setter
public class HeldCollection extends BaseEntity {

    /**
     * 藏品名称
     */
    private String name;

    /**
     * 藏品封面
     */
    private String cover;

    /**
     * 购入价格
     */
    private BigDecimal purchasePrice;

    /**
     * 参考价格
     */
    private BigDecimal referencePrice;

    /**
     * 稀有度
     */
    private CollectionRarity rarity;

    /**
     * '藏品id'
     */
    private Long collectionId;

    /**
     * '藏品编号'
     */
    private String serialNo;

    /**
     * 'nft唯一编号'
     */
    private String nftId;

    /**
     * '上一个持有人id'
     */
    private String preId;

    /**
     * '持有人id'
     */
    private String userId;

    /**
     * '状态'
     */
    private HeldCollectionState state;

    /**
     * '交易hash'
     */
    private String txHash;

    /**
     * '藏品持有时间'
     */
    private Date holdTime;

    /**
     * '藏品同步时间'
     */
    private Date syncChainTime;

    /**
     * '藏品销毁时间'
     */
    private Date deleteTime;

    /**
     * '业务类型'
     */
    private GoodsSaleBizType bizType;

    /**
     * '业务编号'
     */
    private String bizNo;

    public HeldCollection init(HeldCollectionCreateRequest request,String serialNo) {
        //ShardingJDBC 不支持批量插入时获取并返回主键 ID，详见：https://github.com/bigcoder84/study-notes/blob/master/%E5%9F%BA%E7%A1%80%E7%AC%94%E8%AE%B0/%E9%A1%B9%E7%9B%AE%E8%B8%A9%E5%9D%91/subfile/_6MyBatisPlus+ShardingJDBC%E6%89%B9%E9%87%8F%E6%8F%92%E5%85%A5%E4%B8%8D%E8%BF%94%E5%9B%9EID.md
        //为了解决批量生成持有藏品的场景（空投）中，batchCreate 之后无法立刻拿到主键 ID的问你题，这里单独使用雪花算法生成id
        super.setId(IdUtil.getSnowflake(BusinessCode.HELD_COLLECTION.code()).nextId());
        this.collectionId = request.getGoodsId();
        this.serialNo = serialNo;
        this.userId = request.getUserId();
        this.state = HeldCollectionState.INIT;
        this.holdTime = new Date();
        this.bizNo = request.getBizNo();
        this.bizType = GoodsSaleBizType.valueOf(request.getBizType());
        this.name = request.getName();
        this.cover = request.getCover();
        this.purchasePrice = request.getPurchasePrice();
        this.referencePrice = request.getReferencePrice();
        this.rarity = request.getRarity();
        return this;
    }

    public HeldCollection actived(String nftId, String txHash) {
        this.txHash = txHash;
        this.nftId = nftId;
        this.syncChainTime = new Date();
        this.state = HeldCollectionState.ACTIVED;
        return this;
    }

    public HeldCollection inActived() {
        this.state = HeldCollectionState.INACTIVED;
        return this;
    }

    public HeldCollection transfer(Long collectionId, String serialNo, String preId, String userId, String nftId) {
        this.collectionId = collectionId;
        this.serialNo = serialNo;
        this.preId = preId;
        this.userId = userId;
        this.nftId = nftId;
        this.state = HeldCollectionState.INIT;
        this.holdTime = new Date();
        return this;
    }

    public HeldCollection transfer(HeldCollection heldCollection, String recipientUserId) {
        this.collectionId = heldCollection.getCollectionId();
        this.serialNo = heldCollection.getSerialNo();
        this.preId = heldCollection.getUserId();
        this.userId = recipientUserId;
        this.nftId = heldCollection.getNftId();
        this.state = HeldCollectionState.INIT;
        this.holdTime = new Date();
        this.name = heldCollection.getName();
        this.cover = heldCollection.getCover();
        this.bizType = GoodsSaleBizType.TRANSFER;
        this.bizNo = heldCollection.getId().toString();
        this.purchasePrice = heldCollection.getPurchasePrice();
        this.referencePrice = heldCollection.getReferencePrice();
        this.rarity = heldCollection.getRarity();
        return this;
    }

    public HeldCollection destroying() {
        this.state = HeldCollectionState.DESTROYING;
        this.deleteTime = new Date();
        return this;
    }

    public HeldCollection destroyed() {
        this.state = HeldCollectionState.DESTROYED;
        this.deleteTime = new Date();
        return this;
    }

}
