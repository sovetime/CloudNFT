package cn.hollis.nft.turbo.admin.param;

import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
public class AdminLoginParam extends AdminRegisterParam {

    /**
     * 记住我
     */
    private Boolean rememberMe;
}
