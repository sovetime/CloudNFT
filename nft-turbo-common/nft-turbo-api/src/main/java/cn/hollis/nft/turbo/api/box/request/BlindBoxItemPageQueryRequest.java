package cn.hollis.nft.turbo.api.box.request;

import cn.hollis.nft.turbo.base.request.PageRequest;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class BlindBoxItemPageQueryRequest extends PageRequest {

    private String userId;

    private String state;

    private String keyword;

    private Long boxId;

}
