package cn.hollis.nft.turbo.pay.domain.entity.convertor;

import cn.hollis.nft.turbo.api.pay.model.PayOrderVO;
import cn.hollis.nft.turbo.api.pay.request.PayCreateRequest;
import cn.hollis.nft.turbo.pay.domain.entity.PayOrder;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.factory.Mappers;

import java.util.List;


@Mapper(nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface PayOrderConvertor {

    PayOrderConvertor INSTANCE = Mappers.getMapper(PayOrderConvertor.class);

    //转换实体
    public PayOrder mapToEntity(PayCreateRequest request);

    //转换vo
    public PayOrderVO mapToVo(PayOrder request);

    //转换vo
    public List<PayOrderVO> mapToVo(List<PayOrder> request);
}
