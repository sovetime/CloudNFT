package cn.time.nft.turbo.api.box.response;

import cn.time.nft.turbo.base.response.BaseResponse;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class BlindBoxSaleResponse extends BaseResponse {
    //盲盒条目id
    private Long blindBoxItemId;
}
