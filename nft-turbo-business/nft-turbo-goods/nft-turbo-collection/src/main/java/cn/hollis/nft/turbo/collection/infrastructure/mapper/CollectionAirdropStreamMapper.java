package cn.hollis.nft.turbo.collection.infrastructure.mapper;

import cn.hollis.nft.turbo.collection.domain.entity.CollectionAirdropStream;
import cn.hollis.nft.turbo.collection.domain.entity.CollectionStream;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

//藏品空投流水信息 Mapper 接口
@Mapper
public interface CollectionAirdropStreamMapper extends BaseMapper<CollectionAirdropStream> {
    /**
     * 根据标识符查询
     *
     * @param identifier
     * @param streamType
     * @param collectionId
     * @param recipientUserId
     * @return
     */
    CollectionAirdropStream selectByIdentifier(String identifier, String streamType, Long collectionId,String recipientUserId);

}
