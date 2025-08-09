package cn.hollis.nft.turbo.api.collection.response;

import cn.hollis.nft.turbo.base.response.BaseResponse;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class CollectionModifyResponse extends BaseResponse {

    //藏品id
    private Long collectionId;
}
