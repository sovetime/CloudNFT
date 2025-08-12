package cn.hollis.nft.turbo.order.sharding.strategy;


public interface ShardingTableStrategy {

    //获取分表结果
    public int getTable(String externalId, int tableCount);
}