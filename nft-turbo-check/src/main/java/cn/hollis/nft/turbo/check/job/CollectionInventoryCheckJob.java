package cn.hollis.nft.turbo.check.job;

import cn.hollis.nft.turbo.api.check.request.InventoryCheckRequest;
import cn.hollis.nft.turbo.api.check.response.InventoryCheckResponse;
import cn.hollis.nft.turbo.api.check.service.InventoryCheckFacadeService;
import cn.hollis.nft.turbo.api.goods.constant.GoodsEvent;
import cn.hollis.nft.turbo.api.goods.constant.GoodsType;
import cn.hollis.nft.turbo.api.goods.service.GoodsFacadeService;
import cn.hollis.nft.turbo.api.inventory.request.InventoryRequest;
import cn.hollis.nft.turbo.api.inventory.service.InventoryFacadeService;
import cn.hollis.nft.turbo.base.response.MultiResponse;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.annotation.XxlJob;
import org.apache.commons.lang.time.DateUtils;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

import static cn.hollis.nft.turbo.api.common.constant.CommonConstant.SEPARATOR;

/**
 * 库存一致性检查任务
 *
 * @author Hollis
 */
@Component
public class CollectionInventoryCheckJob {

    @DubboReference(version = "1.0.0")
    private InventoryFacadeService inventoryFacadeService;

    @DubboReference(version = "1.0.0")
    private GoodsFacadeService goodsFacadeService;

    @DubboReference(version = "1.0.0")
    private InventoryCheckFacadeService inventoryCheckFacadeService;

    @XxlJob("collectionInventoryCheckJob")
    public ReturnT<String> execute() {

        List<String> hotCollectionIds = goodsFacadeService.getHotGoods(GoodsType.COLLECTION.name());
        for (String hotCollectionId : hotCollectionIds) {
            InventoryRequest inventoryRequest = new InventoryRequest();
            inventoryRequest.setGoodsId(hotCollectionId);
            inventoryRequest.setGoodsType(GoodsType.COLLECTION);
            MultiResponse<String> inventoryLogs = inventoryFacadeService.getInventoryDecreaseLogs(inventoryRequest);

            for (String inventoryLog : inventoryLogs.getDatas()) {
                InventoryCheckRequest inventoryCheckRequest = new InventoryCheckRequest();
                JSONObject jsonObject = JSON.parseObject(inventoryLog);
                Date createTime = new Date(jsonObject.getLong("timestamp"));

                //只处理3秒钟之前的数据，避免出现清理后导致重复扣减
                if (DateUtils.addSeconds(createTime, 3).compareTo(new Date()) < 0) {
                    inventoryCheckRequest.setGoodsId(hotCollectionId);
                    inventoryCheckRequest.setGoodsType(GoodsType.COLLECTION);
                    inventoryCheckRequest.setGoodsEvent(GoodsEvent.TRY_SALE);
                    inventoryCheckRequest.setChangedQuantity(Integer.valueOf(jsonObject.getString("change")));
                    //因为项目过程中修改过Redisson的序列化的协议，历史代码写入的流水为 <"DECREASE_1019222537308167987200003">，优化后的新代码写入的流水为<DECREASE_1019222537308167987200003>
                    String identifier = jsonObject.getString("by");
                    //这是一坨兼容逻辑，因为项目过程中修改过Redisson的序列化的协议，所以出现两种情况
                    if (identifier.lastIndexOf("\"") != -1) {
                        inventoryCheckRequest.setIdentifier(identifier.substring(identifier.indexOf(SEPARATOR) + 1, identifier.lastIndexOf("\"")));
                    } else {
                        inventoryCheckRequest.setIdentifier(identifier.substring(identifier.indexOf(SEPARATOR) + 1));
                    }
                    InventoryCheckResponse response = inventoryCheckFacadeService.check(inventoryCheckRequest);
                    //核对一致后清除redis中的流水
                    if (response.getSuccess() && response.getCheckResult()) {
                        inventoryRequest.setIdentifier(inventoryCheckRequest.getIdentifier());
                        inventoryFacadeService.removeInventoryDecreaseLog(inventoryRequest);
                    } else {
                        //todo 告警推送
                    }
                }
            }
        }

        return ReturnT.SUCCESS;
    }
}
