package cn.time.nft.turbo.api.user.response;

import cn.time.nft.turbo.api.user.response.data.UserInfo;
import cn.time.nft.turbo.base.response.BaseResponse;
import lombok.Getter;
import lombok.Setter;


//用户操作响应
@Getter
@Setter
public class UserOperatorResponse extends BaseResponse {

    private UserInfo user;
}
