package cn.hollis.nft.turbo.collection.facade;

import cn.hollis.nft.turbo.api.chain.constant.ChainOperateBizTypeEnum;
import cn.hollis.nft.turbo.api.chain.request.ChainProcessRequest;
import cn.hollis.nft.turbo.api.chain.response.ChainProcessResponse;
import cn.hollis.nft.turbo.api.chain.response.data.ChainOperationData;
import cn.hollis.nft.turbo.api.chain.service.ChainFacadeService;
import cn.hollis.nft.turbo.api.collection.constant.CollectionInventoryModifyType;
import cn.hollis.nft.turbo.api.collection.model.HeldCollectionVO;
import cn.hollis.nft.turbo.api.collection.request.*;
import cn.hollis.nft.turbo.api.collection.response.*;
import cn.hollis.nft.turbo.api.collection.service.CollectionManageFacadeService;
import cn.hollis.nft.turbo.api.collection.service.CollectionReadFacadeService;
import cn.hollis.nft.turbo.api.goods.constant.GoodsType;
import cn.hollis.nft.turbo.api.inventory.request.InventoryRequest;
import cn.hollis.nft.turbo.api.inventory.service.InventoryFacadeService;
import cn.hollis.nft.turbo.api.user.request.UserQueryRequest;
import cn.hollis.nft.turbo.api.user.response.UserQueryResponse;
import cn.hollis.nft.turbo.api.user.response.data.UserInfo;
import cn.hollis.nft.turbo.api.user.service.UserFacadeService;
import cn.hollis.nft.turbo.base.response.SingleResponse;
import cn.hollis.nft.turbo.base.utils.RemoteCallWrapper;
import cn.hollis.nft.turbo.collection.domain.entity.Collection;
import cn.hollis.nft.turbo.collection.domain.service.CollectionService;
import cn.hollis.nft.turbo.collection.exception.CollectionException;
import cn.hollis.nft.turbo.rpc.facade.Facade;
import com.alibaba.fastjson.JSON;
import org.apache.dubbo.config.annotation.DubboService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

import static cn.hollis.nft.turbo.api.collection.constant.CollectionStateEnum.SUCCEED;
import static cn.hollis.nft.turbo.api.order.constant.OrderErrorCode.*;
import static cn.hollis.nft.turbo.base.response.ResponseCode.DUPLICATED;
import static cn.hollis.nft.turbo.collection.exception.CollectionErrorCode.COLLECTION_INVENTORY_UPDATE_FAILED;

/**
 * 藏品管理服务
 *
 * @author hollis
 */
@DubboService(version = "1.0.0")
public class CollectionManageFacadeServiceImpl implements CollectionManageFacadeService {

    private static final Logger logger = LoggerFactory.getLogger(CollectionManageFacadeServiceImpl.class);

    @Autowired
    private ChainFacadeService chainFacadeService;

    @Autowired
    private CollectionService collectionService;

    @Autowired
    private CollectionReadFacadeService collectionReadFacadeService;

    @Autowired
    private InventoryFacadeService inventoryFacadeService;

    @Autowired
    private UserFacadeService userFacadeService;

    @Override
    @Facade
    public CollectionChainResponse create(CollectionCreateRequest request) {
        Collection collection = collectionService.create(request);
        ChainProcessRequest chainProcessRequest = new ChainProcessRequest();
        chainProcessRequest.setIdentifier(request.getIdentifier());
        chainProcessRequest.setClassId(String.valueOf(collection.getId()));
        chainProcessRequest.setClassName(request.getName());
        chainProcessRequest.setBizType(ChainOperateBizTypeEnum.COLLECTION.name());
        chainProcessRequest.setBizId(collection.getId().toString());
        var chainRes = chainFacadeService.chain(chainProcessRequest);
        CollectionChainResponse response = new CollectionChainResponse();
        if (!chainRes.getSuccess()) {
            response.setSuccess(false);
            return response;
        }
        response.setSuccess(true);
        response.setCollectionId(collection.getId());
        return response;
    }

    @Override
    public CollectionRemoveResponse remove(CollectionRemoveRequest request) {
        CollectionRemoveResponse response = new CollectionRemoveResponse();
        Boolean result = collectionService.remove(request);

        if (result) {
            InventoryRequest inventoryRequest = new InventoryRequest();
            inventoryRequest.setGoodsId(request.getCollectionId().toString());
            inventoryFacadeService.invalid(inventoryRequest);
        }

        response.setSuccess(result);
        response.setCollectionId(request.getCollectionId());
        return response;
    }

    @Override
    @Facade
    public CollectionAirdropResponse airDrop(CollectionAirDropRequest request) {
        //检查用户是否可被空投，这里比较简单，后续如果节点比较多，可以改成责任链
        UserQueryRequest userQueryRequest = new UserQueryRequest(Long.valueOf(request.getRecipientUserId()));
        UserQueryResponse<UserInfo> userQueryResponse = userFacadeService.query(userQueryRequest);
        checkUser(userQueryResponse);
        //检查藏品是否可被空投，这里比较简单，后续如果节点比较多，可以改成责任链
        Collection collection = collectionService.queryById(request.getCollectionId());
        checkCollection(collection,request.getQuantity());

        CollectionAirdropResponse response = collectionService.airDrop(request, collection);

        //执行失败或幂等成功，则直接返回，不用调上链操作了
        if (!response.getSuccess() || response.getResponseCode().equals(DUPLICATED.name())) {
            return response;
        }

        for (HeldCollectionVO heldCollection : response.getHeldCollections()) {
            ChainProcessRequest chainProcessRequest = new ChainProcessRequest();
            chainProcessRequest.setRecipient(userQueryResponse.getData().getBlockChainUrl());
            chainProcessRequest.setClassId(String.valueOf(heldCollection.getCollectionId()));
            chainProcessRequest.setClassName(heldCollection.getName());
            chainProcessRequest.setSerialNo(heldCollection.getSerialNo());
            chainProcessRequest.setBizId(heldCollection.getId().toString());
            chainProcessRequest.setBizType(ChainOperateBizTypeEnum.HELD_COLLECTION.name());
            chainProcessRequest.setIdentifier(UUID.randomUUID().toString());
            //如果失败了，则依靠定时任务补偿
            ChainProcessResponse<ChainOperationData> chainProcessResponse = RemoteCallWrapper.call(req -> chainFacadeService.mint(req), chainProcessRequest, "mint");
        }
        response.setSuccess(response.getSuccess());
        return response;
    }

    private void checkCollection(Collection collection,Integer quantity) {
        if (collection == null) {
            throw new CollectionException(USER_NOT_EXIST);
        }

        if (collection.getState() != SUCCEED) {
            throw new CollectionException(GOODS_NOT_AVAILABLE);
        }

        if (collection.getSaleableInventory() < quantity) {
            throw new CollectionException(INVENTORY_NOT_ENOUGH);
        }
    }

    private static void checkUser(UserQueryResponse<UserInfo> userQueryResponse) {
        if (!userQueryResponse.getSuccess() || userQueryResponse.getData() == null) {
            throw new CollectionException(USER_NOT_EXIST);
        }

        UserInfo userInfo = userQueryResponse.getData();
        if (!userInfo.userCanBuy()) {
            throw new CollectionException(BUYER_STATUS_ABNORMAL);
        }
    }

    @Override
    public CollectionModifyResponse modifyInventory(CollectionModifyInventoryRequest request) {
        CollectionModifyResponse response = new CollectionModifyResponse();
        response.setCollectionId(request.getCollectionId());

        CollectionInventoryModifyResponse modifyResponse = collectionService.modifyInventory(request);

        if (!modifyResponse.getSuccess()) {
            response.setSuccess(false);
            response.setResponseCode(COLLECTION_INVENTORY_UPDATE_FAILED.getCode());
            response.setResponseMessage(COLLECTION_INVENTORY_UPDATE_FAILED.getMessage());
            return response;
        }

        if (modifyResponse.getModifyType() == CollectionInventoryModifyType.UNMODIFIED) {
            response.setSuccess(true);
            return response;
        }

        InventoryRequest inventoryRequest = new InventoryRequest();
        inventoryRequest.setGoodsId(request.getCollectionId().toString());
        inventoryRequest.setGoodsType(GoodsType.COLLECTION);
        inventoryRequest.setIdentifier(request.getIdentifier());
        inventoryRequest.setInventory(modifyResponse.getQuantityModified().intValue());
        SingleResponse<Boolean> inventoryResponse;
        if (modifyResponse.getModifyType() == CollectionInventoryModifyType.INCREASE) {
            inventoryResponse = inventoryFacadeService.increase(inventoryRequest);
        } else {
            inventoryResponse = inventoryFacadeService.decrease(inventoryRequest);
        }

        if (!inventoryResponse.getSuccess()) {
            logger.error("modify inventory failed : " + JSON.toJSONString(inventoryResponse));
            throw new CollectionException(COLLECTION_INVENTORY_UPDATE_FAILED);
        }

        response.setSuccess(true);
        return response;
    }

    @Override
    public CollectionModifyResponse modifyPrice(CollectionModifyPriceRequest request) {
        Boolean result = collectionService.modifyPrice(request);
        CollectionModifyResponse response = new CollectionModifyResponse();
        response.setSuccess(result);
        response.setCollectionId(request.getCollectionId());
        return response;
    }

}
