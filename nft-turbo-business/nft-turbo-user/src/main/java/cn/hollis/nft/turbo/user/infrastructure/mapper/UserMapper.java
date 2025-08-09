package cn.hollis.nft.turbo.user.infrastructure.mapper;

import cn.hollis.nft.turbo.user.domain.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import jakarta.validation.constraints.NotNull;
import org.apache.ibatis.annotations.Mapper;

/**
 * user mapper
 * @author hollis
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

    /**
     * 根据id查询用户
     *
     * @param id
     * @return
     */
    User findById(long id);

    /**
     * 根据昵称查询用户
     *
     * @param nickname
     * @return
     */
    User findByNickname(@NotNull String nickname);

    /**
     * 根据邀请码查询用户
     * @param inviteCode
     * @return
     */
    User findByInviteCode(@NotNull String inviteCode);

    /**
     * 根据手机号查询用户
     *
     * @param telephone
     * @return
     */
    User findByTelephone(@NotNull String telephone);

    /**
     * 根据昵称和密码查询用户
     *
     * @param telephone
     * @param passwordHash
     * @return
     */
    User findByTelephoneAndPass(String telephone, String passwordHash);
}
