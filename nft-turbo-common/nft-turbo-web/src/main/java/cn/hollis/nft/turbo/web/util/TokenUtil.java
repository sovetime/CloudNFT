package cn.hollis.nft.turbo.web.util;

import cn.hutool.crypto.SecureUtil;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

import static cn.hollis.nft.turbo.cache.constant.CacheConstant.CACHE_KEY_SEPARATOR;

public class TokenUtil {

    private static final String TOEKN_AES_KEY = "tokenbynfturbo_0";

    public static final String TOKEN_PREFIX = "token:";

    public static String getTokenValueByKey(String tokenKey) {
        if (tokenKey == null) {
            return null;
        }
        String uuid = UUID.randomUUID().toString();
        //token:buy:29:10085:5ac6542b-64b1-4d41-91b9-e6c55849bb7f
        String tokenValue = tokenKey + CACHE_KEY_SEPARATOR + uuid;

        //YZdkYfQ8fy7biSTsS5oZrbsB8eN7dHPgtCV0dw/36AHSfDQzWOj+ULNEcMluHvep/txjP+BqVRH3JlprS8tWrQ==
        return SecureUtil.aes(TOEKN_AES_KEY.getBytes(StandardCharsets.UTF_8)).encryptBase64(tokenValue);
    }

    public static String getTokenKeyByValue(String tokenValue) {
        if (tokenValue == null) {
            return null;
        }
        //token:buy:29:10085:5ac6542b-64b1-4d41-91b9-e6c55849bb7f
        String decryptTokenValue = SecureUtil.aes(TOEKN_AES_KEY.getBytes(StandardCharsets.UTF_8)).decryptStr(tokenValue);
        System.out.println(decryptTokenValue);

        return decryptTokenValue.substring(0, decryptTokenValue.lastIndexOf(CACHE_KEY_SEPARATOR));
    }
}
