package cn.hollis.nft.turbo.box.domain.request;

import cn.hollis.nft.turbo.base.request.BaseRequest;
import lombok.*;

/**
 * @author Hollis
 * 盲盒分配入参
 * @date 2025/01/11
 */

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class BlindBoxAssignRequest extends BaseRequest {

    /**
     * '盲盒id'
     */
    private Long blindBoxId;

    /**
     * '用户id'
     */
    private String userId;

    /**
     * '订单id'
     */
    private String orderId;
}