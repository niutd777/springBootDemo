package com.niutd.utils;

import com.alibaba.fastjson.JSONObject;
import org.apache.tomcat.util.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.SecretKeySpec;
import java.util.HashMap;
import java.util.Map;

/**
 * AES加密工具类
 *
 * @author: niutd
 * @date: 2019/3/15 16:20
 */
public class AesEncryptUtils {
    static Logger logger = LoggerFactory.getLogger(AesEncryptUtils.class);
    //可配置到Constant中，并读取配置文件注入,16位,自己定义
    private static final String KEY = "xxxxxxxxxxxxxxxx";

    //参数分别代表 算法名称/加密模式/数据填充方式
    private static final String ALGORITHMSTR = "AES/ECB/PKCS5Padding";

    /**
     * 加密
     *
     * @param content    加密的字符串
     * @param encryptKey key值
     * @return
     * @throws Exception
     */
    public static String encrypt(String content, String encryptKey) {
        try {
            KeyGenerator kgen = KeyGenerator.getInstance("AES");
            kgen.init(128);
            Cipher cipher = Cipher.getInstance(ALGORITHMSTR);
            cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(encryptKey.getBytes(), "AES"));
            byte[] b = cipher.doFinal(content.getBytes("utf-8"));
            // 采用base64算法进行转码,避免出现中文乱码
            return Base64.encodeBase64String(b);
        } catch (Exception e) {
            logger.error("AES加密失败", e.getMessage(), e);
        }
        return null;
    }

    /**
     * 解密
     *
     * @param encryptStr 解密的字符串
     * @param decryptKey 解密的key值
     * @return
     * @throws Exception
     */
    public static String decrypt(String encryptStr, String decryptKey) {
        try {
            KeyGenerator kgen = KeyGenerator.getInstance("AES");
            kgen.init(128);
            Cipher cipher = Cipher.getInstance(ALGORITHMSTR);
            cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(decryptKey.getBytes(), "AES"));
            // 采用base64算法进行转码,避免出现中文乱码
            byte[] encryptBytes = Base64.decodeBase64(encryptStr);
            byte[] decryptBytes = cipher.doFinal(encryptBytes);
            return new String(decryptBytes);
        } catch (Exception e) {
            logger.error("AES解密失败", e.getMessage(), e);
        }
        return null;
    }

    public static String encrypt(String content) {
        return encrypt(content, KEY);
    }

    public static String decrypt(String encryptStr) {
        return decrypt(encryptStr, KEY);
    }


    public static void main(String[] args) {
        Map map = new HashMap<String, String>();
        map.put("key", "value");
        map.put("中文", "汉字");
        String content = JSONObject.toJSONString(map);
        System.out.println("加密前：" + content);

        String encrypt = encrypt(content, KEY);
        System.out.println("加密后：" + encrypt);

        String decrypt = decrypt(encrypt, KEY);
        System.out.println("解密后：" + decrypt);
    }

}
