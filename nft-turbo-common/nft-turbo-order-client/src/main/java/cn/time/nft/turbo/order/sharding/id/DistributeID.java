package cn.time.nft.turbo.order.sharding.id;

import cn.hutool.core.util.IdUtil;
import cn.time.nft.turbo.api.common.constant.BusinessCode;
import cn.time.nft.turbo.order.sharding.strategy.DefaultShardingTableStrategy;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;



//分布式ID
@NoArgsConstructor
@ToString
public class DistributeID {

    //系统标识码
    private String businessCode;

    //表下标
    private String table;

    //序列号
    private String seq;

    //分表策略
    private static DefaultShardingTableStrategy shardingTableStrategy = new DefaultShardingTableStrategy();

    //生成分布式唯一Id
    public static String generateWithDefaultWorkerId(BusinessCode businessCode, String userId) {
        return generateWithSnowflake(businessCode, WorkerIdHolder.WORKER_ID, userId);
    }

    //生成唯一Id，订单类型 + workerId + 表下标
    public static String generateWithSnowflake(BusinessCode businessCode, long workerId, String userId) {
        //workerId是全局唯一的，通过雪花算法来生成分布式唯一Id
        long id = IdUtil.getSnowflake(workerId).nextId();
        //因为这里做了分库分表，需要进行表的映射
        return generate(businessCode, id, userId);
    }

    //生成一个唯一ID：订单类型 + 序列号 + 表下标
    //sequenceNumber-> 雪花算法生成Id
    public static String generate(BusinessCode businessCode, Long sequenceNumber,String userId) {
        DistributeID distributeId = create(businessCode, sequenceNumber, userId);
        return distributeId.businessCode + distributeId.seq + distributeId.table;
    }

    // businessCode-> 订单类型，sequenceNumber->分布式id
    public static DistributeID create(BusinessCode businessCode, Long sequenceNumber, String userId) {
        DistributeID distributeId = new DistributeID();
        distributeId.businessCode = businessCode.getCodeString();
        //根据userId 路由到对应的分表
        String table = String.valueOf(shardingTableStrategy.getTable(userId, businessCode.tableCount()));
        //表下表生成，0001、0002、0003、0004
        distributeId.table = StringUtils.leftPad(table, 4, "0");
        //设置序列号
        distributeId.seq = String.valueOf(sequenceNumber);
        return distributeId;
    }


    public static String getShardingTable(DistributeID distributeId){
        return distributeId.table;
    }

    public static String getShardingTable(String externalId, int tableCount) {
        return StringUtils.leftPad(String.valueOf(shardingTableStrategy.getTable(externalId, tableCount)), 4, "0");
    }

    public static String getShardingTable(String id){
        return getShardingTable(valueOf(id));
    }

    public static DistributeID valueOf(String id) {
        DistributeID distributeId = new DistributeID();
        distributeId.businessCode = id.substring(0, 2);
        distributeId.seq = id.substring(2, id.length() - 4);
        distributeId.table = id.substring(id.length() - 4, id.length());
        return distributeId;
    }
}
