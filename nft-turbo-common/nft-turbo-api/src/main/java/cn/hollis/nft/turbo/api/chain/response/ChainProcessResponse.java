package cn.hollis.nft.turbo.api.chain.response;

import cn.hollis.nft.turbo.base.response.BaseResponse;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


//链处理结果
@Setter
@Getter
@ToString
public class ChainProcessResponse<T> extends BaseResponse {
    private static final long serialVersionUID = 1L;

    private T data;

    public static class Builder<T> {
        private T data;

        private String responseCode;

        private String responseMessage;

        public Builder(T data) {
            this.data = data;
        }

        public Builder() {
        }

        public Builder data(T data) {
            this.data = data;
            return this;
        }

        public Builder responseCode(String responseCode) {
            this.responseCode = responseCode;
            return this;
        }

        public Builder responseMessage(String responseMessage) {
            this.responseMessage = responseMessage;
            return this;
        }

        public ChainProcessResponse buildSuccess() {
            ChainProcessResponse response = new ChainProcessResponse();
            response.setSuccess(true);
            response.data = this.data;
            return response;
        }

        public ChainProcessResponse buildFailed() {
            ChainProcessResponse response = new ChainProcessResponse();
            response.setSuccess(false);
            response.setResponseCode(this.responseCode);
            response.setResponseMessage(this.responseMessage);
            return response;
        }
    }
}
