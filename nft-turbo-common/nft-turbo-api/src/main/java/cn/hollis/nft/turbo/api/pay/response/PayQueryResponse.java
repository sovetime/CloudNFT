package cn.hollis.nft.turbo.api.pay.response;

import cn.hollis.nft.turbo.api.pay.model.PayOrderVO;
import cn.hollis.nft.turbo.base.response.BaseResponse;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
public class PayQueryResponse extends BaseResponse {

    private List<PayOrderVO> payOrders;
}
