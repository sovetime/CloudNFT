package cn.hollis.nft.turbo.collection.domain.entity;

import cn.hollis.nft.turbo.datasource.domain.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 藏品快照信息
 * </p>
 *
 * @author Hollis
 * @since 2024-07-17
 */
@Getter
@Setter
public class CollectionSnapshot extends BaseEntity {

    /**
     * 藏品ID
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
     * '藏品详情'
     */
    private String detail;

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
     * '藏品创建者id'
     */
    private String creatorId;

    /**
     * 版本
     */
    private Integer version;
}
