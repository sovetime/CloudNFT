package cn.hollis.nft.turbo.api.box.response;

import cn.hollis.nft.turbo.base.response.BaseResponse;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class BlindBoxBindResponse extends BaseResponse {

    //盲盒绑定总数
    private int bindTotal;

}
