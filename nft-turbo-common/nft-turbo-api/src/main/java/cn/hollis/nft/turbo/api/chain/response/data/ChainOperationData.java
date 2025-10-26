package cn.hollis.nft.turbo.api.chain.response.data;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChainOperationData implements Serializable {
    private static final long serialVersionUID = 1L;

    //操作编号
    private String operationId;

}
