package cn.hollis.nft.turbo.goods.facade.service;

import cn.hollis.nft.turbo.api.box.model.BlindBoxVO;
import cn.hollis.nft.turbo.api.box.service.BlindBoxReadFacadeService;
import cn.hollis.nft.turbo.api.collection.model.CollectionVO;
import cn.hollis.nft.turbo.api.collection.service.CollectionReadFacadeService;
import cn.hollis.nft.turbo.api.goods.constant.GoodsEvent;
import cn.hollis.nft.turbo.api.goods.constant.GoodsType;
import cn.hollis.nft.turbo.api.goods.model.BaseGoodsVO;
import cn.hollis.nft.turbo.api.goods.model.GoodsStreamVO;
import cn.hollis.nft.turbo.api.goods.request.*;
import cn.hollis.nft.turbo.api.goods.response.GoodsBookResponse;
import cn.hollis.nft.turbo.api.goods.response.GoodsSaleResponse;
import cn.hollis.nft.turbo.api.goods.service.GoodsFacadeService;
import cn.hollis.nft.turbo.base.response.SingleResponse;
import cn.hollis.nft.turbo.box.domain.entity.BlindBoxInventoryStream;
import cn.hollis.nft.turbo.box.domain.request.BlindBoxAssignRequest;
import cn.hollis.nft.turbo.box.domain.service.BlindBoxService;
import cn.hollis.nft.turbo.box.infrastructure.mapper.BlindBoxInventoryStreamMapper;
import cn.hollis.nft.turbo.collection.domain.entity.CollectionInventoryStream;
import cn.hollis.nft.turbo.collection.domain.entity.CollectionStream;
import cn.hollis.nft.turbo.collection.domain.entity.HeldCollection;
import cn.hollis.nft.turbo.collection.domain.request.HeldCollectionCreateRequest;
import cn.hollis.nft.turbo.collection.domain.service.CollectionService;
import cn.hollis.nft.turbo.collection.domain.service.impl.HeldCollectionService;
import cn.hollis.nft.turbo.collection.infrastructure.mapper.CollectionInventoryStreamMapper;
import cn.hollis.nft.turbo.collection.infrastructure.mapper.CollectionStreamMapper;
import cn.hollis.nft.turbo.goods.entity.convertor.GoodsStreamConvertor;
import cn.hollis.nft.turbo.goods.service.GoodsBookService;
import cn.hollis.nft.turbo.goods.service.HotGoodsService;
import cn.hollis.nft.turbo.rpc.facade.Facade;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 商品聚合服务
 *
 * @author Hollis
 */
@DubboService(version = "1.0.0")
public class GoodsFacadeServiceImpl implements GoodsFacadeService {

    private static final String ERROR_CODE_UNSUPPORTED_GOODS_TYPE = "UNSUPPORTED_GOODS_TYPE";

    @Autowired
    private CollectionService collectionService;

    @Autowired
    private BlindBoxService blindBoxService;

    @Autowired
    private CollectionReadFacadeService collectionReadFacadeService;

    @Autowired
    private CollectionInventoryStreamMapper collectionInventoryStreamMapper;

    @Autowired
    private BlindBoxInventoryStreamMapper blindBoxInventoryStreamMapper;

    @Autowired
    private BlindBoxReadFacadeService blindBoxReadFacadeService;

    @Autowired
    private GoodsBookService goodsBookService;

    @Autowired
    private HotGoodsService hotGoodsService;

    @Autowired
    private HeldCollectionService heldCollectionService;

    @Override
    public BaseGoodsVO getGoods(String goodsId, GoodsType goodsType) {
        return switch (goodsType) {
            case COLLECTION -> {
                SingleResponse<CollectionVO> response = collectionReadFacadeService.queryById(Long.valueOf(goodsId));
                if (response.getSuccess()) {
                    yield response.getData();
                }
                yield null;
            }

            case BLIND_BOX -> {
                SingleResponse<BlindBoxVO> response = blindBoxReadFacadeService.queryById(Long.valueOf(goodsId));
                if (response.getSuccess()) {
                    yield response.getData();
                }
                yield null;
            }
            default -> throw new UnsupportedOperationException(ERROR_CODE_UNSUPPORTED_GOODS_TYPE);
        };
    }

    @Override
    public GoodsStreamVO getGoodsInventoryStream(String goodsId, GoodsType goodsType, GoodsEvent goodsEvent, String identifier) {
        return switch (goodsType) {
            case COLLECTION -> {
                CollectionInventoryStream collectionInventoryStream = collectionInventoryStreamMapper.selectByIdentifier(identifier, goodsEvent.name(), Long.valueOf(goodsId));

                yield GoodsStreamConvertor.INSTANCE.mapToVo(collectionInventoryStream);
            }

            case BLIND_BOX -> {
                BlindBoxInventoryStream blindBoxInventoryStream = blindBoxInventoryStreamMapper.selectByIdentifier(identifier, goodsEvent.name(), Long.valueOf(goodsId));
                yield GoodsStreamConvertor.INSTANCE.mapToVo(blindBoxInventoryStream);
            }
            default -> throw new UnsupportedOperationException(ERROR_CODE_UNSUPPORTED_GOODS_TYPE);
        };
    }

    @Override
    public GoodsSaleResponse trySale(GoodsSaleRequest request) {
        GoodsTrySaleRequest goodsTrySaleRequest = new GoodsTrySaleRequest(request.getIdentifier(), request.getGoodsId(), request.getQuantity());
        GoodsType goodsType = GoodsType.valueOf(request.getGoodsType());

        Boolean trySaleResult = switch (goodsType) {
            case BLIND_BOX -> blindBoxService.sale(goodsTrySaleRequest);
            case COLLECTION -> collectionService.sale(goodsTrySaleRequest);
            default -> throw new UnsupportedOperationException(ERROR_CODE_UNSUPPORTED_GOODS_TYPE);
        };

        GoodsSaleResponse response = new GoodsSaleResponse();
        response.setSuccess(trySaleResult);
        return response;
    }

    @Override
    public GoodsSaleResponse saleWithoutHint(GoodsSaleRequest request) {
        GoodsTrySaleRequest collectionTrySaleRequest = new GoodsTrySaleRequest(request.getIdentifier(), request.getGoodsId(), request.getQuantity());

        GoodsType goodsType = GoodsType.valueOf(request.getGoodsType());

        Boolean trySaleResult = switch (goodsType) {
            case BLIND_BOX -> blindBoxService.saleWithoutHint(collectionTrySaleRequest);
            case COLLECTION -> collectionService.saleWithoutHint(collectionTrySaleRequest);
            default -> throw new UnsupportedOperationException(ERROR_CODE_UNSUPPORTED_GOODS_TYPE);
        };

        GoodsSaleResponse response = new GoodsSaleResponse();
        response.setSuccess(trySaleResult);
        return response;
    }


    @Override
    @Deprecated
    @SuppressWarnings("deprecation")
    public GoodsSaleResponse confirmSale(GoodsSaleRequest request) {
        GoodsConfirmSaleRequest confirmSaleRequest = new GoodsConfirmSaleRequest(request.getIdentifier(), request.getGoodsId(), request.getQuantity(), request.getBizNo(), request.getBizType(), request.getUserId(), request.getName(), request.getCover(), request.getPurchasePrice());

        GoodsType goodsType = GoodsType.valueOf(request.getGoodsType());

        return switch (goodsType) {
            case BLIND_BOX -> blindBoxService.confirmSale(confirmSaleRequest);
            case COLLECTION -> collectionService.confirmSale(confirmSaleRequest);
            default -> throw new UnsupportedOperationException(ERROR_CODE_UNSUPPORTED_GOODS_TYPE);
        };
    }

    @Override
    public GoodsSaleResponse paySuccess(GoodsSaleRequest request) {
        GoodsSaleResponse response = new GoodsSaleResponse();
        GoodsType goodsType = GoodsType.valueOf(request.getGoodsType());

        return switch (goodsType) {
            case BLIND_BOX -> {
                BlindBoxAssignRequest blindBoxAssignRequest = new BlindBoxAssignRequest();
                blindBoxAssignRequest.setBlindBoxId(request.getGoodsId());
                blindBoxAssignRequest.setUserId(request.getUserId());
                blindBoxAssignRequest.setOrderId(request.getBizNo());
                blindBoxService.assign(blindBoxAssignRequest);
                response.setSuccess(true);
                yield response;
            }
            case COLLECTION -> {
                HeldCollectionCreateRequest heldCollectionCreateRequest = new HeldCollectionCreateRequest();
                BeanUtils.copyProperties(request, heldCollectionCreateRequest);
                heldCollectionCreateRequest.setReferencePrice(request.getPurchasePrice());
                heldCollectionCreateRequest.setSerialNoBaseId(request.getGoodsId().toString());

                HeldCollection heldCollection = heldCollectionService.create(heldCollectionCreateRequest);
                response.setSuccess(true);
                response.setHeldCollectionId(heldCollection.getId());
                yield response;
            }
            default -> throw new UnsupportedOperationException(ERROR_CODE_UNSUPPORTED_GOODS_TYPE);
        };
    }

    @Override
    public GoodsSaleResponse cancelSale(GoodsSaleRequest request) {
        GoodsCancelSaleRequest goodsCancelSaleRequest = new GoodsCancelSaleRequest(request.getIdentifier(), request.getGoodsId(), request.getQuantity());

        GoodsType goodsType = GoodsType.valueOf(request.getGoodsType());

        Boolean result = switch (goodsType) {
            case BLIND_BOX -> blindBoxService.cancel(goodsCancelSaleRequest);
            case COLLECTION -> collectionService.cancel(goodsCancelSaleRequest);
            default -> throw new UnsupportedOperationException(ERROR_CODE_UNSUPPORTED_GOODS_TYPE);
        };

        GoodsSaleResponse response = new GoodsSaleResponse();
        response.setSuccess(result);
        return response;
    }

    @Override
    @Facade
    public GoodsBookResponse book(GoodsBookRequest request) {
        BaseGoodsVO goodsVO = this.getGoods(request.getGoodsId(), request.getGoodsType());
        if (goodsVO.canBookNow()) {
            return goodsBookService.book(request);
        }
        throw new RuntimeException("GOODS_CAN_NOT_BOOK_NOW");
    }

    @Override
    @Facade
    public Boolean isGoodsBooked(String goodsId, GoodsType goodsType, String buyerId) {
        return goodsBookService.isBooked(goodsId, goodsType, buyerId);
    }

    @Override
    @Facade
    public Boolean addHotGoods(String goodsId, String goodsType) {
        hotGoodsService.addHotGoods(goodsId, goodsType);
        //不抛异常就视为成功
        return true;
    }

    @Override
    @Facade
    public Boolean isHotGoods(String goodsId, String goodsType) {
        return hotGoodsService.isHotGoods(goodsId, goodsType);
    }

    @Override
    @Facade
    public List<String> getHotGoods(String goodsType) {
        return hotGoodsService.getHotGoods(goodsType);
    }
}
