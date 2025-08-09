package cn.hollis.nft.turbo.goods.infrastructure.repo;

import cn.hollis.nft.turbo.collection.domain.entity.Collection;
import cn.hollis.nft.turbo.collection.infrastructure.es.mapper.CollectionEsMapper;
import cn.hollis.nft.turbo.goods.GoodsBaseTest;
import com.alibaba.fastjson.JSON;
import org.dromara.easyes.core.biz.EsPageInfo;
import org.dromara.easyes.core.biz.SAPageInfo;
import org.dromara.easyes.core.conditions.select.LambdaEsQueryWrapper;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.*;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
import org.springframework.data.elasticsearch.core.query.Query;

import java.util.List;

public class EsCollectionRepositoryTest extends GoodsBaseTest {

    @Autowired
    private DocumentOperations documentOperations;

    @Autowired
    private SearchOperations searchOperations;

    @Autowired
    private ElasticsearchOperations elasticsearchOperations;

    @Autowired
    private CollectionEsMapper collectionEsMapper;

    @Test
    @Ignore // 测试前，请先开启es，修改base.yml中 nft.turbo.elasticsearch.enable = true
    public void test() {
        IndexOperations indexOperations = elasticsearchOperations.indexOps(Collection.class);
        Assert.assertEquals("nfturbo_collection", indexOperations.getIndexCoordinates().getIndexName());
    }

    @Test
    @Ignore
    public void testFindByNameAndState(){
        Criteria criteria = new Criteria("name").is("11").and(new Criteria[]{new Criteria("state").is("SUCCEED"),new Criteria("deleted").is("0")});
        PageRequest pageRequest = PageRequest.of(0,1);

        Query query = new CriteriaQuery(criteria).setPageable(pageRequest).addSort(Sort.by(Sort.Order.asc("collection_id")));

        SearchHits<Collection> searchHits = elasticsearchOperations.search(query, Collection.class);
        System.out.println(JSON.toJSONString(searchHits));

    }

    @Test
    @Ignore
    public void testFindByNameAndStateWithEasyEs(){
        LambdaEsQueryWrapper<Collection> queryWrapper = new LambdaEsQueryWrapper<>();
        queryWrapper.match(Collection::getName, "测试")
                .and(wrapper -> wrapper
                        .match(collection -> collection.getState().name(), "SUCCEED")
                        .match(Collection::getDeleted, "0"))
                .orderByAsc("collection_id");

        EsPageInfo<Collection> results = collectionEsMapper.pageQuery(queryWrapper, 1, 1);
        System.out.println(JSON.toJSONString(results));
    }


    @Test
    @Ignore
    public void testSearchAfter() {
        LambdaEsQueryWrapper<Collection> queryWrapper = new LambdaEsQueryWrapper<>();
        queryWrapper.match(Collection::getName, "测试")
                .and(wrapper -> wrapper
                        .match(collection -> collection.getState().name(), "SUCCEED")
                        .match(Collection::getDeleted, "0"))
                .orderByAsc("collection_id");

        SAPageInfo<Collection> saPageInfo = collectionEsMapper.searchAfterPage(queryWrapper, null, 10);
        // 第一页
        System.out.println(JSON.toJSONString(saPageInfo));

        // 获取下一页
        List<Object> nextSearchAfter = saPageInfo.getNextSearchAfter();
        SAPageInfo<Collection> next = collectionEsMapper.searchAfterPage(queryWrapper, nextSearchAfter, 10);
        System.out.println(JSON.toJSONString(next));
    }
}