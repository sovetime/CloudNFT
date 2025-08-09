package cn.hollis.nft.turbo.collection.controller;

import cn.dev33.satoken.stp.StpUtil;
import cn.hollis.nft.turbo.api.chain.constant.ChainOperateBizTypeEnum;
import cn.hollis.nft.turbo.api.chain.constant.ChainOperateTypeEnum;
import cn.hollis.nft.turbo.api.chain.request.ChainProcessRequest;
import cn.hollis.nft.turbo.api.chain.service.ChainFacadeService;
import cn.hollis.nft.turbo.api.collection.model.CollectionVO;
import cn.hollis.nft.turbo.api.collection.model.HeldCollectionVO;
import cn.hollis.nft.turbo.api.collection.request.CollectionPageQueryRequest;
import cn.hollis.nft.turbo.api.collection.request.HeldCollectionPageQueryRequest;
import cn.hollis.nft.turbo.api.collection.service.CollectionReadFacadeService;
import cn.hollis.nft.turbo.api.goods.constant.GoodsType;
import cn.hollis.nft.turbo.api.goods.service.GoodsFacadeService;
import cn.hollis.nft.turbo.api.user.request.UserQueryRequest;
import cn.hollis.nft.turbo.api.user.response.UserQueryResponse;
import cn.hollis.nft.turbo.api.user.response.data.UserInfo;
import cn.hollis.nft.turbo.api.user.service.UserFacadeService;
import cn.hollis.nft.turbo.base.response.PageResponse;
import cn.hollis.nft.turbo.base.response.SingleResponse;
import cn.hollis.nft.turbo.collection.domain.entity.HeldCollection;
import cn.hollis.nft.turbo.collection.domain.request.HeldCollectionDestroyRequest;
import cn.hollis.nft.turbo.collection.domain.request.HeldCollectionTransferRequest;
import cn.hollis.nft.turbo.collection.domain.service.impl.HeldCollectionService;
import cn.hollis.nft.turbo.collection.exception.CollectionException;
import cn.hollis.nft.turbo.collection.param.DestroyParam;
import cn.hollis.nft.turbo.collection.param.TransferParam;
import cn.hollis.nft.turbo.web.util.MultiResultConvertor;
import cn.hollis.nft.turbo.web.vo.MultiResult;
import cn.hollis.nft.turbo.web.vo.Result;
import cn.hutool.core.lang.Assert;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static cn.hollis.nft.turbo.api.common.constant.CommonConstant.SEPARATOR;
import static cn.hollis.nft.turbo.api.order.constant.OrderErrorCode.*;
import static cn.hollis.nft.turbo.collection.exception.CollectionErrorCode.HELD_COLLECTION_OWNER_CHECK_ERROR;
import static cn.hollis.nft.turbo.collection.exception.CollectionErrorCode.HELD_COLLECTION_SAVE_FAILED;

/**
 * @author wswyb001
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("collection")
public class CollectionController {

    @Autowired
    private GoodsFacadeService goodsFacadeService;
    @Autowired
    private CollectionReadFacadeService collectionReadFacadeService;
    @Autowired
    private ChainFacadeService chainFacadeService;
    @Autowired
    private UserFacadeService userFacadeService;
    @Autowired
    private HeldCollectionService heldCollectionService;

    /**
     * 藏品列表
     *
     * @param
     * @return 结果
     */
    @GetMapping("/collectionList")
    public MultiResult<CollectionVO> collectionList(@NotBlank String state, String keyword, int pageSize, int currentPage) {
        CollectionPageQueryRequest collectionPageQueryRequest = new CollectionPageQueryRequest();
        collectionPageQueryRequest.setState(state);
        collectionPageQueryRequest.setKeyword(keyword);
        collectionPageQueryRequest.setCurrentPage(currentPage);
        collectionPageQueryRequest.setPageSize(pageSize);
        PageResponse<CollectionVO> pageResponse = collectionReadFacadeService.pageQuery(collectionPageQueryRequest);
        return MultiResultConvertor.convert(pageResponse);
    }

    /**
     * 藏品详情
     *
     * @param
     * @return 结果
     */
    @GetMapping("/collectionInfo")
    public Result<CollectionVO> collectionInfo(@NotBlank String collectionId) {
        CollectionVO collectionVO = (CollectionVO) goodsFacadeService.getGoods(collectionId, GoodsType.COLLECTION);
        if (collectionVO.canBook()) {
            try {
                String userId = (String) StpUtil.getLoginId();
                Boolean hasBooked = goodsFacadeService.isGoodsBooked(collectionId, GoodsType.COLLECTION, userId);
                collectionVO.setHasBooked(hasBooked);
            } catch (Exception e) {
                //如果用户未登录或其他异常
                collectionVO.setHasBooked(false);
            }
        }
        return Result.success(collectionVO);
    }

    /**
     * 用户持有藏品列表
     *
     * @param
     * @return 结果
     */
    @GetMapping("/heldCollectionList")
    public MultiResult<HeldCollectionVO> heldCollectionList(String keyword, String state, int pageSize, int currentPage) {
        String userId = (String) StpUtil.getLoginId();
        HeldCollectionPageQueryRequest heldCollectionPageQueryRequest = new HeldCollectionPageQueryRequest();
        heldCollectionPageQueryRequest.setState(state);
        heldCollectionPageQueryRequest.setUserId(userId);
        heldCollectionPageQueryRequest.setCurrentPage(currentPage);
        heldCollectionPageQueryRequest.setPageSize(pageSize);
        heldCollectionPageQueryRequest.setKeyword(keyword);
        PageResponse<HeldCollectionVO> pageResponse = collectionReadFacadeService.pageQueryHeldCollection(heldCollectionPageQueryRequest);
        return MultiResultConvertor.convert(pageResponse);
    }

    /**
     * 用户持有藏品列表
     *
     * @param
     * @return 结果
     */
    @GetMapping("/heldCollectionCount")
    public Result<Long> heldCollectionCount() {
        String userId = (String) StpUtil.getLoginId();

        SingleResponse<Long> response = collectionReadFacadeService.queryHeldCollectionCount(userId);
        return Result.success(response.getData());
    }

    /**
     * 用户持有藏品详情
     *
     * @param
     * @return 结果
     */
    @GetMapping("/heldCollectionInfo")
    public Result<HeldCollectionVO> heldCollectionInfo(@NotBlank String heldCollectionId) {
        SingleResponse<HeldCollectionVO> singleResponse = collectionReadFacadeService.queryHeldCollectionById(Long.valueOf(heldCollectionId));
        return Result.success(singleResponse.getData());
    }

    @PostMapping("/destroy")
    public Result<Boolean> destroy(@Valid @RequestBody DestroyParam param) {
        String userId = (String) StpUtil.getLoginId();

        HeldCollectionDestroyRequest request = new HeldCollectionDestroyRequest();
        request.setOperatorId(userId);
        request.setHeldCollectionId(param.getHeldCollectionId());
        request.setIdentifier(param.getHeldCollectionId());
        HeldCollection heldCollection = heldCollectionService.destroy(request);

        if (null != heldCollection) {
            ChainProcessRequest chainProcessRequest = new ChainProcessRequest();
            chainProcessRequest.setBizId(String.valueOf(param.getHeldCollectionId()));
            chainProcessRequest.setBizType(ChainOperateBizTypeEnum.HELD_COLLECTION.name());
            chainProcessRequest.setIdentifier(param.getHeldCollectionId() + SEPARATOR + ChainOperateTypeEnum.COLLECTION_DESTROY.name());
            UserInfo owner = (UserInfo) StpUtil.getSession().get(userId);
            chainProcessRequest.setOwner(owner.getBlockChainUrl());
            chainProcessRequest.setClassId(String.valueOf(heldCollection.getCollectionId()));
            chainProcessRequest.setNtfId(heldCollection.getNftId());
            var res = chainFacadeService.destroy(chainProcessRequest);
            return Result.success(res.getSuccess());
        }
        return Result.success(false);
    }


    @PostMapping("/transfer")
    public Result<Boolean> transfer(@Valid @RequestBody TransferParam param) {
        String userId = (String) StpUtil.getLoginId();
        if (userId.equals(param.getRecipientUserId())) {
            throw new CollectionException(TRANSFER_SELF_ERROR);
        }
        SingleResponse<HeldCollectionVO> response = collectionReadFacadeService.queryHeldCollectionById(Long.parseLong(param.getHeldCollectionId()));
        HeldCollectionVO heldCollection = response.getData();
        UserQueryRequest userQueryRequest = new UserQueryRequest(Long.valueOf(param.getRecipientUserId()));
        UserQueryResponse<UserInfo> userQueryResponse = userFacadeService.query(userQueryRequest);
        UserInfo recipient = userQueryResponse.getData();

        if (!userQueryResponse.getSuccess() || userQueryResponse.getData() == null) {
            throw new CollectionException(USER_NOT_EXIST);
        }

        UserInfo userInfo = userQueryResponse.getData();
        if (!userInfo.userCanBuy()) {
            throw new CollectionException(BUYER_STATUS_ABNORMAL);
        }

        if (null != heldCollection && null != recipient) {
            Assert.isTrue(StringUtils.equals(heldCollection.getUserId(), userId), () -> new CollectionException(HELD_COLLECTION_OWNER_CHECK_ERROR));

            //本地数据先变更
            HeldCollectionTransferRequest transferRequest = new HeldCollectionTransferRequest();
            transferRequest.setRecipientUserId(param.getRecipientUserId());
            transferRequest.setHeldCollectionId(param.getHeldCollectionId());
            transferRequest.setOperatorId(userId);
            transferRequest.setIdentifier(param.getHeldCollectionId() + "_TRANSFER");
            HeldCollection transferHeldCollection = heldCollectionService.transfer(transferRequest);
            Assert.notNull(transferHeldCollection, () -> new CollectionException(HELD_COLLECTION_SAVE_FAILED));

            ChainProcessRequest request = new ChainProcessRequest();
            request.setBizId(String.valueOf(transferHeldCollection.getId()));
            request.setBizType(ChainOperateBizTypeEnum.HELD_COLLECTION.name());
            request.setIdentifier(param.getHeldCollectionId() + SEPARATOR + param.getRecipientUserId() + SEPARATOR + ChainOperateTypeEnum.COLLECTION_TRANSFER.name());
            UserInfo owner = (UserInfo) StpUtil.getSession().get(userId);
            request.setOwner(owner.getBlockChainUrl());
            request.setClassId(String.valueOf(heldCollection.getCollectionId()));
            request.setNtfId(transferHeldCollection.getNftId());
            request.setRecipient(recipient.getBlockChainUrl());
            var res = chainFacadeService.transfer(request);
            return Result.success(res.getSuccess());
        }
        return Result.success(false);
    }
}
