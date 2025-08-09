package cn.hollis.nft.turbo.collection.infrastructure.mapper;

import cn.hollis.nft.turbo.collection.domain.entity.CollectionStream;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 藏品流水信息 Mapper 接口
 * </p>
 *
 * @author wswyb001
 * @since 2024-01-19
 */
@Mapper
public interface CollectionStreamMapper extends BaseMapper<CollectionStream> {
    /**
     * 根据标识符查询
     *
     * @param identifier
     * @param streamType
     * @param collectionId
     * @return
     */
    CollectionStream selectByIdentifier(String identifier, String streamType, Long collectionId);

}
