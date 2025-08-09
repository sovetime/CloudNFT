package cn.hollis.nft.turbo.admin.controller;

import cn.dev33.satoken.stp.SaLoginModel;
import cn.dev33.satoken.stp.StpUtil;
import cn.hollis.nft.turbo.admin.infrastructure.exception.AdminException;
import cn.hollis.nft.turbo.admin.param.AdminLoginParam;
import cn.hollis.nft.turbo.admin.vo.AdminLoginVO;
import cn.hollis.nft.turbo.api.user.constant.UserRole;
import cn.hollis.nft.turbo.api.user.request.UserPageQueryRequest;
import cn.hollis.nft.turbo.api.user.request.UserQueryRequest;
import cn.hollis.nft.turbo.api.user.response.UserOperatorResponse;
import cn.hollis.nft.turbo.api.user.response.UserQueryResponse;
import cn.hollis.nft.turbo.api.user.response.data.UserInfo;
import cn.hollis.nft.turbo.api.user.service.UserFacadeService;
import cn.hollis.nft.turbo.api.user.service.UserManageFacadeService;
import cn.hollis.nft.turbo.base.response.PageResponse;
import cn.hollis.nft.turbo.web.util.MultiResultConvertor;
import cn.hollis.nft.turbo.web.vo.MultiResult;
import cn.hollis.nft.turbo.web.vo.Result;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.*;

import static cn.hollis.nft.turbo.admin.infrastructure.exception.AdminErrorCode.ADMIN_USER_NOT_EXIST;


// 用户后台管理
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("admin/user")
@CrossOrigin(origins = "*")
public class UserAdminController {

    @DubboReference(version = "1.0.0")
    private UserFacadeService userFacadeService;

    @DubboReference(version = "1.0.0")
    private UserManageFacadeService userManageFacadeService;

    //默认登录超时时间：7天
    private static final Integer DEFAULT_LOGIN_SESSION_TIMEOUT = 60 * 60 * 24 * 7;

    @GetMapping("/getUserInfo")
    public Result<UserInfo> getUserInfo() {
        String userId = (String) StpUtil.getLoginId();
        UserQueryRequest request = new UserQueryRequest(Long.valueOf(userId));
        UserQueryResponse<UserInfo> userQueryResponse = userFacadeService.query(request);
        UserInfo userInfo = userQueryResponse.getData();

        if (userInfo == null) {
            throw new AdminException(ADMIN_USER_NOT_EXIST);
        }
        return Result.success(userInfo);
    }

    @GetMapping("/userList")
    public MultiResult<UserInfo> userList(@NotBlank String state, String keyWord, int pageSize, int currentPage) {
        UserPageQueryRequest userPageQueryRequest = new UserPageQueryRequest();
        userPageQueryRequest.setState(state);
        userPageQueryRequest.setKeyWord(keyWord);
        userPageQueryRequest.setCurrentPage(currentPage);
        userPageQueryRequest.setPageSize(pageSize);
        PageResponse<UserInfo> pageResponse = userFacadeService.pageQuery(userPageQueryRequest);
        return MultiResultConvertor.convert(pageResponse);
    }

    @PostMapping("/registerAdmin")
    public Result<Boolean> registerAdmin(@Valid String phone) {
        //不直接提供管理员注册功能，通过数据订正进行管理员账号初始化
        //INSERT INTO `users` (`gmt_create`,`gmt_modified`,`nick_name`,`password_hash`,`state`,`invite_code`,`telephone`,`inviter_id`,`last_login_time`,`profile_photo_url`,`block_chain_url`,`block_chain_platform`,`certification`,`real_name`,`id_card_no`,`user_role`,`deleted`,`lock_version`) VALUES ('2024-07-06 14:13:26','2024-07-06 14:13:26','藏家_R3qWhY3333','34347c343003e57232a5d21f14fe399e','ACTIVE',null,'13333333333',null,null,null,null,null,null,null,null,'ADMIN',0,0);

        return null;
    }

    @PostMapping("/login")
    public Result<AdminLoginVO> login(@Valid @RequestBody AdminLoginParam loginParam) {

        //查询用户信息
        UserQueryRequest userQueryRequest = new UserQueryRequest(loginParam.getTelephone(), loginParam.getPassword());
        UserQueryResponse<UserInfo> userQueryResponse = userFacadeService.query(userQueryRequest);
        UserInfo userInfo = userQueryResponse.getData();
        //用户不存在（密码错误）或者不是管理员用户，不能登陆
        if (userInfo == null || !userInfo.getUserRole().equals(UserRole.ADMIN)) {
            return Result.error(ADMIN_USER_NOT_EXIST.getCode(), ADMIN_USER_NOT_EXIST.getMessage());
        } else {
            //登录
            StpUtil.login(userInfo.getUserId(), new SaLoginModel().setIsLastingCookie(loginParam.getRememberMe()).setTimeout(DEFAULT_LOGIN_SESSION_TIMEOUT));
            StpUtil.getSession().set(userInfo.getUserId().toString(), userInfo);
            AdminLoginVO loginVO = new AdminLoginVO(userInfo);
            return Result.success(loginVO);
        }
    }

    @PostMapping("/logout")
    public Result<Boolean> logout() {
        StpUtil.logout();
        return Result.success(true);
    }

    @PostMapping("/freeze")
    public Result<UserOperatorResponse> freeze(@Valid Long userId) {
        String adminUserId = (String) StpUtil.getLoginId();
        //查询用户信息
        UserQueryRequest adminQueryRequest = new UserQueryRequest(Long.valueOf(adminUserId));
        UserQueryResponse<UserInfo> userQueryResponse = userFacadeService.query(adminQueryRequest);
        UserInfo userInfo = userQueryResponse.getData();
        //用户不存在或者不是管理员用户
        if (userInfo == null || !userInfo.getUserRole().equals(UserRole.ADMIN)) {
            return Result.error(ADMIN_USER_NOT_EXIST.getCode(), ADMIN_USER_NOT_EXIST.getMessage());
        }
        var res = userManageFacadeService.freeze(userId);

        //重新查出用户信息，更新登录的session，确保用户权限实时更新
        refreshUserInSession(userId);

        return Result.success(res);
    }

    @PostMapping("/unfreeze")
    public Result<UserOperatorResponse> unfreeze(@Valid Long userId) {
        String adminUserId = (String) StpUtil.getLoginId();
        //查询用户信息
        UserQueryRequest adminQueryRequest = new UserQueryRequest(Long.valueOf(adminUserId));
        UserQueryResponse<UserInfo> userQueryResponse = userFacadeService.query(adminQueryRequest);
        UserInfo userInfo = userQueryResponse.getData();
        //用户不存在或者不是管理员用户
        if (userInfo == null || !userInfo.getUserRole().equals(UserRole.ADMIN)) {
            return Result.error(ADMIN_USER_NOT_EXIST.getCode(), ADMIN_USER_NOT_EXIST.getMessage());
        }
        var res = userManageFacadeService.unfreeze(userId);

        //重新查出用户信息，更新登录的session，确保用户权限实时更新
        refreshUserInSession(userId);
        return Result.success(res);
    }

    private void refreshUserInSession(Long userId) {
        UserQueryRequest userQueryRequest = new UserQueryRequest(userId);
        UserQueryResponse userQueryResponse = userFacadeService.query(userQueryRequest);
        StpUtil.getSession().set(userId.toString(), userQueryResponse.getData());
    }

}
