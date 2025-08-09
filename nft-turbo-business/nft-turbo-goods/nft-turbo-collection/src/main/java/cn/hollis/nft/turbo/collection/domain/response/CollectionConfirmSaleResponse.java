package cn.hollis.nft.turbo.collection.domain.response;

import cn.hollis.nft.turbo.base.response.BaseResponse;
import cn.hollis.nft.turbo.collection.domain.entity.Collection;
import cn.hollis.nft.turbo.collection.domain.entity.HeldCollection;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Hollis
 */
@Setter
@Getter
public class CollectionConfirmSaleResponse extends BaseResponse {

    private Collection collection;

    private HeldCollection heldCollection;
}
