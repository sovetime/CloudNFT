package cn.hollis.nft.turbo.order.sharding.strategy;

/**
 * @author Hollis
 */
public class DefaultShardingTableStrategy implements ShardingTableStrategy {

    public DefaultShardingTableStrategy() {
    }

    @Override
    public int getTable(String externalId,int tableCount) {
        int hashCode = externalId.hashCode();
        return (int) Math.abs((long) hashCode) % tableCount;
        //  为了性能更好，可以优化成：return (int) Math.abs((long) hashCode) & (tableCount - 1); 具体原理参考 hashmap 的 hash 方法
    }
}
