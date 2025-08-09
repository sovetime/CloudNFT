package cn.hollis.nft.turbo.api.check.response;

import cn.hollis.nft.turbo.base.response.BaseResponse;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class InventoryCheckResponse extends BaseResponse {

    //核对结果
    private Boolean checkResult;
}