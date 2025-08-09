package cn.hollis.nft.turbo.user.infrastructure.util;

import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.symmetric.AES;
import org.apache.commons.lang3.StringUtils;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.SecureRandom;
import java.util.Base64;


//AES加解密
public class AesUtil {

    //密钥
    private static String key = "uTfe6WtWICU/6rk0Gr7qKrAvHaRvQj+HRaHKvSe9UJI=";
    //使用该密钥初始化AES加密器
    private static AES aes = SecureUtil.aes(Base64.getDecoder().decode(key));

    public static String encrypt(String content) {
        //判空修改
        if (StringUtils.isBlank(content)) {
            return content;
        }

        // 使用AES加密并返回十六进制格式的加密结果
        return aes.encryptHex(content);
    }

    public static String decrypt(String content) {
        //判空修改
        if (StringUtils.isBlank(content)) {
            return content;
        }

        // 对十六进制格式的密文进行解密，返回原文
        return aes.decryptStr(content);
    }
}