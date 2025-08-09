package cn.hollis.nft.turbo.goods.infrastructure.mapper;

import cn.hollis.nft.turbo.goods.entity.GoodsBook;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 预约Mapper
 *
 * @author Hollis
 */
@Mapper
public interface GoodsBookMapper extends BaseMapper<GoodsBook> {

    /**
     * 根据预约买家和商品查询预约信息
     *
     * @param goodsId 商品id
     * @param goodsType 商品类型
     * @param buyerId    买家ID
     * @return 预约单
     */
    GoodsBook selectByGoodsIdAndBuyerId(String goodsId, String goodsType, String buyerId);
}
