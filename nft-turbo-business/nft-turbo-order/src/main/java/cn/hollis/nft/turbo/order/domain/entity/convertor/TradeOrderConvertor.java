package cn.hollis.nft.turbo.order.domain.entity.convertor;

import cn.hollis.nft.turbo.api.order.model.TradeOrderVO;
import cn.hollis.nft.turbo.api.order.request.OrderCreateRequest;
import cn.hollis.nft.turbo.order.domain.entity.TradeOrder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author Hollis
 */
@Mapper(nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface TradeOrderConvertor {

    TradeOrderConvertor INSTANCE = Mappers.getMapper(TradeOrderConvertor.class);

    /**
     * 转换实体
     *
     * @param request
     * @return
     */
    public TradeOrder mapToEntity(OrderCreateRequest request);

    /**
     * 转换vo
     *
     * @param request
     * @return
     */
    @Mapping(target = "timeout", expression = "java(request.isTimeout())")
    public TradeOrderVO mapToVo(TradeOrder request);

    /**
     * 转换vo
     *
     * @param request
     * @return
     */
    public List<TradeOrderVO> mapToVo(List<TradeOrder> request);
}
