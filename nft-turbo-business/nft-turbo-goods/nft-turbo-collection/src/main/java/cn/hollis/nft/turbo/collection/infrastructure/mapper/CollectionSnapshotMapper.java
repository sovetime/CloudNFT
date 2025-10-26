package cn.hollis.nft.turbo.collection.infrastructure.mapper;

import cn.hollis.nft.turbo.collection.domain.entity.Collection;
import cn.hollis.nft.turbo.collection.domain.entity.CollectionSnapshot;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;


//藏品快照信息 Mapper 接口
@Mapper
public interface CollectionSnapshotMapper extends BaseMapper<CollectionSnapshot> {

    //根据藏品标识查询藏品信息
    Collection selectByVersion(String collectionId, Integer version);
}
