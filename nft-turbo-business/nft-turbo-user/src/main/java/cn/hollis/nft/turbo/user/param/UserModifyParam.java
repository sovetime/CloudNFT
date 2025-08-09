package cn.hollis.nft.turbo.user.param;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

/**
 * 用户修改参数
 *
 * @author hollis
 */
@Setter
@Getter
public class UserModifyParam {

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 旧密码
     */
    private String oldPassword;

    /**
     * 新密码
     */
    private String newPassword;

}
