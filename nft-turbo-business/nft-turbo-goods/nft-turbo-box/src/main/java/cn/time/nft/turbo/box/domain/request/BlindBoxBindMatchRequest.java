package cn.time.nft.turbo.box.domain.request;

import cn.time.nft.turbo.base.request.BaseRequest;
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
