package cn.hollis.nft.turbo.box.domain.entity;

import cn.hollis.nft.turbo.api.box.constant.BlindBoxItemStateEnum;
import cn.hollis.nft.turbo.api.box.request.BlindBoxItemCreateRequest;
import cn.hollis.nft.turbo.api.collection.constant.CollectionRarity;
import cn.hollis.nft.turbo.api.goods.request.GoodsConfirmSaleRequest;
import cn.hollis.nft.turbo.box.domain.request.BlindBoxAssignRequest;
import cn.hollis.nft.turbo.datasource.domain.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 盲盒条目信息
 * </p>
 *
 * @author wswyb001
 * @since 2024-10-05
 */
@Getter
@Setter
public class BlindBoxItem extends BaseEntity {
    /**
     * '盲盒id'
     */
    private Long blindBoxId;
    /**
     * '盲盒名称'
     */
    private String name;

    /**
     * '盲盒封面'
     */
    private String cover;

    /**
     * '藏品名称'
     */
    private String collectionName;

    /**
     * '藏品封面'
     */
    private String collectionCover;

    /**
     * '藏品详情'
     */
    private String collectionDetail;

    /**
     * 藏品序列号
     * @Deprecated
     */
    private String collectionSerialNo;

    /**
     * '状态'
     */
    private BlindBoxItemStateEnum state;

    /**
     * 开盒成功时间
     */
    private Date openedTime;

    /**
     * '分配时间'
     */
    private Date assignTime;

    /**
     * '持有人id'
     */
    private String userId;

    /**
     * '购入价格'
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
     * 订单号
     */
    private String orderId;

    @Deprecated
    public BlindBoxItem assign(GoodsConfirmSaleRequest request, BlindBox blindBox) {
        this.setState(BlindBoxItemStateEnum.ASSIGNED);
        this.setUserId(request.userId());
        this.setOrderId(request.bizNo());
        this.setAssignTime(new Date());
        return this;
    }

    public BlindBoxItem assign(BlindBoxAssignRequest request) {
        this.setState(BlindBoxItemStateEnum.ASSIGNED);
        this.setUserId(request.getUserId());
        this.setOrderId(request.getOrderId());
        this.setAssignTime(new Date());
        return this;
    }

    public BlindBoxItem opening() {
        this.setState(BlindBoxItemStateEnum.OPENING);
        return this;
    }

    public BlindBoxItem openSuccess() {
        this.setState(BlindBoxItemStateEnum.SUCCEED);
        this.setOpenedTime(new Date());
        return this;
    }

    public static BlindBoxItem create(BlindBoxItemCreateRequest request, BlindBox blindBox) {
        BlindBoxItem blindBoxItem = new BlindBoxItem();
        blindBoxItem.setBlindBoxId(blindBox.getId());
        blindBoxItem.setCover(blindBox.getCover());
        blindBoxItem.setName(blindBox.getName());
        blindBoxItem.setPurchasePrice(blindBox.getPrice());
        blindBoxItem.setState(BlindBoxItemStateEnum.INIT);
        blindBoxItem.setCollectionName(request.getCollectionName());
        blindBoxItem.setCollectionDetail(request.getCollectionDetail());
        blindBoxItem.setCollectionCover(request.getCollectionCover());
        blindBoxItem.setReferencePrice(request.getReferencePrice());
        blindBoxItem.setRarity(request.getRarity());
        blindBoxItem.setLockVersion(1);
        return blindBoxItem;
    }
}
