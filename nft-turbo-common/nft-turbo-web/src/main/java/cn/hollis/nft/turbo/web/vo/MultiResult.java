package cn.hollis.nft.turbo.web.vo;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

import static cn.hollis.nft.turbo.base.response.ResponseCode.SUCCESS;


@Getter
@Setter
public class MultiResult<T> extends Result<List<T>> {

    private long total;

    private int page;

    private int size;

    public MultiResult() {
        super();
    }

    public MultiResult(Boolean success, String code, String message, List<T> data, long total, int page, int size) {
        super(success, code, message, data);
        this.total = total;
        this.page = page;
        this.size = size;
    }

    public static <T> MultiResult<T> successMulti(List<T> data, long total, int page, int size) {
        return new MultiResult<>(true, SUCCESS.name(), SUCCESS.name(), data, total, page, size);
    }

    public static <T> MultiResult<T> errorMulti(String errorCode, String errorMsg) {
        return new MultiResult<>(true, errorCode, errorMsg, null, 0, 0, 0);
    }

}
