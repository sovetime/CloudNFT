package cn.hollis.nft.turbo.api.user.service;

import cn.hollis.nft.turbo.api.user.request.UserRegisterRequest;
import cn.hollis.nft.turbo.api.user.response.UserOperatorResponse;


public interface UserManageFacadeService {

    //管理用户注册
    UserOperatorResponse registerAdmin(UserRegisterRequest userRegisterRequest);

    //用户冻结
    UserOperatorResponse freeze(Long userId);

    //用户解冻
    UserOperatorResponse unfreeze(Long userId);

}
