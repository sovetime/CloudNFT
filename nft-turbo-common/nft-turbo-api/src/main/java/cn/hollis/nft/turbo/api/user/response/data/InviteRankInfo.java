package cn.hollis.nft.turbo.api.user.response.data;

import com.github.houbb.sensitive.annotation.strategy.SensitiveStrategyMaskHalf;
import lombok.Getter;
import lombok.Setter;

//邀请排行信息
@Setter
@Getter
public class InviteRankInfo {

    //昵称
    @SensitiveStrategyMaskHalf
    private String nickName;

    //邀请码
    private String inviteCode;

    //邀请积分
    private Integer inviteScore;
}
