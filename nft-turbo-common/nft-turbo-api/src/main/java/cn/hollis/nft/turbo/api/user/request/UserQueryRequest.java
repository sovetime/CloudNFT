package cn.hollis.nft.turbo.api.user.request;

import cn.hollis.nft.turbo.api.user.request.condition.UserIdQueryCondition;
import cn.hollis.nft.turbo.api.user.request.condition.UserPhoneAndPasswordQueryCondition;
import cn.hollis.nft.turbo.api.user.request.condition.UserPhoneQueryCondition;
import cn.hollis.nft.turbo.api.user.request.condition.UserQueryCondition;
import cn.hollis.nft.turbo.base.request.BaseRequest;
import lombok.*;


@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UserQueryRequest extends BaseRequest {

    private UserQueryCondition userQueryCondition;

    public UserQueryRequest(Long userId) {
        UserIdQueryCondition userIdQueryCondition = new UserIdQueryCondition();
        userIdQueryCondition.setUserId(userId);
        this.userQueryCondition = userIdQueryCondition;
    }

    public UserQueryRequest(String telephone) {
        UserPhoneQueryCondition userPhoneQueryCondition = new UserPhoneQueryCondition();
        userPhoneQueryCondition.setTelephone(telephone);
        this.userQueryCondition = userPhoneQueryCondition;
    }

    public UserQueryRequest(String telephone, String password) {
        UserPhoneAndPasswordQueryCondition userPhoneAndPasswordQueryCondition = new UserPhoneAndPasswordQueryCondition();
        userPhoneAndPasswordQueryCondition.setTelephone(telephone);
        userPhoneAndPasswordQueryCondition.setPassword(password);
        this.userQueryCondition = userPhoneAndPasswordQueryCondition;
    }

}
