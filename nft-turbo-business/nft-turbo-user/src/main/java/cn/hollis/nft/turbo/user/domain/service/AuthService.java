package cn.hollis.nft.turbo.user.domain.service;


//认证服务
public interface AuthService {

    //校验认证信息
    public boolean checkAuth(String realName, String idCard);
}
