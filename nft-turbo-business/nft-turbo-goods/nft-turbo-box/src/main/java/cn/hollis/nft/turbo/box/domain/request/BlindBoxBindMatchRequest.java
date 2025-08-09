package cn.hollis.nft.turbo.box.domain.request;

import cn.hollis.nft.turbo.base.request.BaseRequest;
import lombok.*;

/**
 * @author wswyb001
 * @date 2024/01/17
 */

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class BlindBoxBindMatchRequest extends BaseRequest {

    /**
     * '盲盒id'
     */
    private Long blindBoxId;


}
