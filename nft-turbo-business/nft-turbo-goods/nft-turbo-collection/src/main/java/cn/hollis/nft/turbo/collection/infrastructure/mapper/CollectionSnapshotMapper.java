package cn.hollis.nft.turbo.collection.infrastructure.mapper;

import cn.hollis.nft.turbo.collection.domain.entity.Collection;
import cn.hollis.nft.turbo.collection.domain.entity.CollectionSnapshot;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 藏品快照信息 Mapper 接口
 * </p>
 *
 * @author Hollis
 * @since 2024-07-17
 */
@Mapper
public interface CollectionSnapshotMapper extends BaseMapper<CollectionSnapshot> {

    /**
     * 根据藏品标识查询藏品信息
     * @param collectionId
     * @param version
     * @return
     */
    Collection selectByVersion(String collectionId, Integer version);
}
