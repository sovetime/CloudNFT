package cn.hollis.nft.turbo.auth.controller;

import cn.dev33.satoken.stp.StpUtil;
import cn.hollis.nft.turbo.auth.exception.AuthErrorCode;
import cn.hollis.nft.turbo.auth.exception.AuthException;
import cn.hollis.nft.turbo.web.util.TokenUtil;
import cn.hollis.nft.turbo.web.vo.Result;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

import static cn.hollis.nft.turbo.cache.constant.CacheConstant.CACHE_KEY_SEPARATOR;
import static cn.hollis.nft.turbo.web.util.TokenUtil.TOKEN_PREFIX;

//获取token
//创建订单的时候需要携带token，防止订单被重复创建
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("token")
public class TokenController {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    //获取token
    @GetMapping("/get")
    public Result<String> get(@NotBlank String scene, @NotBlank String key) {
        if (StpUtil.isLogin()) {
            //获取用户id
            String userId = (String) StpUtil.getLoginId();

            //key：(token:+场景+userid:+key ) -> token:buy:29:10085
            String tokenKey = TOKEN_PREFIX + scene + CACHE_KEY_SEPARATOR + userId + CACHE_KEY_SEPARATOR + key;
            //通过tokenkey生成value值
            String tokenValue = TokenUtil.getTokenValueByKey(tokenKey);

            //缓存在Redis中
            stringRedisTemplate.opsForValue().set(tokenKey, tokenValue, 30, TimeUnit.MINUTES);
            return Result.success(tokenValue);
        }
        throw new AuthException(AuthErrorCode.USER_NOT_LOGIN);
    }
}
