package cn.hollis.nft.turbo.base.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;


//分页响应
@Setter
@Getter
public class PageResponse<T> extends MultiResponse<T> {
    private static final long serialVersionUID = 1L;

    private int currentPage;

    private int pageSize;

    private int totalPage;

    private int total;

    public static <T> PageResponse<T> of(List<T> datas, int total, int pageSize,int currentPage) {
        PageResponse<T> pageResponse = new PageResponse<>();
        pageResponse.setSuccess(true);
        pageResponse.setDatas(datas);
        pageResponse.setTotal(total);
        pageResponse.setPageSize(pageSize);
        pageResponse.setCurrentPage(currentPage);
        pageResponse.setTotalPage((pageSize + total - 1) / pageSize);
        return pageResponse;
    }
}
