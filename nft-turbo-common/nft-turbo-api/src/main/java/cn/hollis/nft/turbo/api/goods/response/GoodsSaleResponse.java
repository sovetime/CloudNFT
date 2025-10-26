package cn.hollis.nft.turbo.api.goods.response;

import cn.hollis.nft.turbo.base.response.BaseResponse;
import lombok.Getter;
import lombok.Setter;

import static cn.hollis.nft.turbo.base.exception.BizErrorCode.DUPLICATED;


@Getter
@Setter
public class GoodsSaleResponse extends BaseResponse {

    //持有藏品id
    private Long heldCollectionId;

    public static class GoodsResponseBuilder {
        private Long heldCollectionId;

        public GoodsSaleResponse.GoodsResponseBuilder heldCollectionId(Long heldCollectionId) {
            this.heldCollectionId = heldCollectionId;
            return this;
        }

        public GoodsSaleResponse buildSuccess() {
            GoodsSaleResponse goodsSaleResponse = new GoodsSaleResponse();
            goodsSaleResponse.setHeldCollectionId(heldCollectionId);
            goodsSaleResponse.setSuccess(true);
            return goodsSaleResponse;
        }

        public GoodsSaleResponse buildDuplicated() {
            GoodsSaleResponse goodsSaleResponse = new GoodsSaleResponse();
            goodsSaleResponse.setHeldCollectionId(heldCollectionId);
            goodsSaleResponse.setSuccess(true);
            goodsSaleResponse.setResponseCode(DUPLICATED.getCode());
            goodsSaleResponse.setResponseMessage(DUPLICATED.getMessage());
            return goodsSaleResponse;
        }

        public GoodsSaleResponse buildFail(String code, String msg) {
            GoodsSaleResponse goodsSaleResponse = new GoodsSaleResponse();
            goodsSaleResponse.setHeldCollectionId(heldCollectionId);
            goodsSaleResponse.setSuccess(false);
            goodsSaleResponse.setResponseCode(code);
            goodsSaleResponse.setResponseMessage(msg);
            return goodsSaleResponse;
        }
    }
}
