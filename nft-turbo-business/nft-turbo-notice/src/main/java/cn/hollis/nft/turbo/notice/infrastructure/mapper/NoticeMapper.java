package cn.hollis.nft.turbo.notice.infrastructure.mapper;

import cn.hollis.nft.turbo.notice.domain.entity.Notice;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 链操作 Mapper 接口
 * </p>
 *
 * @author wswyb001
 * @since 2024-01-19
 */
@Mapper
public interface NoticeMapper extends BaseMapper<Notice> {

}
