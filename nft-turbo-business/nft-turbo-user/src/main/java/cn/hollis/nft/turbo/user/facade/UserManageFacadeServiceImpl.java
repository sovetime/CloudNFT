package cn.hollis.nft.turbo.user.facade;

import cn.hollis.nft.turbo.api.user.request.UserRegisterRequest;
import cn.hollis.nft.turbo.api.user.response.UserOperatorResponse;
import cn.hollis.nft.turbo.api.user.service.UserManageFacadeService;
import cn.hollis.nft.turbo.rpc.facade.Facade;
import cn.hollis.nft.turbo.user.domain.service.UserService;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;


@DubboService(version = "1.0.0")
public class UserManageFacadeServiceImpl implements UserManageFacadeService {

    @Autowired
    private UserService userService;

    //管理员注册
    @Override
    @Facade
    public UserOperatorResponse registerAdmin(UserRegisterRequest userRegisterRequest) {
        return userService.registerAdmin(userRegisterRequest.getTelephone(), userRegisterRequest.getPassword());
    }

    //冻结
    @Override
    public UserOperatorResponse freeze(Long userId) {
        return userService.freeze(userId);
    }

    //解冻
    @Override
    public UserOperatorResponse unfreeze(Long userId) {
        return userService.unfreeze(userId);
    }
}
