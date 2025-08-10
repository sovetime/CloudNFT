package cn.hollis.nft.turbo.box.domain.service;

import cn.hollis.nft.turbo.api.box.request.BlindBoxItemPageQueryRequest;
import cn.hollis.nft.turbo.base.response.PageResponse;
import cn.hollis.nft.turbo.box.domain.entity.BlindBoxItem;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;


//盲盒条目服务
public interface BlindBoxItemService extends IService<BlindBoxItem> {

    //批量创建
    Boolean batchCreateItem(List<BlindBoxItem> blindBoxItems);

    //打开盲盒
    BlindBoxItem open(BlindBoxItem blindBoxItem);

    //查询
    BlindBoxItem queryById(Long blindBoxItemId);

    //查询
    List<BlindBoxItem> queryListById(List<Long> itemIds);

    //查询一个随机的 BoxItem
    public Long queryRandomByBoxIdAndState(Long blindBoxId, String state);

    //查询
    List<BlindBoxItem> queryListByBoxIdAndState(Long blindBoxId, String state);

    //分页查询盲盒条目
    public PageResponse<BlindBoxItem> pageQueryBlindBoxItem(BlindBoxItemPageQueryRequest request);

}
