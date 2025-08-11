package cn.hollis.nft.turbo.collection.domain.service.impl.es;

import cn.hollis.nft.turbo.base.response.PageResponse;
import cn.hollis.nft.turbo.collection.domain.entity.Collection;
import cn.hollis.nft.turbo.collection.domain.service.impl.BaseCollectionService;
import cn.hollis.nft.turbo.collection.infrastructure.es.mapper.CollectionEsMapper;
import com.google.common.collect.ImmutableList;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.dromara.easyes.core.biz.SAPageInfo;
import org.dromara.easyes.core.conditions.select.LambdaEsQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Service;


//藏品服务--Elasticsearch实现
@Service
//检查application.yml配置文件属性，根据对应属性值走数据库/es
@ConditionalOnProperty(name = "spring.elasticsearch.enable", havingValue = "true")
public class CollectionEsService extends BaseCollectionService {

    //Spring Data Elasticsearch提供的核心操作接口，用于执行各种Elasticsearch操作，如索引、搜索、删除等
    //新版本的ElasticsearchRepository是对其的封装，提供类似crud操作
    //但是没有办法解决复杂查询（深度分页等）
    @Autowired
    private ElasticsearchOperations elasticsearchOperations;

    @Resource
    private CollectionEsMapper collectionEsMapper;

    //分页查询，根据名称和状态条件进行分页查询
    @Override
    public PageResponse<Collection> pageQueryByState(String name, String state, int currentPage, int pageSize) {
        //Criteria 是spring data elasticsearch 提供用于构建查询条件的类，可以构建复杂查询
        Criteria criteria = null;
        if (StringUtils.isNotBlank(name)) {
            criteria = new Criteria("name").is(name).and(new Criteria("state").is(state), new Criteria("deleted").is("0"));
        } else if ((StringUtils.isNotBlank(state))) {
            criteria = new Criteria("state").is(state).and(new Criteria("deleted").is("0"));
        } else {
            criteria = new Criteria("deleted").is("0");
        }

        // 构建分页请求和查询对象，按创建时间降序排列
        PageRequest pageRequest = PageRequest.of(currentPage - 1, pageSize);
        Query query = new CriteriaQuery(criteria).setPageable(pageRequest)
                .addSort(Sort.by(Sort.Order.desc("create_time")));
        // 执行查询并获取查询结果
        SearchHits<Collection> searchHits = elasticsearchOperations.search(query, Collection.class);

        // 将查询结果转换为分页响应对象并返回
        return PageResponse.of(searchHits.getSearchHits().stream().map(SearchHit::getContent).toList(), (int) searchHits.getTotalHits(), pageSize, currentPage);
    }


    //深度分页查询
    public SAPageInfo<Collection> deepPageQueryByState(String name, String state, int pageSize, Long lastId) {
        LambdaEsQueryWrapper<Collection> queryWrapper = new LambdaEsQueryWrapper<>();
        queryWrapper.match(Collection::getName, name)
                .and(wrapper -> wrapper
                        .match(collection -> collection.getState().name(), state)
                        .match(Collection::getDeleted, "0"))
                .orderByAsc("create_time");

        SAPageInfo<Collection> saPageInfo;
        if (lastId == null) {
            saPageInfo = collectionEsMapper.searchAfterPage(queryWrapper, null, pageSize);
        } else {
            saPageInfo = collectionEsMapper.searchAfterPage(queryWrapper, ImmutableList.of(lastId), 10);
        }
        return saPageInfo;
    }
}
