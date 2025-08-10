package cn.hollis.nft.turbo.box.domain.request;

import cn.hollis.nft.turbo.base.request.BaseRequest;
import lombok.*;



@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class BlindBoxBindMatchRequest extends BaseRequest {

    //盲盒id
    private Long blindBoxId;

}
