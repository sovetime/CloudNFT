package cn.hollis.nft.turbo.order.infrastructure.sharding.algorithm;

import cn.hollis.nft.turbo.order.sharding.id.DistributeID;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.shardingsphere.sharding.api.sharding.complex.ComplexKeysShardingAlgorithm;
import org.apache.shardingsphere.sharding.api.sharding.complex.ComplexKeysShardingValue;
import org.apache.shardingsphere.sharding.api.sharding.hint.HintShardingAlgorithm;
import org.apache.shardingsphere.sharding.api.sharding.hint.HintShardingValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.HashSet;
import java.util.Properties;
import java.util.stream.Collectors;

import static cn.hollis.nft.turbo.api.common.constant.CommonConstant.SEPARATOR;

/**
 * @author Hollis
 */
public class TurboKeyShardingAlgorithm implements ComplexKeysShardingAlgorithm<String>, HintShardingAlgorithm<String> {

    private static Logger logger = LoggerFactory.getLogger(TurboKeyShardingAlgorithm.class);

    private Properties props;

    private static final String PROP_MAIN_COLUM = "mainColum";

    private static final String PROP_TABLE_COUNT = "tableCount";

    @Override
    public Properties getProps() {
        return props;
    }

    @Override
    public void init(Properties props) {
        this.props = props;
    }

    @Override
    public Collection<String> doSharding(Collection<String> availableTargetNames, ComplexKeysShardingValue<String> complexKeysShardingValue) {
        Collection<String> result = new HashSet<>();

        String mainColum = props.getProperty(PROP_MAIN_COLUM);
        // 获取分片键的值
        Collection<String> mainColums = complexKeysShardingValue.getColumnNameAndShardingValuesMap().get(mainColum);

        if (CollectionUtils.isNotEmpty(mainColums)) {
            for (String colum : mainColums) {
                String shardingTarget = calculateShardingTarget(colum);
                result.add(shardingTarget);
            }
            return getMatchedTables(result, availableTargetNames);
        }

        complexKeysShardingValue.getColumnNameAndShardingValuesMap().remove(mainColum);
        Collection<String> otherColums = complexKeysShardingValue.getColumnNameAndShardingValuesMap().keySet();
        if (CollectionUtils.isNotEmpty(otherColums)) {
            for (String colum : otherColums) {
                Collection<String> otherColumValues = complexKeysShardingValue.getColumnNameAndShardingValuesMap().get(colum);
                for (String value : otherColumValues) {
                    String shardingTarget = extractShardingTarget(value);
                    result.add(shardingTarget);
                }
            }
            return getMatchedTables(result, availableTargetNames);
        }

        return null;
    }

    private Collection<String> getMatchedTables(Collection<String> results, Collection<String> availableTargetNames) {
        Collection<String> matchedTables = new HashSet<>();
        for (String result : results) {
            matchedTables.addAll(availableTargetNames.parallelStream().filter(each -> each.endsWith(result)).collect(Collectors.toSet()));
        }
        return matchedTables;
    }

    private String extractShardingTarget(String orderId) {
        return DistributeID.getShardingTable(orderId);
    }

    private String calculateShardingTarget(String buyerId) {
        String tableCount = props.getProperty(PROP_TABLE_COUNT);
        return DistributeID.getShardingTable(buyerId, Integer.parseInt(tableCount));
    }

    @Override
    public Collection<String> doSharding(Collection<String> collection, HintShardingValue<String> hintShardingValue) {
        logger.info("collection : " + collection);
        logger.info("hintShardingValue : " + hintShardingValue);
        String logicTableName = hintShardingValue.getLogicTableName();
        Collection<String> shardingTargets = hintShardingValue.getValues();

        Collection<String> matchedTables = new HashSet<>();
        for (String shardingTarget : shardingTargets) {
            matchedTables.add(logicTableName + SEPARATOR + shardingTarget);
        }

        logger.info("matchedTables : " + matchedTables);
        return CollectionUtils.intersection(collection, matchedTables);
    }
}
