package cn.hollis.nft.turbo.collection.domain.entity.convertor;

import cn.hollis.nft.turbo.api.collection.constant.CollectionStateEnum;
import cn.hollis.nft.turbo.api.collection.model.CollectionVO;
import cn.hollis.nft.turbo.api.collection.request.CollectionCreateRequest;
import cn.hollis.nft.turbo.api.goods.constant.GoodsState;
import cn.hollis.nft.turbo.collection.domain.entity.Collection;
import cn.hollis.nft.turbo.collection.domain.entity.CollectionSnapshot;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.factory.Mappers;

import java.util.Date;
import java.util.List;

/**
 * @author Hollis
 */
@Mapper(nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface CollectionConvertor {

    CollectionConvertor INSTANCE = Mappers.getMapper(CollectionConvertor.class);

    /**
     * 转换为VO
     *
     * @param request
     * @return
     */
    @Mapping(target = "inventory", source = "request.saleableInventory")
    @Mapping(target = "state", expression = "java(setState(request.getState(), request.getSaleTime(), request.getSaleableInventory()))")
    public CollectionVO mapToVo(Collection request);

    /**
     * 转换为实体
     * @param request
     * @return
     */
    @Mapping(target = "saleableInventory", source = "request.inventory")
    @Mapping(target = "state", ignore = true)
    public Collection mapToEntity(CollectionVO request);

    /**
     * 设置状态
     * @param state
     * @param saleTime
     * @param saleableInventory
     * @return
     */
    public default GoodsState setState(CollectionStateEnum state, Date saleTime, Long saleableInventory) {
        return CollectionVO.getState(state, saleTime, saleableInventory);
    }

    /**
     * 创建快照
     *
     * @param request
     * @return
     */
    @Mapping(target = "collectionId", source = "request.id")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "gmtCreate", ignore = true)
    @Mapping(target = "gmtModified", ignore = true)
    public CollectionSnapshot createSnapshot(Collection request);

    /**
     * 转换为实体
     *
     * @param request
     * @return
     */
    public Collection mapToEntity(CollectionCreateRequest request);

    /**
     * 转换为VO
     *
     * @param request
     * @return
     */
    public List<CollectionVO> mapToVo(List<Collection> request);
}
