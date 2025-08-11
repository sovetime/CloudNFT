package cn.hollis.nft.turbo.goods.domain.service;

import cn.hollis.nft.turbo.api.collection.constant.GoodsSaleBizType;
import cn.hollis.nft.turbo.api.collection.constant.HeldCollectionState;
import cn.hollis.nft.turbo.api.collection.request.CollectionCreateRequest;
import cn.hollis.nft.turbo.collection.domain.entity.Collection;
import cn.hollis.nft.turbo.collection.domain.request.HeldCollectionActiveRequest;
import cn.hollis.nft.turbo.collection.domain.request.HeldCollectionCreateRequest;
import cn.hollis.nft.turbo.collection.domain.request.HeldCollectionDestroyRequest;
import cn.hollis.nft.turbo.collection.domain.request.HeldCollectionTransferRequest;
import cn.hollis.nft.turbo.collection.domain.service.CollectionService;
import cn.hollis.nft.turbo.collection.domain.service.impl.HeldCollectionService;
import cn.hollis.nft.turbo.goods.GoodsBaseTest;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.Date;

public class HeldCollectionServiceTest extends GoodsBaseTest {
    @Autowired
    private HeldCollectionService heldCollectionService;

    @Autowired
    private CollectionService collectionService;

    @Test
    public void serviceTest() {
        CollectionCreateRequest request = new CollectionCreateRequest();
        request.setIdentifier("123456");
        request.setName("name");
        request.setCover("cover");
        request.setPrice(BigDecimal.ONE);
        request.setQuantity(100L);
        request.setCreateTime(new Date());
        request.setSaleTime(new Date());
        Collection collection = collectionService.create(request);
        Assert.assertNotNull(collection.getId());

        //create
        HeldCollectionCreateRequest mintRequest = new HeldCollectionCreateRequest();
        mintRequest.setGoodsId(collection.getId());
        mintRequest.setIdentifier("123");
        mintRequest.setSerialNo("12345");
        mintRequest.setBizType(GoodsSaleBizType.PRIMARY_TRADE.name());
        mintRequest.setUserId("1");
        var heldCollection = heldCollectionService.create(mintRequest);
        Assert.assertNotNull(heldCollection.getId());
        Assert.assertSame(heldCollection.getState(), HeldCollectionState.INIT);

        // active
        HeldCollectionActiveRequest heldCollectionActiveRequest = new HeldCollectionActiveRequest();
        heldCollectionActiveRequest.setHeldCollectionId(heldCollection.getId().toString());
        heldCollectionActiveRequest.setIdentifier(heldCollection.getId().toString());
        heldCollectionActiveRequest.setNftId(heldCollection.getId().toString());
        heldCollectionActiveRequest.setTxHash(heldCollection.getId().toString());
        heldCollectionService.active(heldCollectionActiveRequest);
        // transfer
        HeldCollectionTransferRequest transferRequest = new HeldCollectionTransferRequest();
        transferRequest.setHeldCollectionId(heldCollection.getId().toString());
        transferRequest.setIdentifier("345");
        transferRequest.setRecipientUserId("2");
        transferRequest.setOperatorId("1");
        var newHeldCollection = heldCollectionService.transfer(transferRequest);
        Assert.assertNotNull(newHeldCollection.getId());
        Assert.assertSame(newHeldCollection.getState(), HeldCollectionState.INIT);
        var oldHeldCollection = heldCollectionService.queryByCollectionIdAndSerialNo(heldCollection.getCollectionId(),
                heldCollection.getSerialNo());
        Assert.assertSame(oldHeldCollection.getState(), HeldCollectionState.INACTIVED);
        // destroy
        HeldCollectionDestroyRequest destroyRequest = new HeldCollectionDestroyRequest();
        destroyRequest.setHeldCollectionId(newHeldCollection.getId().toString());
        destroyRequest.setIdentifier("456");
        destroyRequest.setOperatorId(transferRequest.getRecipientUserId());
        var destroyHeldCollection = heldCollectionService.destroy(destroyRequest);
        Assert.assertNotNull(destroyHeldCollection.getId());
        Assert.assertSame(destroyHeldCollection.getState(), HeldCollectionState.DESTROYING);
    }
}
