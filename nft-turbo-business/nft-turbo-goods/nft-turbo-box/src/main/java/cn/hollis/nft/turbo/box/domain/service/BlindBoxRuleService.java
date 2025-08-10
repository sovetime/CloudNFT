package cn.hollis.nft.turbo.box.domain.service;

import cn.hollis.nft.turbo.box.domain.request.BlindBoxBindMatchRequest;


//盲盒分配服务
public interface BlindBoxRuleService {

    //按照规则进行匹配
    Long match(BlindBoxBindMatchRequest request);
}
