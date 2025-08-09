package cn.hollis.nft.turbo.user.controller;

import cn.dev33.satoken.stp.StpUtil;
import cn.hollis.nft.turbo.api.user.response.data.InviteRankInfo;
import cn.hollis.nft.turbo.api.user.response.data.UserInfo;
import cn.hollis.nft.turbo.base.response.PageResponse;
import cn.hollis.nft.turbo.user.domain.entity.User;
import cn.hollis.nft.turbo.user.domain.entity.convertor.UserConvertor;
import cn.hollis.nft.turbo.user.domain.service.UserService;
import cn.hollis.nft.turbo.web.vo.MultiResult;
import cn.hollis.nft.turbo.web.vo.Result;
import jakarta.validation.constraints.Max;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 邀请
 *
 * @author
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("user/invite")
public class InviteController {

    @Autowired
    private UserService userService;

    @GetMapping("/getTopN")
    public MultiResult<InviteRankInfo> getTopN(@Max(100) Integer topN) {
        if (topN == null) {
            topN = 100;
        }

        List<InviteRankInfo> inviteRankInfos = userService.getTopN(topN);
        return MultiResult.successMulti(inviteRankInfos, topN, 1, 10);
    }

    @GetMapping("/getMyRank")
    public Result<Integer> getMyRank() {
        String userId = (String) StpUtil.getLoginId();
        Integer rank = userService.getInviteRank(userId);
        return Result.success(rank);
    }

    @GetMapping("/getInviteList")
    public MultiResult<UserInfo> getInviteList(int currentPage) {
        String userId = (String) StpUtil.getLoginId();

        PageResponse<User> pageResponse = userService.getUsersByInviterId(userId,currentPage,20);

        return MultiResult.successMulti(UserConvertor.INSTANCE.mapToVo(pageResponse.getDatas()), pageResponse.getTotal(), currentPage, 20);
    }
}
