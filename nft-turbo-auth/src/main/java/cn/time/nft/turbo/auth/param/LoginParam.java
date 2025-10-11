package cn.time.nft.turbo.auth.param;

import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
public class LoginParam extends RegisterParam {

    private Boolean rememberMe;
}
