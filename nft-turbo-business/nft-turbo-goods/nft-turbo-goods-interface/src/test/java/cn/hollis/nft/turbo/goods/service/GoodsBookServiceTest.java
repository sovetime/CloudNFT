package cn.hollis.nft.turbo.goods.service;

import cn.hollis.nft.turbo.api.goods.constant.GoodsType;
import cn.hollis.nft.turbo.api.goods.request.GoodsBookRequest;
import cn.hollis.nft.turbo.api.goods.response.GoodsBookResponse;
import cn.hollis.nft.turbo.api.user.constant.UserType;
import cn.hollis.nft.turbo.goods.GoodsBaseTest;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class GoodsBookServiceTest extends GoodsBaseTest {
    @Autowired
    private GoodsBookService goodsBookService;
    @Test
    public void bookTest() {
        GoodsBookRequest bookRequest = new GoodsBookRequest();
        bookRequest.setGoodsId("1");
        bookRequest.setGoodsType(GoodsType.BLIND_BOX);
        bookRequest.setIdentifier("1234567");
        bookRequest.setBuyerId("123");
        bookRequest.setBuyerType(UserType.CUSTOMER);
        GoodsBookResponse response=goodsBookService.book(bookRequest);
        Assert.assertTrue(response.getBookId() != null);

    }
}
