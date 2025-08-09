package cn.hollis.nft.turbo.box.domain.service;

import cn.hollis.nft.turbo.api.box.request.BlindBoxItemPageQueryRequest;
import cn.hollis.nft.turbo.base.response.PageResponse;
import cn.hollis.nft.turbo.box.domain.entity.BlindBoxItem;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 盲盒条目服务
 *
 * @author Hollis
 */
public interface BlindBoxItemService extends IService<BlindBoxItem> {

    /**
     * 批量创建
     *
     * @param blindBoxItems
     * @return
     */
    Boolean batchCreateItem(List<BlindBoxItem> blindBoxItems);

    /**
     * 打开盲盒
     *
     * @param blindBoxItem
     * @return
     */
    BlindBoxItem open(BlindBoxItem blindBoxItem);

    /**
     * 查询
     *
     * @param blindBoxItemId
     * @return
     */
    BlindBoxItem queryById(Long blindBoxItemId);

    /**
     * 查询
     *
     * @param itemIds
     * @return
     */
    List<BlindBoxItem> queryListById(List<Long> itemIds);

    /**
     * 查询一个随机的 BoxItem
     *
     * @param blindBoxId
     * @param state
     * @return
     */
    public Long queryRandomByBoxIdAndState(Long blindBoxId, String state);

    /**
     * 查询
     *
     * @param blindBoxId
     * @param state
     * @return
     */
    List<BlindBoxItem> queryListByBoxIdAndState(Long blindBoxId, String state);

    /**
     * 分页查询盲盒条目
     *
     * @param request
     * @return
     */
    public PageResponse<BlindBoxItem> pageQueryBlindBoxItem(BlindBoxItemPageQueryRequest request);

}
