package cn.time.nft.turbo.api.collection.response;

import cn.time.nft.turbo.base.response.BaseResponse;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class CollectionChainResponse extends BaseResponse {

    //藏品id
    private Long collectionId;

}
