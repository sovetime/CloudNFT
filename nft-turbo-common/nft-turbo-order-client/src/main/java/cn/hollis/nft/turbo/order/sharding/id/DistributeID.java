package cn.hollis.nft.turbo.order.sharding.id;
import cn.hollis.nft.turbo.api.common.constant.BusinessCode;
import cn.hollis.nft.turbo.order.sharding.strategy.DefaultShardingTableStrategy;
import cn.hutool.core.util.IdUtil;
import org.apache.commons.lang3.StringUtils;



//分布式ID
public class DistributeID {

    //系统标识码
    private String businessCode;
    //表下标
    private String table;
    //序列号
    private String seq;
    //分表策略
    private static DefaultShardingTableStrategy shardingTableStrategy = new DefaultShardingTableStrategy();

    public DistributeID() {
    }

    //利用雪花算法生成一个唯一ID
    public static String generateWithSnowflake(BusinessCode businessCode, long workerId, String externalId) {
        //
        long id = IdUtil.getSnowflake(workerId).nextId();
        return generate(businessCode, externalId, id);
    }

    //生成一个唯一ID：10（业务码） 1769649671860822016（sequence) 1023(分表）
    public static String generate(BusinessCode businessCode,
                                  String externalId, Long sequenceNumber) {
        //
        DistributeID distributeId = create(businessCode, externalId, sequenceNumber);
        return distributeId.businessCode + distributeId.seq + distributeId.table;
    }

    @Override
    public String toString() {
        return this.businessCode + this.seq + this.table;
    }

    //创建分布式ID对象
    // businessCode 业务代码，用于确定业务类型和表数量
    //externalId 外部ID，用于分表策略计算
    //sequenceNumber 序列号，用于生成唯一标识
    public static DistributeID create(BusinessCode businessCode, String externalId, Long sequenceNumber) {

        DistributeID distributeId = new DistributeID();
        distributeId.businessCode = businessCode.getCodeString();

        // 根据分表策略计算表名
        String table = String.valueOf(shardingTableStrategy.getTable(externalId, businessCode.tableCount()));
        // 格式化为4位数字字符串
        distributeId.table = StringUtils.leftPad(table, 4, "0");
        // 设置序列号
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
