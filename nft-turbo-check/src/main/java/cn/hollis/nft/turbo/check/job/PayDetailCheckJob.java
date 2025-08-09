package cn.hollis.nft.turbo.check.job;

import cn.hollis.nft.turbo.api.check.service.InventoryCheckFacadeService;
import cn.hollis.nft.turbo.api.goods.service.GoodsFacadeService;
import cn.hollis.nft.turbo.api.inventory.service.InventoryFacadeService;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.annotation.XxlJob;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Component;

/**
 * 支付一致性检查任务
 *
 * @author Hollis
 */
@Component
public class PayDetailCheckJob {

    @DubboReference(version = "1.0.0")
    private InventoryFacadeService inventoryFacadeService;

    @DubboReference(version = "1.0.0")
    private GoodsFacadeService goodsFacadeService;

    @DubboReference(version = "1.0.0")
    private InventoryCheckFacadeService inventoryCheckFacadeService;

    @XxlJob("payDetailCheckJob")
    public ReturnT<String> execute() {


        return ReturnT.SUCCESS;
    }
}
