package com.opay.im.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Base64Utils;
import org.springframework.util.StringUtils;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * AES加解密工具
 * Created by lin.liu on 2019/10/17
 */
@Slf4j
public class AESUtil {

    private static final String PADDING = "AES/CBC/PKCS5Padding";

    /**
     * 加密
     *
     * @param data
     * @param key
     * @return
     * @throws Exception
     */
    public static String encrypt(String data, String key, String ivStr) throws Exception {
        if (StringUtils.isEmpty(data)) {
            throw new Exception("encrypt data can't null or empty");
        }
        if (StringUtils.isEmpty(key)) {
            throw new Exception("encrypt key can't null or empty");
        }

        log.debug("AESUtil key:{}, data:{}", key, data);

        SecretKeySpec skeySpec = getKey(key);
        Cipher cipher = Cipher.getInstance(PADDING);
        IvParameterSpec iv = new IvParameterSpec(ivStr.getBytes());
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
        byte[] encrypted = cipher.doFinal(data.getBytes("utf-8"));

        String result = Base64Utils.encodeToString(encrypted);

        log.debug("AESUtil key:{}, data:{}, result:{}", key, data, result);
        return result;
    }

    /**
     * 解密
     *
     * @param data
     * @param key
     * @return
     * @throws Exception
     */
    public static String decrypt(String data, String key, String ivStr) throws Exception {


        if (StringUtils.isEmpty(data)) {
            throw new Exception("decrypt data can't null or empty");
        }
        if (StringUtils.isEmpty(key)) {
            throw new Exception("decrypt key can't null or empty");
        }

        log.debug("AESUtil decrypt data:{}, key:{}", data, key);
        SecretKeySpec skeySpec = getKey(key);
        Cipher cipher = Cipher.getInstance(PADDING);
        IvParameterSpec iv = new IvParameterSpec(ivStr.getBytes());
        cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);

        byte[] encrypted = Base64Utils.decodeFromString(data);
        byte[] original = cipher.doFinal(encrypted);
        String originalString = new String(original, "utf-8");

        log.debug("AESUtil decrypt data:{}, key:{}, result:{}", data, key, originalString);
        return originalString;
    }

    private static SecretKeySpec getKey(String strKey) throws Exception {
        byte[] arrBTmp = strKey.getBytes();
        byte[] arrB = new byte[16];
        // 创建一个空的16位字节数组（默认值为0）
        for (int i = 0; i < arrBTmp.length && i < arrB.length; i++) {
            arrB[i] = arrBTmp[i];
        }
        SecretKeySpec skeySpec = new SecretKeySpec(arrB, "AES");

        return skeySpec;
    }

}
