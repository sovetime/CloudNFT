package cn.hollis.nft.turbo.tcc.response;

import cn.hollis.nft.turbo.tcc.entity.TransCancelSuccessType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class TransactionCancelResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    private Boolean success;

    private String errorCode;

    private String errorMsg;

    private TransCancelSuccessType transCancelSuccessType;

    public TransactionCancelResponse(Boolean success, TransCancelSuccessType transCancelSuccessType) {
        this.success = success;
        this.transCancelSuccessType = transCancelSuccessType;
    }

    public TransactionCancelResponse(Boolean success, String errorCode, String errorMsg) {
        this.success = success;
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }
}
