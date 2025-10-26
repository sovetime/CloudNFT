package cn.hollis.nft.turbo.gateway.auth;

import cn.dev33.satoken.stp.StpInterface;
import cn.dev33.satoken.stp.StpUtil;
import cn.hollis.nft.turbo.api.user.constant.UserPermission;
import cn.hollis.nft.turbo.api.user.constant.UserRole;
import cn.hollis.nft.turbo.api.user.constant.UserStateEnum;
import cn.hollis.nft.turbo.api.user.response.data.UserInfo;
import org.springframework.stereotype.Component;

import java.util.List;


//自定义权限验证接口，主要是对用户权限、角色进行获取
@Component
public class StpInterfaceImpl implements StpInterface {

    // 获取权限列表
    @Override
    public List<String> getPermissionList(Object loginId, String loginType) {
        //根据 loginid 获取用户角色
        UserInfo userInfo = (UserInfo) StpUtil.getSessionByLoginId(loginId).get((String) loginId);

        if (userInfo.getUserRole() == UserRole.ADMIN || userInfo.getState().equals(UserStateEnum.ACTIVE.name()) || userInfo.getState().equals(UserStateEnum.AUTH.name()) ) {
            return List.of(UserPermission.BASIC.name(), UserPermission.AUTH.name());
        }

        if (userInfo.getState().equals(UserStateEnum.INIT.name())) {
            return List.of(UserPermission.BASIC.name());
        }

        if (userInfo.getState().equals(UserStateEnum.FROZEN.name())) {
            return List.of(UserPermission.FROZEN.name());
        }

        return List.of(UserPermission.NONE.name());
    }

    // 获取角色列表
    @Override
    public List<String> getRoleList(Object loginId, String loginType) {
        //根据 loginid 获取用户角色
        UserInfo userInfo = (UserInfo) StpUtil.getSessionByLoginId(loginId).get((String) loginId);

        if (userInfo.getUserRole() == UserRole.ADMIN) {
            return List.of(UserRole.ADMIN.name());
        }
        return List.of(UserRole.CUSTOMER.name());
    }
}
