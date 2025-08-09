package cn.hollis.nft.turbo.goods.domain.service;

import cn.hollis.nft.turbo.api.box.constant.BlindAllotBoxRule;
import cn.hollis.nft.turbo.api.box.constant.BlindBoxItemStateEnum;
import cn.hollis.nft.turbo.api.box.constant.BlindBoxStateEnum;
import cn.hollis.nft.turbo.api.box.request.BlindBoxCreateRequest;
import cn.hollis.nft.turbo.api.box.request.BlindBoxItemCreateRequest;
import cn.hollis.nft.turbo.api.box.response.BlindBoxCreateResponse;
import cn.hollis.nft.turbo.api.box.service.BlindBoxManageFacadeService;
import cn.hollis.nft.turbo.api.chain.response.ChainProcessResponse;
import cn.hollis.nft.turbo.api.chain.response.data.ChainOperationData;
import cn.hollis.nft.turbo.api.collection.constant.GoodsSaleBizType;
import cn.hollis.nft.turbo.api.collection.model.HeldCollectionVO;
import cn.hollis.nft.turbo.api.collection.response.BlindBoxCollectionSaleResponse;
import cn.hollis.nft.turbo.api.goods.request.GoodsConfirmSaleRequest;
import cn.hollis.nft.turbo.api.goods.request.GoodsTrySaleRequest;
import cn.hollis.nft.turbo.api.goods.response.GoodsSaleResponse;
import cn.hollis.nft.turbo.api.user.response.UserQueryResponse;
import cn.hollis.nft.turbo.api.user.response.data.UserInfo;
import cn.hollis.nft.turbo.base.response.SingleResponse;
import cn.hollis.nft.turbo.box.domain.entity.BlindBox;
import cn.hollis.nft.turbo.box.domain.service.BlindBoxItemService;
import cn.hollis.nft.turbo.box.domain.service.BlindBoxService;
import cn.hollis.nft.turbo.collection.domain.entity.HeldCollection;
import cn.hollis.nft.turbo.collection.domain.service.impl.HeldCollectionService;
import cn.hollis.nft.turbo.goods.GoodsBaseTest;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class BlindBoxItemServiceTest extends GoodsBaseTest {
    @Autowired
    private BlindBoxItemService blindBoxItemService;
    @Autowired
    private BlindBoxManageFacadeService blindBoxManageFacadeService;
    @Autowired
    private BlindBoxService blindBoxService;
    @MockBean
    private HeldCollectionService heldCollectionService;

    @Test
    public void openTest() {
        BlindBoxCreateRequest request = new BlindBoxCreateRequest();
        request.setName("blindName");
        request.setQuantity(10L);
        request.setCover("blindCover");
        request.setDetail("blindDetail");
        request.setPrice(BigDecimal.TEN);
        request.setIdentifier(UUID.randomUUID().toString());
        request.setCreatorId("123456");
        request.setCreateTime(new Date());
        request.setSaleTime(new Date());
        List<BlindBoxItemCreateRequest> boxItemCreateRequests = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            BlindBoxItemCreateRequest blindBoxItemCreateRequest = new BlindBoxItemCreateRequest();
            blindBoxItemCreateRequest.setCollectionCover("cover" + i);
            blindBoxItemCreateRequest.setQuantity(5L);
            blindBoxItemCreateRequest.setCollectionDetail("detail" + i);
            boxItemCreateRequests.add(blindBoxItemCreateRequest);
        }
        request.setBlindBoxItemCreateRequests(boxItemCreateRequests);
        request.setAllocateRule(BlindAllotBoxRule.RANDOM.name());
        ChainProcessResponse<ChainOperationData> chainProcessResponse = new ChainProcessResponse<>();
        chainProcessResponse.setSuccess(true);
        when(chainFacadeService.chain(any())).thenReturn(chainProcessResponse);
        BlindBoxCreateResponse response = blindBoxManageFacadeService.create(request);
        Assert.assertTrue(response.getSuccess());
        BlindBox blindBox = blindBoxService.getById(response.getBlindBoxId());
        Assert.assertEquals(blindBox.getName(), "blindName");

        blindBox.setState(BlindBoxStateEnum.SUCCEED);
        blindBox.setSyncChainTime(new Date());
        blindBoxService.updateById(blindBox);


        GoodsTrySaleRequest collectionTrySaleRequest = new GoodsTrySaleRequest("test123", blindBox.getId(), 1);
        boolean tryRes = blindBoxService.sale(collectionTrySaleRequest);
        Assert.assertTrue(tryRes);
        var queRes = blindBoxService.getById(blindBox.getId());
        Assert.assertTrue(queRes.getSaleableInventory() == 9L);
        GoodsConfirmSaleRequest collectionSaleConfirm = new GoodsConfirmSaleRequest("676776", blindBox.getId(), 1, "23123", GoodsSaleBizType.BLIND_BOX_TRADE.name(), "321321", "name", "cover", BigDecimal.ONE);
        GoodsSaleResponse confirmRes = blindBoxService.confirmSale(collectionSaleConfirm);
        queRes = blindBoxService.getById(blindBox.getId());
        Assert.assertTrue(queRes.getOccupiedInventory() == 1L);

        var items = blindBoxItemService.queryListByBoxIdAndState(blindBox.getId(), BlindBoxItemStateEnum.ASSIGNED.name());
        BlindBoxCollectionSaleResponse blindBoxCollectionSaleResponse = new BlindBoxCollectionSaleResponse();
        blindBoxCollectionSaleResponse.setSuccess(true);
        blindBoxCollectionSaleResponse.setHeldCollectionId(11L);
//        when(collectionFacadeService.blindBoxCollectionSale(any())).thenReturn(blindBoxCollectionSaleResponse);
        SingleResponse<HeldCollectionVO> heldCollectionVOSingleResponse = new SingleResponse<>();
        HeldCollectionVO heldCollectionVO = new HeldCollectionVO();
        heldCollectionVO.setId("1");
        heldCollectionVOSingleResponse.setData(heldCollectionVO);
        when(collectionReadFacadeService.queryHeldCollectionById(any())).thenReturn(heldCollectionVOSingleResponse);
        UserQueryResponse<UserInfo> userQueryResponse = new UserQueryResponse<>();
        UserInfo userInfo = new UserInfo();
        userInfo.setBlockChainUrl("url");
        userQueryResponse.setData(userInfo);
        when(userFacadeService.query(any())).thenReturn(userQueryResponse);

        when(chainFacadeService.mint(any())).thenReturn(chainProcessResponse);
        HeldCollection heldCollection = new HeldCollection();
        heldCollection.setId(1L);
        heldCollection.setName("testName");
        heldCollection.setSerialNo("12345");
        when(heldCollectionService.create(any())).thenReturn(heldCollection);
        blindBoxItemService.open(items.get(0));
        var queryItemRes = blindBoxItemService.queryById(items.get(0).getId());
        Assert.assertEquals(queryItemRes.getState(), BlindBoxItemStateEnum.OPENING);

    }

}
