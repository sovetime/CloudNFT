package cn.hollis.nft.turbo.goods.infrastructure.mapper;

import cn.hollis.nft.turbo.goods.entity.GoodsBook;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;


//预约Mapper
@Mapper
public interface GoodsBookMapper extends BaseMapper<GoodsBook> {

    //根据预约买家和商品查询预约信息
    GoodsBook selectByGoodsIdAndBuyerId(String goodsId, String goodsType, String buyerId);
}
