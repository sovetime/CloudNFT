package cn.hollis.nft.turbo.api.box.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;



//用户持有的盲盒
//相比BlindBoxVO增加了盲盒条目的 id 信息，相比BlindBoxItemVO减少了不该被用户看到的信息
@Getter
@Setter
@ToString
public class HeldBlindBoxVO implements Serializable {

    //盲盒ID
    private Long id;
    //盲盒条目ID
    private Long itemId;
    //盲盒名称
    private String name;
    //盲盒封面
    private String cover;
    //价格
    private BigDecimal price;
    //购买时间
    private Date buyTime;
}
