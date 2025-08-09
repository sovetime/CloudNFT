package cn.hollis.nft.turbo.api.user.response.data;

import cn.hollis.nft.turbo.api.user.constant.UserRole;
import cn.hollis.nft.turbo.api.user.constant.UserStateEnum;
import com.github.houbb.sensitive.annotation.strategy.SensitiveStrategyPhone;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;


@Getter
@Setter
@NoArgsConstructor
public class UserInfo extends BasicUserInfo {

    private static final long serialVersionUID = 1L;

    // 手机号
    @SensitiveStrategyPhone
    private String telephone;

    //状态
    private String state;

    //区块链地址
    private String blockChainUrl;

    //区块链平台
    private String blockChainPlatform;

    //实名认证
    private Boolean certification;

    //用户角色
    private UserRole userRole;

    //邀请码
    private String inviteCode;

    //注册时间
    private Date createTime;

    public boolean userCanBuy() {

        if (this.getUserRole() != null && !this.getUserRole().equals(UserRole.CUSTOMER)) {
            return false;
        }
        //判断买家状态
        if (this.getState() != null && !this.getState().equals(UserStateEnum.ACTIVE.name())) {
            return false;
        }
        //判断买家状态
        if (this.getState() != null && !this.getCertification()) {
            return false;
        }
        return true;
    }
}
