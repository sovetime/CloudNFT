package cn.hollis.nft.turbo.box.infrastructure.mapper;

import cn.hollis.nft.turbo.box.domain.entity.BlindBoxItem;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 盲盒条目信息 Mapper 接口
 * </p>
 *
 * @author Hollis
 * @since 2024-10-05
 */
@Mapper
public interface BlindBoxItemMapper extends BaseMapper<BlindBoxItem> {

    /**
     * 根据盲盒id和状态查询随机条目id
     * <pre>
     *      通过 SELECT xxx FROM xxx WHERE xxx ORDER BY RAND() LIMIT 1 实现
     * </pre>
     *
     * @param blindBoxId
     * @param state
     * @return
     */
    Long queryRandomByBoxIdAndState(Long blindBoxId, String state);
}
