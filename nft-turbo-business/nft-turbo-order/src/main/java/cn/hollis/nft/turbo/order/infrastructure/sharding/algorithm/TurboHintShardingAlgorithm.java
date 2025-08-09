package cn.hollis.nft.turbo.order.infrastructure.sharding.algorithm;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.shardingsphere.sharding.api.sharding.hint.HintShardingAlgorithm;
import org.apache.shardingsphere.sharding.api.sharding.hint.HintShardingValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.HashSet;
import java.util.Properties;

import static cn.hollis.nft.turbo.api.common.constant.CommonConstant.SEPARATOR;

/**
 * @author Hollis
 */
public class TurboHintShardingAlgorithm implements HintShardingAlgorithm<String> {
    private Properties props;

    private static final Logger LOG = LoggerFactory.getLogger(TurboHintShardingAlgorithm.class);

    @Override
    public Collection<String> doSharding(Collection<String> collection, HintShardingValue<String> hintShardingValue) {
        String logicTableName = hintShardingValue.getLogicTableName();
        Collection<String> shardingTargets = hintShardingValue.getValues();

        Collection<String> matchedTables = new HashSet<>();
        for (String shardingTarget : shardingTargets) {
            matchedTables.add(logicTableName + SEPARATOR + shardingTarget);
        }

        LOG.info("matchedTables : " + matchedTables);
        return CollectionUtils.intersection(collection, matchedTables);
    }

    @Override
    public Properties getProps() {
        return props;
    }

    @Override
    public void init(Properties props) {
        this.props = props;
    }
}
