package cn.hollis.nft.turbo.api.collection.response;

import cn.hollis.nft.turbo.base.response.BaseResponse;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class BlindBoxCollectionSaleResponse extends BaseResponse {

    //持有藏品id
    private Long heldCollectionId;

}
