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


@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("token")
public class TokenController {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @GetMapping("/get")
    public Result<String> get(@NotBlank String scene, @NotBlank String key) {
        if (StpUtil.isLogin()) {
            String userId = (String) StpUtil.getLoginId();
            //token:buy:29:10085
            String tokenKey = TOKEN_PREFIX + scene + CACHE_KEY_SEPARATOR + userId + CACHE_KEY_SEPARATOR + key;
            String tokenValue = TokenUtil.getTokenValueByKey(tokenKey);
            //key：token:buy:29:10085
            //value：YZdkYfQ8fy7biSTsS5oZrbsB8eN7dHPgtCV0dw/36AHSfDQzWOj+ULNEcMluHvep/txjP+BqVRH3JlprS8tWrQ==
            stringRedisTemplate.opsForValue().set(tokenKey, tokenValue, 30, TimeUnit.MINUTES);
            return Result.success(tokenValue);
        }
        throw new AuthException(AuthErrorCode.USER_NOT_LOGIN);
    }
}
