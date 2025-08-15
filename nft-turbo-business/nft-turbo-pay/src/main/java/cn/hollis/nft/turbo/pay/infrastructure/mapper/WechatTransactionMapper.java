package cn.hollis.nft.turbo.pay.infrastructure.mapper;

import cn.hollis.nft.turbo.pay.domain.entity.WechatTransaction;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface WechatTransactionMapper extends BaseMapper<WechatTransaction> {


}
