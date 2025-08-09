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
public class WenChangChainBody extends WenChangRequestBody{

    @JSONField(name = "name")
    private String name;

    @JSONField(name = "class_id")
    private String classId;

    @JSONField(name = "owner")
    private String  owner;
}
