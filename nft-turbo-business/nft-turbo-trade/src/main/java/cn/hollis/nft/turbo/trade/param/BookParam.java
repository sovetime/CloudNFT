package cn.hollis.nft.turbo.trade.param;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Hollis
 */
@Getter
@Setter
public class BookParam {

    @NotNull(message = "goodsId is null")
    private String goodsId;

    @NotNull(message = "goodsType is null")
    private String goodsType;

}
