package cn.hollis.nft.turbo.user.domain.service;

import cn.hollis.nft.turbo.api.user.constant.UserOperateTypeEnum;
import cn.hollis.nft.turbo.user.domain.entity.User;
import cn.hollis.nft.turbo.user.domain.entity.UserOperateStream;
import cn.hollis.nft.turbo.user.infrastructure.mapper.UserOperateStreamMapper;
import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Date;

//用户操作流水表 服务类
@Service
public class UserOperateStreamService extends ServiceImpl<UserOperateStreamMapper, UserOperateStream> {

    // 保存流水记录并返回结果
    public Long insertStream(User user, UserOperateTypeEnum type) {
        UserOperateStream stream = new UserOperateStream();
        stream.setUserId(String.valueOf(user.getId()));
        stream.setOperateTime(new Date());
        stream.setType(type.name());
        stream.setParam(JSON.toJSONString(user));
        boolean result = save(stream);
        if (result) {
            return stream.getId();
        }
        return null;
    }
}
