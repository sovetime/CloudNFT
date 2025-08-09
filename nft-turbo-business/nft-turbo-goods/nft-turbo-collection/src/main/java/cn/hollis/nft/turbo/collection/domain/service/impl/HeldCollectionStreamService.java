package cn.hollis.nft.turbo.collection.domain.service.impl;

import cn.hollis.nft.turbo.collection.domain.entity.HeldCollectionStream;
import cn.hollis.nft.turbo.collection.infrastructure.mapper.HeldCollectionStreamMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * 持有藏品流水表服务
 *
 * @author Hollis
 */
@Service
public class HeldCollectionStreamService extends ServiceImpl<HeldCollectionStreamMapper, HeldCollectionStream> {

    /**
     * 按照幂等号、持有藏品ID和流水类型查询
     */
    public HeldCollectionStream queryByIdAndStreamType(Long heldCollectionId, String streamType, String identifier) {
        return baseMapper.selectOne(
                new com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<HeldCollectionStream>()
                        .eq("held_collection_id", heldCollectionId)
                        .eq("stream_type", streamType)
                        .eq("identifier", identifier)
        );
    }
}