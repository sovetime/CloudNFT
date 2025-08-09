package cn.hollis.nft.turbo.api.collection.request;

import cn.hollis.nft.turbo.base.request.PageRequest;
import lombok.Getter;
import lombok.Setter;


//空投记录分页查询参数
@Getter
@Setter
public class AirDropPageQueryRequest extends PageRequest {

    private String collectionId;

    private String userId;
}
