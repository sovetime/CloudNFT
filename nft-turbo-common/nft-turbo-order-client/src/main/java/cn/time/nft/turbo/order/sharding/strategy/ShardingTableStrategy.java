package cn.time.nft.turbo.order.sharding.strategy;


public interface ShardingTableStrategy {

    //将userId 路由到对应的分表
    public int getTable(String userId, int tableCount);

}