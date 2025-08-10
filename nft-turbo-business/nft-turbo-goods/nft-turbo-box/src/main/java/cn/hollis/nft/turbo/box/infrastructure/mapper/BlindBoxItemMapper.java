package cn.hollis.nft.turbo.box.infrastructure.mapper;

import cn.hollis.nft.turbo.box.domain.entity.BlindBoxItem;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;


//盲盒条目信息 Mapper 接口
@Mapper
public interface BlindBoxItemMapper extends BaseMapper<BlindBoxItem> {

    //根据盲盒id和状态查询随机条目id
    Long queryRandomByBoxIdAndState(Long blindBoxId, String state);
}
