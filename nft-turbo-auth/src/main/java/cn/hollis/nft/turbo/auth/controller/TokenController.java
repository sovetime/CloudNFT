package cn.hollis.nft.turbo.auth.controller;

import cn.dev33.satoken.stp.StpUtil;
import cn.hollis.nft.turbo.api.goods.constant.GoodsType;
import cn.hollis.nft.turbo.api.goods.model.BaseGoodsVO;
import cn.hollis.nft.turbo.api.goods.service.GoodsFacadeService;
import cn.hollis.nft.turbo.auth.constant.TokenSceneEnum;
import cn.hollis.nft.turbo.auth.exception.AuthErrorCode;
import cn.hollis.nft.turbo.auth.exception.AuthException;
import cn.hollis.nft.turbo.web.util.TokenUtil;
import cn.hollis.nft.turbo.web.vo.Result;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
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

    @DubboReference(version = "1.0.0")
    private GoodsFacadeService goodsFacadeService;

    //获取token
    @GetMapping("/get")
    public Result<String> get(@NotBlank String scene, @NotBlank String key) {

        /**
         * 检查下key是不是合法的值（存在的商品id），如果不合法，拒绝生成token，避免攻击者传入一堆随机的key来生成token。
         * 如果做的再好点，商品id不用自增id，而是雪花算法等方式生成，避免攻击者穷举
         *
         * 但是在后面校验token的时候，还是有个问题，那就是我们其实没有校验token对应的商品和下单的商品是不是同一个。这块大家可以自行实现一下。
         */
        TokenSceneEnum tokenScene = Arrays.stream(TokenSceneEnum.values()).filter(tokenSceneEnum -> tokenSceneEnum.getScene().equals(scene))
                .findFirst()
                .orElseThrow(() -> new AuthException(AuthErrorCode.TOKEN_SCENE_NOT_EXIST));

        BaseGoodsVO baseGoodsVO = goodsFacadeService.getGoods(key, getGoodsType(tokenScene));

        if (baseGoodsVO == null || !baseGoodsVO.available()) {
            throw new AuthException(AuthErrorCode.TOKEN_KEY_IS_ILLEGAL);
        }

        if (StpUtil.isLogin()) {
            //获取用户id
            String userId = (String) StpUtil.getLoginId();

            //key：(token:+场景+userid:+key(商品id) ) -> token:buy:29:10085
            String tokenKey = TOKEN_PREFIX + scene + CACHE_KEY_SEPARATOR + userId + CACHE_KEY_SEPARATOR + key;
            //通过tokenkey生成value值
            String tokenValue = TokenUtil.getTokenValueByKey(tokenKey);

            //缓存在Redis中
            stringRedisTemplate.opsForValue().set(tokenKey, tokenValue, 30, TimeUnit.MINUTES);
            return Result.success(tokenValue);
        }
        throw new AuthException(AuthErrorCode.USER_NOT_LOGIN);
    }

    private GoodsType getGoodsType(TokenSceneEnum tokenScene) {

        return switch (tokenScene) {
            case BUY_COLLECTION -> GoodsType.COLLECTION;
            case BUY_BLIND_BOX -> GoodsType.BLIND_BOX;
            default -> throw new AuthException(AuthErrorCode.TOKEN_SCENE_NOT_EXIST);
        };
    }
}
