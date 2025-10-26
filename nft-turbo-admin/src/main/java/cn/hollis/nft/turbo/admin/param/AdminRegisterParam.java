package cn.hollis.nft.turbo.admin.param;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AdminRegisterParam {

    @NotBlank(message = "手机号不能为空")
    private String telephone;

    @NotBlank(message = "密码不能为空")
    private String password;
}
