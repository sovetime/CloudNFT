package cn.hollis.nft.turbo.api.collection.request;

import cn.hollis.nft.turbo.api.collection.constant.GoodsSaleBizType;
import cn.hollis.nft.turbo.api.goods.constant.GoodsEvent;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;


//藏品空投请求
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CollectionAirDropRequest extends BaseCollectionRequest {

    //接收用户ID
    @NotNull(message = "recipientUserId 不能为空")
    private String recipientUserId;

    //数量
    @Min(value = 1, message = "数量不能小于1")
    private Integer quantity;

    //商品类型
    @NotNull(message = "bizType 不能为空")
    private GoodsSaleBizType bizType;

    @Override
    public GoodsEvent getEventType() {
        return GoodsEvent.AIR_DROP;
    }
}
