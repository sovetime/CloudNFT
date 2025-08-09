package cn.hollis.nft.turbo.chain.domain.entity;

import com.alibaba.fastjson2.annotation.JSONField;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Hollis
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WenChangMintBody extends WenChangRequestBody{

    @JSONField(name = "name")
    private String name;

    @JSONField(name = "recipient")
    private String  recipient;

}
