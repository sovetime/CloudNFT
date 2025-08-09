package cn.hollis.nft.turbo.api.pay.request;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class PayQueryByBizNo implements PayQueryCondition {

    private String bizNo;

    private String bizType;
}
