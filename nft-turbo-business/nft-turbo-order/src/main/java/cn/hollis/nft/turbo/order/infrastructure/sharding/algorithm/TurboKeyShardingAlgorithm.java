package cn.hollis.nft.turbo.order.infrastructure.sharding.algorithm;

import cn.hollis.nft.turbo.order.sharding.id.DistributeID;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.shardingsphere.sharding.api.sharding.complex.ComplexKeysShardingAlgorithm;
import org.apache.shardingsphere.sharding.api.sharding.complex.ComplexKeysShardingValue;
import org.apache.shardingsphere.sharding.api.sharding.hint.HintShardingAlgorithm;
import org.apache.shardingsphere.sharding.api.sharding.hint.HintShardingValue;
import java.util.Collection;
import java.util.HashSet;
import java.util.Properties;
import java.util.stream.Collectors;

import static cn.hollis.nft.turbo.api.common.constant.CommonConstant.SEPARATOR;



// 自定义分片算法
// 实现 ComplexKeysShardingAlgorithm：支持多分片键（buyer_id、order_id）
// 分片逻辑
// 1. 优先使用主分片列（buyer_id）进行分表，规则：buyerId % tableCount。
// 2. 如果没有 buyer_id，但有 order_id，则解析 orderId 得到分表号（因为 orderId 已绑定 buyerId 信息）
// 3. 如果两者都没有，返回 null（交给审计器拦截或全表扫）。
@Slf4j
public class TurboKeyShardingAlgorithm implements ComplexKeysShardingAlgorithm<String>, HintShardingAlgorithm<String> {

    private Properties props;

    // 主分片列
    private static final String PROP_MAIN_COLUM = "mainColum";

    // 分表数量
    private static final String PROP_TABLE_COUNT = "tableCount";

    @Override
    public Properties getProps() {
        return props;
    }

    @Override
    public void init(Properties props) {
        this.props = props;
    }

    //复杂分片算法（支持 buyer_id、order_id）
    @Override
    public Collection<String> doSharding(Collection<String> availableTargetNames, ComplexKeysShardingValue<String> complexKeysShardingValue) {
        Collection<String> result = new HashSet<>();

        //读取配置中的主分片列
        String mainColum = props.getProperty(PROP_MAIN_COLUM);
        // 获取分片键的值
        Collection<String> mainColums = complexKeysShardingValue.getColumnNameAndShardingValuesMap().get(mainColum);

        //如果SQL 中包含 buyer_id，则优先按 buyer_id 分表
        if (CollectionUtils.isNotEmpty(mainColums)) {
            for (String colum : mainColums) {
                //buyerId % tableCount 得到分表号
                String shardingTarget = calculateShardingTarget(colum);
                result.add(shardingTarget);
            }
            return getMatchedTables(result, availableTargetNames);
        }

        // 如果SQL 没有buyer_id，使用order_id
        complexKeysShardingValue.getColumnNameAndShardingValuesMap().remove(mainColum);
        Collection<String> otherColums = complexKeysShardingValue.getColumnNameAndShardingValuesMap().keySet();
        if (CollectionUtils.isNotEmpty(otherColums)) {
            for (String colum : otherColums) {
                Collection<String> otherColumValues = complexKeysShardingValue.getColumnNameAndShardingValuesMap().get(colum);
                for (String value : otherColumValues) {
                    // 从 orderId 中解析分表号（orderId 已包含 buyerId）
                    String shardingTarget = extractShardingTarget(value);
                    result.add(shardingTarget);
                }
            }
            return getMatchedTables(result, availableTargetNames);
        }

        return null;
    }

    // 匹配实际存在的目标表（根据分表号后缀匹配）
    private Collection<String> getMatchedTables(Collection<String> results, Collection<String> availableTargetNames) {
        Collection<String> matchedTables = new HashSet<>();
        for (String result : results) {
            matchedTables.addAll(availableTargetNames.parallelStream().filter(each -> each.endsWith(result)).collect(Collectors.toSet()));
        }
        return matchedTables;
    }

    // 从orderId 中解析出分表号
    private String extractShardingTarget(String orderId) {
        return DistributeID.getShardingTable(orderId);
    }

    // 根据buyerId 计算分表号：buyerId % tableCount
    private String calculateShardingTarget(String buyerId) {
        String tableCount = props.getProperty(PROP_TABLE_COUNT);
        return DistributeID.getShardingTable(buyerId, Integer.parseInt(tableCount));
    }

    // hint分片算法
    @Override
    public Collection<String> doSharding(Collection<String> collection, HintShardingValue<String> hintShardingValue) {
        log.info("collection : " + collection);
        log.info("hintShardingValue : " + hintShardingValue);
        String logicTableName = hintShardingValue.getLogicTableName();
        Collection<String> shardingTargets = hintShardingValue.getValues();

        Collection<String> matchedTables = new HashSet<>();
        for (String shardingTarget : shardingTargets) {
            matchedTables.add(logicTableName + SEPARATOR + shardingTarget);
        }

        log.info("matchedTables : " + matchedTables);
        return CollectionUtils.intersection(collection, matchedTables);
    }
}
