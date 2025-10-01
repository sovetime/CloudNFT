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


//库存一致性检查任务
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
        //获取热门商品id列表
        //本项目采用的是统计预约商品的方式确定热门商品，后续考虑采用hotKey
        List<String> hotCollectionIds = goodsFacadeService.getHotGoods(GoodsType.COLLECTION.name());

        for (String hotCollectionId : hotCollectionIds) {
            //构造库存请求
            InventoryRequest inventoryRequest = new InventoryRequest();
            inventoryRequest.setGoodsId(hotCollectionId);
            inventoryRequest.setGoodsType(GoodsType.COLLECTION);

            //批量获取库存扣减流水，根据商品id和类型进行分组，每个商品都是有很多条流水的
            MultiResponse<String> inventoryLogs = inventoryFacadeService.getInventoryDecreaseLogs(inventoryRequest);

            //根据流水进行库存核对，核对之后在删除
            for (String inventoryLog : inventoryLogs.getDatas()) {
                //构造库存校验请求
                InventoryCheckRequest inventoryCheckRequest = new InventoryCheckRequest();

                // 解析 Redis 中保存的库存流水日志（JSON 格式）
                JSONObject jsonObject = JSON.parseObject(inventoryLog);
                Date createTime = new Date(jsonObject.getLong("timestamp"));
                String action = jsonObject.getString("action");

                //只处理库存扣减的流水，库存增加的流水不处理
                //只处理 3 秒钟之前的扣减数据（避免重复扣减问题）
                if ("decrease".equals(action) && DateUtils.addSeconds(createTime, 3).compareTo(new Date()) < 0) {
                    inventoryCheckRequest.setGoodsId(hotCollectionId);
                    inventoryCheckRequest.setGoodsType(GoodsType.COLLECTION);
                    inventoryCheckRequest.setGoodsEvent(GoodsEvent.TRY_SALE);
                    //设置流水中的数量
                    inventoryCheckRequest.setChangedQuantity(Integer.valueOf(jsonObject.getString("change")));

                    String identifier = jsonObject.getString("by");
                    //设置流水中的标识符
                    inventoryCheckRequest.setIdentifier(identifier.substring(identifier.indexOf(SEPARATOR) + 1));

                    //调用库存校验服务，检查扣减流水是否和数据库一致
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
