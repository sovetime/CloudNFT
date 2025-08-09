package cn.hollis.nft.turbo.collection.infrastructure.mapper;

import cn.hollis.nft.turbo.collection.domain.entity.CollectionInventoryStream;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 藏品库存流水信息 Mapper 接口
 * </p>
 *
 * @author wswyb001
 * @since 2024-01-19
 */
@Mapper
public interface CollectionInventoryStreamMapper extends BaseMapper<CollectionInventoryStream> {
    /**
     * 根据标识符查询
     *
     * @param identifier
     * @param streamType
     * @param collectionId
     * @return
     */
    CollectionInventoryStream selectByIdentifier(String identifier, String streamType, Long collectionId);

}
