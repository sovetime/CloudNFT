package cn.hollis.nft.turbo.user.domain.entity.convertor;

import cn.hollis.nft.turbo.api.user.response.data.BasicUserInfo;
import cn.hollis.nft.turbo.api.user.response.data.UserInfo;
import cn.hollis.nft.turbo.user.domain.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.factory.Mappers;

import java.util.List;

//用户实体转换器
//使用 MapStruct 框架实现 User 实体与各种 VO 之间的自动转换
//@Mapper MapStruct核心注解，标记对象映射接口，NullValueCheckStrategy.ALWAYS表示总是进行空值检查，源属性为null则将目标属性设置为默认值
@Mapper(nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface UserConvertor {

    //过 MapStruct 的 Mappers 工厂获取当前接口的实现类实例
    UserConvertor INSTANCE = Mappers.getMapper(UserConvertor.class);

    //转换为vo
    @Mapping(target = "userId", source = "request.id")
    @Mapping(target = "createTime", source = "request.gmtCreate")
    public UserInfo mapToVo(User request);

    //转换为简单的VO
    @Mapping(target = "userId", source = "request.id")
    public BasicUserInfo mapToBasicVo(User request);

    //转换为实体
    @Mapping(target = "id", source = "request.userId")
    public User mapToEntity(UserInfo request);

    //转换为VO
    @Mapping(target = "userId", source = "request.id")
    public List<UserInfo> mapToVo(List<User> request);
}
