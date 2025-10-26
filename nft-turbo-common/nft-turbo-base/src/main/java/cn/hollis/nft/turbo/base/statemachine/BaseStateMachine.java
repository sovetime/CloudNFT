package cn.hollis.nft.turbo.base.statemachine;

import cn.hollis.nft.turbo.base.exception.BizException;
import com.google.common.base.Joiner;
import com.google.common.collect.Maps;

import java.util.Map;

import static cn.hollis.nft.turbo.base.exception.BizErrorCode.STATE_MACHINE_TRANSITION_FAILED;


//基础状态机实现类
public class BaseStateMachine<STATE, EVENT> implements StateMachine<STATE, EVENT> {

    //状态转换映射表
    private Map<String, STATE> stateTransitions = Maps.newHashMap();

    //添加状态转换规则
    protected void putTransition(STATE origin, EVENT event, STATE target) {
        //将orgin和event 用 _ 连接起来，作为key存储
        //value 则是target
        stateTransitions.put(Joiner.on("_").join(origin, event), target);
    }

    //执行状态转换
    @Override
    public STATE transition(STATE state, EVENT event) {
        // 根据当前状态和事件查找目标状态
        STATE target = stateTransitions.get(Joiner.on("_").join(state, event));
        //没有对应的规则抛出异常
        if (target == null) {
            throw new BizException("state = " + state + " , event = " + event, STATE_MACHINE_TRANSITION_FAILED);
        }
        return target;
    }
}
