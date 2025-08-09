package cn.hollis.nft.turbo.chain.domain.service;

import cn.hollis.nft.turbo.chain.domain.constant.ChainOperateStateEnum;
import cn.hollis.nft.turbo.chain.domain.entity.ChainOperateInfo;
import cn.hollis.nft.turbo.chain.infrastructure.mapper.ChainOperateInfoMapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;

/**
 * @author wswyb001
 * @date 2024/01/19
 */
@Service
public class ChainOperateInfoService extends ServiceImpl<ChainOperateInfoMapper, ChainOperateInfo> {

    @Autowired
    private ChainOperateInfoMapper chainOperateInfoMapper;

    public Long insertInfo(String chainType, String bizId, String bizType, String operateType, String param,String operationId) {
        ChainOperateInfo operateInfo = new ChainOperateInfo();
        operateInfo.setOperateTime(new Date());
        operateInfo.setChainType(chainType);
        operateInfo.setBizId(bizId);
        operateInfo.setBizType(bizType);
        operateInfo.setOperateType(operateType);
        operateInfo.setParam(param);
        operateInfo.setOutBizId(operationId);
        operateInfo.setState(ChainOperateStateEnum.PROCESSING);

        boolean ret = save(operateInfo);
        if (ret) {
            return operateInfo.getId();
        }
        return null;
    }

    public boolean updateResult(Long id, ChainOperateStateEnum state, String result) {
        ChainOperateInfo operateInfoDO = getById(id);
        operateInfoDO.setResult(result);
        operateInfoDO.setState(state);
        return updateById(operateInfoDO);
    }

    public ChainOperateInfo queryByOutBizId(String bizId, String bizType, String outBizId) {
        QueryWrapper<ChainOperateInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("biz_id", bizId);
        queryWrapper.eq("biz_type", bizType);
        queryWrapper.eq("out_biz_id", outBizId);
        List<ChainOperateInfo> retList = list(queryWrapper);
        if (CollectionUtils.isEmpty(retList)) {
            return null;
        }
        return retList.get(0);
    }

    public List<ChainOperateInfo> pageQueryOperateInfoByState(String state, int pageSize,Long minId) {
        QueryWrapper<ChainOperateInfo> wrapper = new QueryWrapper<>();
        wrapper.eq("state", state);
        wrapper.orderBy(true, true, "gmt_create");
        wrapper.ge("id", minId);
        wrapper.last("limit " + pageSize);
        return this.list(wrapper);
    }

    public Long queryMinIdByState(String state) {
        return chainOperateInfoMapper.queryMinIdByState(state);
    }

    public void delete(Long id) {
        removeById(id);
    }

}
