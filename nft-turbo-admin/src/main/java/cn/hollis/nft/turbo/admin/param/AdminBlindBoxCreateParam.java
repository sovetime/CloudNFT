package cn.hollis.nft.turbo.admin.param;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;


//盲盒创建参数
@Setter
@Getter
public class AdminBlindBoxCreateParam {

    @NotNull(message = "盲盒名称不能为空")
    private String name;

    @NotNull(message = "盲盒封面不能为空")
    private String cover;

    private String detail;

    @NotNull(message = "价格不能为空")
    private BigDecimal price;


    @Min(value = 1, message = "盲盒数量不能小于1")
    @Max(value = 1000, message = "盲盒数量不能超过1000")
    private Long quantity;

    @NotNull(message = "盲盒发售时间不能为空")
    private String saleTime;

    //盲盒是否预约
    private boolean canBook;

    //盲盒预约开始时间
    private String bookStartTime;

    //盲盒预约结束时间
    private String bookEndTime;

    //藏品列表
    private List<AdminBlindBoxCollectionCreateParam> collectionBoxParams;

}
