package cn.time.nft.turbo.collection.domain.response;

import cn.time.nft.turbo.base.response.BaseResponse;
import cn.time.nft.turbo.collection.domain.entity.Collection;
import cn.time.nft.turbo.collection.domain.entity.HeldCollection;
import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
public class CollectionConfirmSaleResponse extends BaseResponse {

    private Collection collection;

    private HeldCollection heldCollection;
}
