package cn.hollis.nft.turbo.user.facade;

import cn.hollis.nft.turbo.api.user.request.*;
import cn.hollis.nft.turbo.api.user.request.condition.UserIdQueryCondition;
import cn.hollis.nft.turbo.api.user.request.condition.UserPhoneAndPasswordQueryCondition;
import cn.hollis.nft.turbo.api.user.request.condition.UserPhoneQueryCondition;
import cn.hollis.nft.turbo.api.user.response.UserOperatorResponse;
import cn.hollis.nft.turbo.api.user.response.UserQueryResponse;
import cn.hollis.nft.turbo.api.user.response.data.UserInfo;
import cn.hollis.nft.turbo.api.user.service.UserFacadeService;
import cn.hollis.nft.turbo.base.response.PageResponse;
import cn.hollis.nft.turbo.rpc.facade.Facade;
import cn.hollis.nft.turbo.user.domain.entity.User;
import cn.hollis.nft.turbo.user.domain.entity.convertor.UserConvertor;
import cn.hollis.nft.turbo.user.domain.service.UserService;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;


@DubboService(version = "1.0.0")
public class UserFacadeServiceImpl implements UserFacadeService {

    @Autowired
    private UserService userService;

    //用户信息查询
    @Override
    public UserQueryResponse<UserInfo> query(UserQueryRequest userQueryRequest) {
        User user = switch (userQueryRequest.getUserQueryCondition()) {
            case UserIdQueryCondition userIdQueryCondition:
                yield userService.findById(userIdQueryCondition.getUserId());
            case UserPhoneQueryCondition userPhoneQueryCondition:
                yield userService.findByTelephone(userPhoneQueryCondition.getTelephone());
            case UserPhoneAndPasswordQueryCondition userPhoneAndPasswordQueryCondition:
                yield userService.findByTelephoneAndPass(userPhoneAndPasswordQueryCondition.getTelephone(), userPhoneAndPasswordQueryCondition.getPassword());
            default:
                throw new UnsupportedOperationException(userQueryRequest.getUserQueryCondition() + "'' is not supported");
        };

        UserQueryResponse<UserInfo> response = new UserQueryResponse();
        response.setSuccess(true);
        UserInfo userInfo = UserConvertor.INSTANCE.mapToVo(user);
        response.setData(userInfo);
        return response;
    }

    //分页查询用户信息
    @Override
    public PageResponse<UserInfo> pageQuery(UserPageQueryRequest userPageQueryRequest) {
        var queryResult = userService.pageQueryByState(userPageQueryRequest.getKeyWord(), userPageQueryRequest.getState(), userPageQueryRequest.getCurrentPage(), userPageQueryRequest.getPageSize());
        PageResponse<UserInfo> response = new PageResponse<>();
        if (!queryResult.getSuccess()) {
            response.setSuccess(false);
            return response;
        }
        response.setSuccess(true);
        response.setDatas(UserConvertor.INSTANCE.mapToVo(queryResult.getDatas()));
        response.setCurrentPage(queryResult.getCurrentPage());
        response.setPageSize(queryResult.getPageSize());
        return response;
    }

    //用户注册
    @Override
    @Facade
    public UserOperatorResponse register(UserRegisterRequest userRegisterRequest) {
        return userService.register(userRegisterRequest.getTelephone(), userRegisterRequest.getInviteCode());
    }

    //用户信息修改
    @Override
    @Facade
    public UserOperatorResponse modify(UserModifyRequest userModifyRequest) {
        return userService.modify(userModifyRequest);
    }

    //用户实名认证
    @Override
    @Facade
    public UserOperatorResponse auth(UserAuthRequest userAuthRequest) {
        return userService.auth(userAuthRequest);
    }

    //用户激活
    @Override
    @Facade
    public UserOperatorResponse active(UserActiveRequest userActiveRequest) {
        return userService.active(userActiveRequest);
    }
}
