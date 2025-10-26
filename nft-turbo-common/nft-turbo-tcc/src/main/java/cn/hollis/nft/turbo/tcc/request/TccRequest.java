package cn.hollis.nft.turbo.tcc.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TccRequest implements Serializable {
    private static final long serialVersionUID = 1L;

    //事务ID
    private String transactionId;

    //业务场景
    private String businessScene;

    // 业务模块
    private String businessModule;

}
