package cn.hollis.nft.turbo.api.user.service;

import cn.hollis.nft.turbo.api.user.request.*;
import cn.hollis.nft.turbo.api.user.response.UserOperatorResponse;
import cn.hollis.nft.turbo.api.user.response.UserQueryResponse;
import cn.hollis.nft.turbo.api.user.response.data.UserInfo;
import cn.hollis.nft.turbo.base.response.PageResponse;

//用户服务接口
public interface UserFacadeService {

    //用户信息查询
    UserQueryResponse<UserInfo> query(UserQueryRequest userQueryRequest);

    //分页查询用户信息
    PageResponse<UserInfo> pageQuery(UserPageQueryRequest userPageQueryRequest);

    //用户注册
    UserOperatorResponse register(UserRegisterRequest userRegisterRequest);

    //用户信息修改
    UserOperatorResponse modify(UserModifyRequest userModifyRequest);

    //用户实名认证
    UserOperatorResponse auth(UserAuthRequest userAuthRequest);

    //用户激活
    UserOperatorResponse active(UserActiveRequest userActiveRequest);

}
