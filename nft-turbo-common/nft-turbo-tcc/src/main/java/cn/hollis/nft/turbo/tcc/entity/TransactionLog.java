package cn.hollis.nft.turbo.tcc.entity;

import cn.hollis.nft.turbo.datasource.domain.entity.BaseEntity;
import cn.hollis.nft.turbo.tcc.request.TccRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TransactionLog extends BaseEntity {

    //事务ID
    private String transactionId;
    //业务场景
    private String businessScene;
    //业务模块
    private String businessModule;
    //状态
    private TransActionLogState state;

    // Cancel的类型
    private TransCancelSuccessType cancelType;

    public TransactionLog(TccRequest tccRequest, TransActionLogState state) {
        this.state = state;
        this.transactionId = tccRequest.getTransactionId();
        this.businessScene = tccRequest.getBusinessScene();
        this.businessModule = tccRequest.getBusinessModule();
    }

    public TransactionLog(TccRequest tccRequest, TransActionLogState state, TransCancelSuccessType cancelType) {
        this.state = state;
        this.transactionId = tccRequest.getTransactionId();
        this.businessScene = tccRequest.getBusinessScene();
        this.businessModule = tccRequest.getBusinessModule();
        this.cancelType = cancelType;
    }
}
