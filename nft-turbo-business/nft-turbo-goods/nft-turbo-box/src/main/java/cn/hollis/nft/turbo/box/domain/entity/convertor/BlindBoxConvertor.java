package cn.hollis.nft.turbo.box.domain.entity.convertor;

import cn.hollis.nft.turbo.api.box.constant.BlindBoxStateEnum;
import cn.hollis.nft.turbo.api.box.model.BlindBoxVO;
import cn.hollis.nft.turbo.api.box.request.BlindBoxCreateRequest;
import cn.hollis.nft.turbo.api.goods.constant.GoodsState;
import cn.hollis.nft.turbo.box.domain.entity.BlindBox;
import org.apache.commons.lang3.BooleanUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.factory.Mappers;

import java.util.Date;
import java.util.List;

/**
 * @author Hollis
 */
@Mapper(nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface BlindBoxConvertor {

    BlindBoxConvertor INSTANCE = Mappers.getMapper(BlindBoxConvertor.class);

    /**
     * 转换为VO
     *
     * @param request
     * @return
     */
    @Mapping(target = "inventory", source = "request.saleableInventory")
    @Mapping(target = "state", expression = "java(setState(request.getState(), request.getSaleTime(), request.getSaleableInventory()))")
    public BlindBoxVO mapToVo(BlindBox request);

    /**
     * 设置状态
     * @param state
     * @param saleTime
     * @param saleableInventory
     * @return
     */
    public default GoodsState setState(BlindBoxStateEnum state, Date saleTime, Long saleableInventory) {
        return BlindBoxVO.getState(state, saleTime, saleableInventory);
    }

    /**
     * 转换为实体
     *
     * @param request
     * @return
     */
    @Mapping(target = "canBook", source = "canBook", qualifiedByName = "mapBooleanToInteger")
    public BlindBox mapToEntity(BlindBoxCreateRequest request);

    /**
     * 转换为VO
     *
     * @param request
     * @return
     */
    public List<BlindBoxVO> mapToVo(List<BlindBox> request);

    /**
     * boolean 转为 Integer
     * @param value
     * @return
     */
    @Named("mapBooleanToInteger")
    default Integer mapBooleanToInteger(Boolean value) {
        return BooleanUtils.toInteger(value);
    }
}
