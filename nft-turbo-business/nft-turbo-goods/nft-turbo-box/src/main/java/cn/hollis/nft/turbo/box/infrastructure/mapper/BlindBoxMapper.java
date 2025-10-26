package cn.hollis.nft.turbo.box.infrastructure.mapper;

import cn.hollis.nft.turbo.box.domain.entity.BlindBox;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;


//盲盒信息 Mapper 接口
@Mapper
public interface BlindBoxMapper extends BaseMapper<BlindBox> {

    //根据藏品标识查询藏品信息
    BlindBox selectByIdentifier(String identifier);

    //库存确认扣减
    int confirmSale(Long id, Long occupiedInventory, Integer quantity);

    //库存扣减
    int sale(Long id, Integer quantity);

    //库存扣减-无hint版
    int saleWithoutHint(Long id, Integer quantity);

    //库存退回
    int cancel(Long id, Integer quantity);

    //冻结库存
    int freezeInventory(Long id, Integer quantity);

    //解冻并扣减库存
    int unfreezeAndSale(Long id, Integer quantity);

    //解冻库存
    int unfreezeInventory(Long id, Integer quantity);
}
