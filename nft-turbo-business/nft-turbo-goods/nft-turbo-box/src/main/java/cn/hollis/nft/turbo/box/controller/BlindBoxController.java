package cn.hollis.nft.turbo.box.controller;

import cn.dev33.satoken.stp.StpUtil;
import cn.hollis.nft.turbo.api.box.constant.BlindBoxItemStateEnum;
import cn.hollis.nft.turbo.api.box.model.BlindBoxVO;
import cn.hollis.nft.turbo.api.box.model.HeldBlindBoxVO;
import cn.hollis.nft.turbo.api.box.request.BlindBoxItemPageQueryRequest;
import cn.hollis.nft.turbo.api.box.request.BlindBoxPageQueryRequest;
import cn.hollis.nft.turbo.api.box.service.BlindBoxReadFacadeService;
import cn.hollis.nft.turbo.api.inventory.service.InventoryFacadeService;
import cn.hollis.nft.turbo.base.response.PageResponse;
import cn.hollis.nft.turbo.base.response.SingleResponse;
import cn.hollis.nft.turbo.box.domain.entity.BlindBoxItem;
import cn.hollis.nft.turbo.box.domain.service.BlindBoxItemService;
import cn.hollis.nft.turbo.box.domain.service.BlindBoxService;
import cn.hollis.nft.turbo.box.exception.BlindBoxException;
import cn.hollis.nft.turbo.web.util.MultiResultConvertor;
import cn.hollis.nft.turbo.web.vo.MultiResult;
import cn.hollis.nft.turbo.web.vo.Result;
import cn.hutool.core.lang.Assert;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import static cn.hollis.nft.turbo.box.exception.BlindBoxErrorCode.*;

/**
 * @author Hollis
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("box")
public class BlindBoxController {
    @Autowired
    private BlindBoxItemService blindBoxItemService;

    @Autowired
    private BlindBoxService blindBoxService;

    @Autowired
    private BlindBoxReadFacadeService blindBoxReadFacadeService;

    @Autowired
    private InventoryFacadeService inventoryFacadeService;

    /**
     * 盲盒列表
     *
     * @param
     * @return 结果
     */
    @GetMapping("/boxList")
    public MultiResult<BlindBoxVO> boxList(@NotBlank String state, String keyword, int pageSize, int currentPage) {
        BlindBoxPageQueryRequest blindBoxPageQueryRequest = new BlindBoxPageQueryRequest();
        blindBoxPageQueryRequest.setState(state);
        blindBoxPageQueryRequest.setKeyword(keyword);
        blindBoxPageQueryRequest.setCurrentPage(currentPage);
        blindBoxPageQueryRequest.setPageSize(pageSize);
        PageResponse<BlindBoxVO> pageResponse = blindBoxReadFacadeService.pageQueryBlindBox(blindBoxPageQueryRequest);
        return MultiResultConvertor.convert(pageResponse);
    }

    /**
     * 已持有盲盒列表
     *
     * @param keyword
     * @param pageSize
     * @param currentPage
     * @return
     */
    @GetMapping("/heldBoxList")
    public MultiResult<HeldBlindBoxVO> heldBoxList(String keyword, int pageSize, int currentPage) {
        String userId = (String) StpUtil.getLoginId();
        BlindBoxItemPageQueryRequest blindBoxItemPageQueryRequest = new BlindBoxItemPageQueryRequest();
        blindBoxItemPageQueryRequest.setState(BlindBoxItemStateEnum.ASSIGNED.name());
        blindBoxItemPageQueryRequest.setUserId(userId);
        blindBoxItemPageQueryRequest.setCurrentPage(currentPage);
        blindBoxItemPageQueryRequest.setPageSize(pageSize);
        blindBoxItemPageQueryRequest.setKeyword(keyword);
        PageResponse<HeldBlindBoxVO> pageResponse = blindBoxReadFacadeService.pageQueryBlindBoxItem(blindBoxItemPageQueryRequest);
        return MultiResultConvertor.convert(pageResponse);
    }

    /**
     * 盲盒详情
     *
     * @param
     * @return 结果
     */
    @GetMapping("/boxInfo")
    public Result<BlindBoxVO> boxInfo(Long boxId) {
        SingleResponse<BlindBoxVO> singleResponse = blindBoxReadFacadeService.queryById(boxId);
        return Result.success(singleResponse.getData());
    }

    @PostMapping("/openBlindBox")
    public Result<BlindBoxItem> openBlindBox(@RequestBody Map<String, Object> requestData) {
        String userId = (String) StpUtil.getLoginId();
        Integer itemId = (Integer) requestData.get("itemId");
        BlindBoxItem blindBoxItem = blindBoxItemService.queryById(itemId.longValue());
        if (blindBoxItem == null) {
            throw new BlindBoxException(BLIND_BOX_ITEM_NOT_EXIST);
        }

        if (!userId.equals(blindBoxItem.getUserId())) {
            throw new BlindBoxException(BLIND_BOX_ITEM_OPEN_PERMISSION_CHECK_FAILED);
        }
        blindBoxItem = blindBoxItemService.open(blindBoxItem);
        Assert.isTrue(blindBoxItem != null, () -> new BlindBoxException(BLIND_BOX_OPEN_FAILED));
        return Result.success(blindBoxItem);
    }
}
