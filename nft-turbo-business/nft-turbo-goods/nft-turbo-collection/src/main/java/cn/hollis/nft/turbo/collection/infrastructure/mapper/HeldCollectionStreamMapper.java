package cn.hollis.nft.turbo.collection.infrastructure.mapper;

import cn.hollis.nft.turbo.collection.domain.entity.HeldCollectionStream;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 持有藏品流水表Mapper
 *
 * @author Hollis
 */
@Mapper
public interface HeldCollectionStreamMapper extends BaseMapper<HeldCollectionStream> {
}