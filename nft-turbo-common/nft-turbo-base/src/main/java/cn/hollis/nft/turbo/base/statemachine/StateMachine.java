package cn.hollis.nft.turbo.base.statemachine;

public interface StateMachine<STATE, EVENT> {

    //状态机转移
    public STATE transition(STATE state, EVENT event);
}

