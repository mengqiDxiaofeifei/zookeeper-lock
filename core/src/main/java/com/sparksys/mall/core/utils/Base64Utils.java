package com.sparksys.mall.core.utils;

import java.util.Base64;

/**
 * 中文类名:base64加密工具类
 * 中文描述:base64加密工具类
 *
 * @author zhouxinlei
 * @date 2019-09-11 11:05:47
 */
public class Base64Utils {

    public static final Base64.Encoder encoder = Base64.getEncoder();
    public static final Base64.Decoder decoder = Base64.getDecoder();

    /**
     * base64加密
     *
     * @param encodeText 明文
     * @return
     */
    public static byte[] encoder(byte[] encodeText) {
        return encoder.encode(encodeText);
    }

    /**
     * base64加密
     *
     * @param decodeText 密文
     */
    public static byte[] decoder(byte[] decodeText) {
        return decoder.decode(decodeText);
    }

}
