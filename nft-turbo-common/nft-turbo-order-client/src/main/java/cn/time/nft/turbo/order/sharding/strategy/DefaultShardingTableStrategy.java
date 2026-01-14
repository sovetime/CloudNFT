package cn.time.nft.turbo.order.sharding.strategy;


// 默认的分表策略
public class DefaultShardingTableStrategy implements ShardingTableStrategy {

    public DefaultShardingTableStrategy() {
    }

    //将userId 路由到对应的分表
    @Override
    public int getTable(String userId, int tableCount) {
        int hashCode = userId.hashCode();
        return (int) Math.abs((long) hashCode) % tableCount;
        //  为了性能更好，可以优化成：return (int) Math.abs((long) hashCode) & (tableCount - 1); 具体原理参考 hashmap 的 hash 方法
    }
}
