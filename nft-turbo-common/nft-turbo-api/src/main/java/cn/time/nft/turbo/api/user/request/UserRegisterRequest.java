package cn.time.nft.turbo.api.user.request;

import cn.time.nft.turbo.base.request.BaseRequest;
import lombok.*;


@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UserRegisterRequest extends BaseRequest {

    private String telephone;

    private String inviteCode;

    private String password;

}
