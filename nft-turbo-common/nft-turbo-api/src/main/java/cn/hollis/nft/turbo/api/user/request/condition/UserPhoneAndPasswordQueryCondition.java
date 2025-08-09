package cn.hollis.nft.turbo.api.user.request.condition;

import lombok.*;


@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UserPhoneAndPasswordQueryCondition implements UserQueryCondition {

    private static final long serialVersionUID = 1L;

    // 用户手机号
    private String telephone;

    //密码
    private String password;
}
