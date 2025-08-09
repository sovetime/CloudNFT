package cn.hollis.nft.turbo.admin.param;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;


//藏品创建参数
@Setter
@Getter
public class AdminCollectionCreateParam {

    @NotNull(message = "藏品名称不能为空")
    private String name;

    @NotNull(message = "藏品封面不能为空")
    private String cover;

    private String detail;

    @NotNull(message = "价格不能为空")
    private BigDecimal price;

    @Min(value = 1, message = "藏品数量不能小于1")
    private Long quantity;

    @NotNull(message = "藏品发售时间不能为空")
    private String saleTime;

    @NotNull(message = "藏品是否预约不能为空")
    private Integer canBook;

    //藏品预约开始时间
    private String bookStartTime;

    //藏品预约结束时间
    private String bookEndTime;
}
