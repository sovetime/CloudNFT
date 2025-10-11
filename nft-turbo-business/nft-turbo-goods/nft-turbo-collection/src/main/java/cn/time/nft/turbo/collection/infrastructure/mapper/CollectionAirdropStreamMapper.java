package cn.time.nft.turbo.collection.infrastructure.mapper;

import cn.time.nft.turbo.collection.domain.entity.CollectionAirdropStream;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

//藏品空投流水信息 Mapper 接口
@Mapper
public interface CollectionAirdropStreamMapper extends BaseMapper<CollectionAirdropStream> {

    //根据标识符查询
    CollectionAirdropStream selectByIdentifier(String identifier, String streamType, Long collectionId,String recipientUserId);

}
