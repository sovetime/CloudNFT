package auth;

import java.util.UUID;

import cn.hollis.nft.turbo.api.user.constant.UserStateEnum;
import cn.hollis.nft.turbo.api.user.request.UserActiveRequest;
import cn.hollis.nft.turbo.api.user.request.UserAuthRequest;
import cn.hollis.nft.turbo.api.user.request.UserModifyRequest;
import cn.hollis.nft.turbo.user.domain.entity.User;
import cn.hollis.nft.turbo.user.domain.service.AuthServiceImpl;
import cn.hollis.nft.turbo.user.domain.service.UserService;
import cn.hollis.nft.turbo.user.infrastructure.mapper.UserMapper;
import com.alibaba.fastjson2.JSON;

import com.github.houbb.sensitive.core.api.SensitiveUtil;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

/**
 * @author Hollis
 * <p>
 * 这里依赖缓存配置，先 ignore 掉，如果增加了 redis 配置之后，可以把redissonClient的 mock 移除，再去除 ignore 即可
 */
@Ignore
public class UserServiceTest extends UserBaseTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserMapper userMapper;

    @Test
    public void testCache() {
        userService.register("13444444444","6666");
        User user = userService.findByTelephone("13444444444");
        System.out.println(JSON.toJSONString(user));
        Assert.assertNotNull(user);
        User user1 = new User();
        BeanUtils.copyProperties(user, user1);
        user1.setNickName("newtest");
        userService.updateById(user1);
        User user2 = userService.findByTelephone("13444444444");
        System.out.println(JSON.toJSONString(user2));
        Assert.assertNotEquals(user2.getNickName(), "newtest");
    }

    @Test
    public void testCache1() {
        userService.register("13448444447","6666");
        User user = userService.findByTelephone("13448444447");
        Assert.assertEquals(user.getState(), UserStateEnum.INIT);

        UserAuthRequest request = new UserAuthRequest();
        request.setIdCard("12321321321321");
        request.setRealName("test");
        request.setUserId(user.getId());
        Boolean res = userService.auth(request).getSuccess();
        Assert.assertTrue(res);

        user = userService.findById(user.getId());
        Assert.assertNotNull(user);
        Assert.assertEquals(user.getState(), UserStateEnum.AUTH);
    }

    @Test
    public void testAuth() {
        userService.register("13444444445","6666");
        User user = userService.findByTelephone("13444444445");
        User sensitiveUser = SensitiveUtil.desCopy(user);
        Assert.assertNotNull(user);
        Assert.assertEquals(user.getState(), UserStateEnum.INIT);
        Assert.assertEquals(sensitiveUser.getTelephone(), "1344****445");
        UserAuthRequest userAuthRequest = new UserAuthRequest();
        userAuthRequest.setUserId(user.getId());
        userAuthRequest.setRealName("wang");
        userAuthRequest.setIdCard("1234");
        Boolean authResult = userService.auth(userAuthRequest).getSuccess();
        Assert.assertTrue(authResult);
        user = userMapper.findById(user.getId());
        Assert.assertNotNull(user);
        Assert.assertEquals(user.getState(), UserStateEnum.AUTH);
        Assert.assertEquals(user.getRealName(), "wang");
        Assert.assertEquals(user.getIdCardNo(), "1234");
        //修改信息
        UserModifyRequest userModifyRequest = new UserModifyRequest();
        userModifyRequest.setUserId(user.getId());
        userModifyRequest.setNickName("newNick");
        userModifyRequest.setProfilePhotoUrl("xxx");
        Boolean modifyResult = userService.modify(userModifyRequest).getSuccess();
        Assert.assertTrue(modifyResult);
        user = userMapper.findById(user.getId());
        Assert.assertNotNull(user);
        Assert.assertEquals(user.getRealName(), "wang");
        Assert.assertEquals(user.getIdCardNo(), "1234");
    }

    @Test
    public void testActive() {
        userService.register("13444444446","6666");
        User user = userService.findByTelephone("13444444446");

        Assert.assertNotNull(user);
        Assert.assertEquals(user.getState(), UserStateEnum.INIT);
        UserAuthRequest userAuthRequest = new UserAuthRequest();
        userAuthRequest.setUserId(user.getId());
        userAuthRequest.setRealName("wang");
        userAuthRequest.setIdCard("1234");
        Boolean authResult = userService.auth(userAuthRequest).getSuccess();
        Assert.assertTrue(authResult);
        user = userMapper.findById(user.getId());
        Assert.assertNotNull(user);
        Assert.assertEquals(user.getState(), UserStateEnum.AUTH);
        UserActiveRequest userActiveRequest = new UserActiveRequest();
        userActiveRequest.setUserId(user.getId());
        userActiveRequest.setBlockChainUrl("url");
        userActiveRequest.setBlockChainPlatform("XXX");
        Boolean activeResult = userService.active(userActiveRequest).getSuccess();
        Assert.assertTrue(activeResult);
        user = userMapper.findById(user.getId());
        Assert.assertNotNull(user);
        Assert.assertEquals(user.getBlockChainUrl(), "url");
        Assert.assertEquals(user.getState(), UserStateEnum.ACTIVE);


    }

    @Test
    public void testModify() {
        userService.register("13844444448","6666");
        User user = userService.findByTelephone("13844444448");

        Assert.assertNotNull(user);
        Assert.assertEquals(user.getState(), UserStateEnum.INIT);

        //修改信息
        UserModifyRequest userModifyRequest = new UserModifyRequest();
        userModifyRequest.setUserId(user.getId());
        String nickName = UUID.randomUUID().toString().substring(0, 13);
        userModifyRequest.setNickName(nickName);
        userModifyRequest.setProfilePhotoUrl("xxx");
        Boolean modifyResult = userService.modify(userModifyRequest).getSuccess();
        Assert.assertTrue(modifyResult);
        user = userMapper.findById(user.getId());
        Assert.assertNotNull(user);
        Assert.assertEquals(user.getProfilePhotoUrl(), "xxx");
        Assert.assertEquals(user.getNickName(), nickName);
    }

    @Test
    public void testFreezeANdUnFreeze() {
        userService.register("13447444448","6666");
        User user = userService.findByTelephone("13447444448");

        Assert.assertNotNull(user);
        Assert.assertEquals(user.getState(), UserStateEnum.INIT);
        UserAuthRequest userAuthRequest = new UserAuthRequest();
        userAuthRequest.setUserId(user.getId());
        userAuthRequest.setRealName("wang");
        userAuthRequest.setIdCard("1234");

        Boolean authResult = userService.auth(userAuthRequest).getSuccess();
        Assert.assertTrue(authResult);
        user = userMapper.findById(user.getId());
        Assert.assertNotNull(user);
        Assert.assertEquals(user.getState(), UserStateEnum.AUTH);
        UserActiveRequest userActiveRequest = new UserActiveRequest();
        userActiveRequest.setUserId(user.getId());
        userActiveRequest.setBlockChainUrl("url");
        userActiveRequest.setBlockChainPlatform("XXX");
        Boolean activeResult = userService.active(userActiveRequest).getSuccess();
        Assert.assertTrue(activeResult);
        user = userMapper.findById(user.getId());
        Assert.assertNotNull(user);
        Assert.assertEquals(user.getBlockChainUrl(), "url");
        Assert.assertEquals(user.getState(), UserStateEnum.ACTIVE);
        Boolean freezeResult = userService.freeze(user.getId()).getSuccess();
        Assert.assertTrue(freezeResult);
        user = userMapper.findById(user.getId());
        Assert.assertNotNull(user);
        Assert.assertEquals(user.getState(), UserStateEnum.FROZEN);
        Boolean unFreezeResult = userService.unfreeze(user.getId()).getSuccess();
        Assert.assertTrue(unFreezeResult);
        user = userMapper.findById(user.getId());
        Assert.assertNotNull(user);
        Assert.assertEquals(user.getState(), UserStateEnum.ACTIVE);

    }
}
