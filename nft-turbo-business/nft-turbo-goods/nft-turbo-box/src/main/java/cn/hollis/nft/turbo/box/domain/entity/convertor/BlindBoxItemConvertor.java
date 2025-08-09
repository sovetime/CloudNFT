package cn.hollis.nft.turbo.box.domain.entity.convertor;

import cn.hollis.nft.turbo.api.box.model.BlindBoxItemVO;
import cn.hollis.nft.turbo.api.box.model.HeldBlindBoxVO;
import cn.hollis.nft.turbo.api.box.request.BlindBoxItemCreateRequest;
import cn.hollis.nft.turbo.box.domain.entity.BlindBoxItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author Hollis
 */
@Mapper(nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface BlindBoxItemConvertor {

    BlindBoxItemConvertor INSTANCE = Mappers.getMapper(BlindBoxItemConvertor.class);

    /**
     * 转换为vo
     *
     * @param request
     * @return
     */
    public BlindBoxItemVO mapToVo(BlindBoxItem request);


    /**
     * 转换为vo
     *
     * @param request
     * @return
     */
    @Mapping(target = "itemId", source = "request.id")
    @Mapping(target = "id", source = "request.blindBoxId")
    @Mapping(target = "price", source = "request.purchasePrice")
    @Mapping(target = "buyTime", source = "request.assignTime")
    public HeldBlindBoxVO mapToHeldVo(BlindBoxItem request);

    /**
     * 转换为vo
     * @param request
     * @return
     */
    public List<HeldBlindBoxVO> mapToHeldVo(List<BlindBoxItem> request);


    /**
     * 转换为vo
     *
     * @param request
     * @return
     */
    public List<BlindBoxItemVO> mapToVo(List<BlindBoxItem> request);

    /**
     * 转换为实体
     *
     * @param request
     * @return
     */
    public BlindBoxItem mapToEntity(BlindBoxItemCreateRequest request);
}
