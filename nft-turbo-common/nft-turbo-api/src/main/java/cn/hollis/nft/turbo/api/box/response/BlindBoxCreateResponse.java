package cn.hollis.nft.turbo.api.box.response;

import cn.hollis.nft.turbo.base.response.BaseResponse;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class BlindBoxCreateResponse extends BaseResponse {

    //盲盒id
    private Long blindBoxId;

}
