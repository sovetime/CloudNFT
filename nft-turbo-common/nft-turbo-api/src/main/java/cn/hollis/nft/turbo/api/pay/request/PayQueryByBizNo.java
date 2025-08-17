package cn.hollis.nft.turbo.api.pay.request;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class PayQueryByBizNo implements PayQueryCondition {

    // 业务单号
    private String bizNo;

    // 业务单类型
    private String bizType;
}
