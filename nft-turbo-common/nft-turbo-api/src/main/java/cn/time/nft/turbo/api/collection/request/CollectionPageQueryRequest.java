package cn.time.nft.turbo.api.collection.request;

import cn.time.nft.turbo.base.request.PageRequest;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CollectionPageQueryRequest extends PageRequest {

    private String state;

    private String keyword;

}
