package cn.hollis.nft.turbo.api.goods.response;

import cn.hollis.nft.turbo.base.response.BaseResponse;
import lombok.Getter;
import lombok.Setter;

import static cn.hollis.nft.turbo.base.exception.BizErrorCode.DUPLICATED;


@Getter
@Setter
public class GoodsBookResponse extends BaseResponse {
    private Long bookId;

    public static class GoodsBookResponseBuilder {
        private Long bookId;

        public GoodsBookResponseBuilder bookId(Long bookId) {
            this.bookId = bookId;
            return this;
        }

        public GoodsBookResponse buildSuccess() {
            GoodsBookResponse response = new GoodsBookResponse();
            response.setBookId(bookId);
            response.setSuccess(true);
            return response;
        }

        public GoodsBookResponse buildDuplicated() {
            GoodsBookResponse response = new GoodsBookResponse();
            response.setBookId(bookId);
            response.setSuccess(true);
            response.setResponseCode(DUPLICATED.getCode());
            response.setResponseMessage(DUPLICATED.getMessage());
            return response;
        }

        public GoodsBookResponse buildFail(String code, String msg) {
            GoodsBookResponse response = new GoodsBookResponse();
            response.setBookId(bookId);
            response.setSuccess(false);
            response.setResponseCode(code);
            response.setResponseMessage(msg);
            return response;
        }
    }
}
