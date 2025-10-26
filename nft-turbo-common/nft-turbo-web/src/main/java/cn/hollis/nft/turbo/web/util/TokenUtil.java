package cn.hollis.nft.turbo.web.util;

import cn.hutool.crypto.SecureUtil;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

import static cn.hollis.nft.turbo.cache.constant.CacheConstant.CACHE_KEY_SEPARATOR;

@Slf4j
public class TokenUtil {

    private static final String TOEKN_AES_KEY = "tokenbynfturbo_0";

    public static final String TOKEN_PREFIX = "token:";

    //通过tokenkey生成 value值
    public static String getTokenValueByKey(String tokenKey) {
        if (tokenKey == null) {
            return null;
        }

        String uuid = UUID.randomUUID().toString();
        //key: (token:+场景+userid:+key ) -> token:buy:29:10085
        //value: token:buy:29:10085:uuid(5ac6542b-64b1-4d41-91b9-e6c55849bb7f)
        String tokenValue = tokenKey + CACHE_KEY_SEPARATOR + uuid;

        //使用aes加墨算法进行加密，返回base64编码
        return SecureUtil.aes(TOEKN_AES_KEY.getBytes(StandardCharsets.UTF_8)).encryptBase64(tokenValue);
    }

    //通过tokenValue获取tokenKey
    public static String getTokenKeyByValue(String tokenValue) {
        if (tokenValue == null) {
            return null;
        }

        //使用aes解密
        String decryptTokenValue = SecureUtil.aes(TOEKN_AES_KEY.getBytes(StandardCharsets.UTF_8)).decryptStr(tokenValue);
        log.info("decryptTokenValue: {}", decryptTokenValue);

        //字符串截取前面的key部分
        return decryptTokenValue.substring(0, decryptTokenValue.lastIndexOf(CACHE_KEY_SEPARATOR));
    }
}
