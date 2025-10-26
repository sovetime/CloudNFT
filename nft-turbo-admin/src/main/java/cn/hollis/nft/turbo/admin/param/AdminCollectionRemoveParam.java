package cn.hollis.nft.turbo.admin.param;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;


//藏品下架参数
@Setter
@Getter
public class AdminCollectionRemoveParam {

    @NotNull(message = "藏品id不能为空")
    private Long collectionId;
}
