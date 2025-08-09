package auth;

import cn.hollis.nft.turbo.user.domain.entity.User;
import cn.hollis.nft.turbo.user.domain.entity.UserOperateStream;
import cn.hollis.nft.turbo.user.infrastructure.mapper.UserMapper;
import cn.hollis.nft.turbo.user.infrastructure.mapper.UserOperateStreamMapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static org.mockito.Mockito.when;

public class UserDAOTest extends UserBaseTest {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserOperateStreamMapper userOperateStreamMapper;

    @Test
    public void testStreamInsert() {

        UserOperateStream userOperateStream = new UserOperateStream();
        userOperateStream.setUserId("123");
        userOperateStreamMapper.insert(userOperateStream);

        Assert.assertNotNull(userOperateStream.getId());
        Assert.assertNotNull(userOperateStream.getGmtCreate());
        Assert.assertNotNull(userOperateStream.getGmtModified());

        QueryWrapper<UserOperateStream> queryWrapper = new QueryWrapper<>();
        queryWrapper.setEntity(userOperateStream);
        List<UserOperateStream> res = userOperateStreamMapper.selectObjs(queryWrapper);
        Assert.assertNotNull(res);
    }

    @Test
    public void testFindByNickName() {
        User user = new User();
        user.setNickName("auth");
        int result = userMapper.insert(user);
        Assert.assertEquals(result, 1);
        User existUser = userMapper.findByNickname("auth");

        Assert.assertNotNull(existUser);
        Assert.assertEquals(existUser.getNickName(), user.getNickName());
        Assert.assertNotNull(existUser.getGmtCreate());
        Assert.assertNotNull(existUser.getGmtModified());
        Assert.assertEquals(0, (int) existUser.getDeleted());
        Assert.assertNotNull(existUser.getId());

        user = new User();
        user.setNickName("test2");
        result = userMapper.insert(user);
        Assert.assertEquals(result, 1);

        existUser = userMapper.findByNickname("test2");
        Assert.assertNotNull(existUser);
        Assert.assertEquals(existUser.getNickName(), user.getNickName());
        Assert.assertNotNull(existUser.getGmtCreate());
        Assert.assertNotNull(existUser.getGmtModified());
        Assert.assertEquals(0, (int) existUser.getDeleted());
        Assert.assertNotNull(existUser.getId());
    }
}
