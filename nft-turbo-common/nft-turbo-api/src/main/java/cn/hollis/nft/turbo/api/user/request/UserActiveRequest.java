package cn.hollis.nft.turbo.api.user.request;

import cn.hollis.nft.turbo.base.request.BaseRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UserActiveRequest extends BaseRequest {

    private Long userId;
    private String blockChainPlatform;
    private String blockChainUrl;

}
