package cn.hollis.nft.turbo.box.domain.response;

import cn.hollis.nft.turbo.base.response.BaseResponse;
import cn.hollis.nft.turbo.box.domain.entity.BlindBox;
import cn.hollis.nft.turbo.box.domain.entity.BlindBoxItem;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Hollis
 */
@Setter
@Getter
public class BlindBoxConfirmSaleResponse extends BaseResponse {

    private BlindBox blindBox;

    private BlindBoxItem blindBoxItem;
}
