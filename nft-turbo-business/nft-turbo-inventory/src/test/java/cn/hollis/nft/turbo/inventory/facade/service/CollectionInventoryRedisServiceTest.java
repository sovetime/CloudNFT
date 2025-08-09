package cn.hollis.nft.turbo.inventory.facade.service;

import cn.hollis.nft.turbo.api.inventory.request.InventoryRequest;
import cn.hollis.nft.turbo.api.inventory.service.InventoryFacadeService;
import cn.hollis.nft.turbo.base.response.SingleResponse;
import cn.hollis.nft.turbo.inventory.InventoryBaseTest;
import com.alibaba.fastjson.JSON;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Hollis
 * <p>
 * 这里依赖缓存配置，先 ignore 掉，如果增加了 redis 配置之后，可以把redissonClient的 mock 移除，再去除 ignore 即可
 */
@Ignore
public class CollectionInventoryRedisServiceTest extends InventoryBaseTest {

    @Autowired
    private InventoryFacadeService inventoryFacadeService;

    @Test
    public void init() {
        InventoryRequest request = new InventoryRequest();
        request.setGoodsId("test3211");
        request.setInventory(66);
        inventoryFacadeService.init(request);

        SingleResponse<Integer> result = inventoryFacadeService.queryInventory(request);
        Assert.assertEquals(66, (int) result.getData());
    }

    @Test
    public void decrease_concurrent() throws InterruptedException {
        InventoryRequest request = new InventoryRequest();
        request.setGoodsId("test321");
        request.setInventory(100);
        inventoryFacadeService.invalid(request);
        inventoryFacadeService.init(request);

        ExecutorService executor = Executors.newFixedThreadPool(100);
        CountDownLatch startGate = new CountDownLatch(1);
        CountDownLatch endGate = new CountDownLatch(100);

        for (int i = 0; i < 100; i++) {
            executor.submit(() -> {
                try {
                    startGate.await(); // 等待所有线程准备好
                    try {
                        InventoryRequest decreaseRequest = new InventoryRequest();
                        decreaseRequest.setGoodsId("test321");
                        decreaseRequest.setInventory(1);
                        decreaseRequest.setIdentifier(UUID.randomUUID().toString());
                        inventoryFacadeService.decrease(decreaseRequest);
                    } finally {
                        endGate.countDown(); // 告诉主线程该线程已完成
                    }
                } catch (InterruptedException ignored) {
                    Thread.currentThread().interrupt();
                }
            });
        }

        startGate.countDown(); // 启动所有等待线程
        endGate.await(); // 等待所有线程执行完毕

        SingleResponse<Integer> result = inventoryFacadeService.queryInventory(request);
        Assert.assertEquals(0, (int) result.getData());
    }

    @Test
    public void decrease_duplicated() {
        InventoryRequest request = new InventoryRequest();
        request.setGoodsId("test0321");
        request.setInventory(100);
        inventoryFacadeService.invalid(request);
        inventoryFacadeService.init(request);

        InventoryRequest decreaseRequest = new InventoryRequest();
        decreaseRequest.setGoodsId("test0321");
        decreaseRequest.setInventory(1);
        decreaseRequest.setIdentifier(UUID.randomUUID().toString());

        SingleResponse<Boolean> response = inventoryFacadeService.decrease(decreaseRequest);
        Assert.assertTrue(response.getSuccess());
        Assert.assertNull(response.getResponseCode());

        response = inventoryFacadeService.decrease(decreaseRequest);
        Assert.assertTrue(response.getSuccess());
        Assert.assertEquals(response.getResponseCode(), "OPERATION_ALREADY_EXECUTED");

        SingleResponse<Integer> result = inventoryFacadeService.queryInventory(request);
        Assert.assertEquals(99, (int) result.getData());
    }

    @Test
    public void increase_duplicated() {
        InventoryRequest request = new InventoryRequest();
        request.setGoodsId("test00321");
        request.setInventory(100);
        inventoryFacadeService.invalid(request);
        inventoryFacadeService.init(request);

        InventoryRequest decreaseRequest = new InventoryRequest();
        decreaseRequest.setGoodsId("test00321");
        decreaseRequest.setInventory(1);
        decreaseRequest.setIdentifier(UUID.randomUUID().toString());

        SingleResponse<Boolean> response = inventoryFacadeService.increase(decreaseRequest);
        Assert.assertTrue(response.getSuccess());
        Assert.assertNull(response.getResponseCode());

        response = inventoryFacadeService.increase(decreaseRequest);
        Assert.assertTrue(response.getSuccess());
        Assert.assertEquals(response.getResponseCode(), "OPERATION_ALREADY_EXECUTED");

        SingleResponse<Integer> result = inventoryFacadeService.queryInventory(request);
        Assert.assertEquals(101, (int) result.getData());
    }

    @Test
    public void increase_concurrent() throws InterruptedException {
        InventoryRequest request = new InventoryRequest();
        request.setGoodsId("test32122");
        request.setInventory(100);
        inventoryFacadeService.invalid(request);
        inventoryFacadeService.init(request);

        ExecutorService executor = Executors.newFixedThreadPool(100);
        CountDownLatch startGate = new CountDownLatch(1);
        CountDownLatch endGate = new CountDownLatch(100);

        for (int i = 0; i < 100; i++) {
            executor.submit(() -> {
                try {
                    startGate.await(); // 等待所有线程准备好
                    try {
                        InventoryRequest decreaseRequest = new InventoryRequest();
                        decreaseRequest.setGoodsId("test32122");
                        decreaseRequest.setInventory(1);
                        decreaseRequest.setIdentifier(UUID.randomUUID().toString());
                        inventoryFacadeService.increase(decreaseRequest);
                    } finally {
                        endGate.countDown(); // 告诉主线程该线程已完成
                    }
                } catch (InterruptedException ignored) {
                    Thread.currentThread().interrupt();
                }
            });
        }

        startGate.countDown(); // 启动所有等待线程
        endGate.await(); // 等待所有线程执行完毕

        SingleResponse<Integer> result = inventoryFacadeService.queryInventory(request);
        Assert.assertEquals(200, (int) result.getData());
    }

    @Test
    public void decrease_oversold() throws InterruptedException {
        InventoryRequest request = new InventoryRequest();
        request.setGoodsId("test3213");
        request.setInventory(100);
        inventoryFacadeService.invalid(request);
        inventoryFacadeService.init(request);
        request.setInventory(101);
        request.setIdentifier(UUID.randomUUID().toString());
        SingleResponse<Boolean> result = inventoryFacadeService.decrease(request);
        System.out.println(JSON.toJSONString(result));
        Assert.assertEquals(result.getResponseCode(), "INVENTORY_NOT_ENOUGH");
    }


    @Test
    public void invalid_decrease() throws InterruptedException {
        InventoryRequest request = new InventoryRequest();
        request.setGoodsId("test321366");
        request.setInventory(100);
        inventoryFacadeService.init(request);

        inventoryFacadeService.invalid(request);

        request.setInventory(1);
        request.setIdentifier(UUID.randomUUID().toString());
        SingleResponse<Boolean> result = inventoryFacadeService.decrease(request);
        System.out.println(JSON.toJSONString(result));
        Assert.assertEquals(result.getResponseCode(), "KEY_NOT_FOUND");
    }
}