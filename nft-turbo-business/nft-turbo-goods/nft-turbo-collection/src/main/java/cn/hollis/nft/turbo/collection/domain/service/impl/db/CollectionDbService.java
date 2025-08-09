package cn.hollis.nft.turbo.collection.domain.service.impl.db;

import cn.hollis.nft.turbo.api.collection.request.CollectionRemoveRequest;
import cn.hollis.nft.turbo.base.response.PageResponse;
import cn.hollis.nft.turbo.collection.domain.entity.Collection;
import cn.hollis.nft.turbo.collection.domain.service.impl.BaseCollectionService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

/**
 * 藏品服务-数据库
 *
 * @author Hollis
 */
@Service
@ConditionalOnProperty(name = "spring.elasticsearch.enable", havingValue = "false", matchIfMissing = true)
public class CollectionDbService extends BaseCollectionService {


    @Override
    public PageResponse<Collection> pageQueryByState(String keyWord, String state, int currentPage, int pageSize) {
        Page<Collection> page = new Page<>(currentPage, pageSize);
        QueryWrapper<Collection> wrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(state)) {
            wrapper.eq("state", state);
        }

        if (StringUtils.isNotBlank(keyWord)) {
            wrapper.like("name", keyWord);
        }
        wrapper.orderBy(true, false, "gmt_create");

        Page<Collection> collectionPage = this.page(page, wrapper);

        return PageResponse.of(collectionPage.getRecords(), (int) collectionPage.getTotal(), pageSize, currentPage);
    }
}
