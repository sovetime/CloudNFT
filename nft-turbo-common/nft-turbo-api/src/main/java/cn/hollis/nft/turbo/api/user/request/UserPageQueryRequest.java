package cn.hollis.nft.turbo.api.user.request;

import cn.hollis.nft.turbo.base.request.BaseRequest;
import lombok.*;


@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UserPageQueryRequest extends BaseRequest {


    //手机号关键字
    private String keyWord;

    //用户状态
    private String state;

    private int currentPage;

    private int pageSize;

}
