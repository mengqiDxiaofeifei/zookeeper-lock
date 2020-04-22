package com.sparksys.mall.core.utils;

import com.sparksys.mall.core.constant.RsaConstant;
import org.apache.tomcat.util.codec.binary.Base64;

import javax.crypto.Cipher;
import java.io.ByteArrayOutputStream;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * 中文类名: RSA密码加密工具类
 * 中文描述: RSA密码加密工具类
 *
 * @author zhouxinlei
 * @date 2019-09-11 10:30:43
 */
public class RsaUtils {
    /**
     * RSA最大加密明文大小
     */
    private static final int MAX_ENCRYPT_BLOCK = 117;

    /**
     * RSA最大解密密文大小
     */
    private static final int MAX_DECRYPT_BLOCK = 128;

    /**
     * 获取密钥对
     *
     * @return java.security.KeyPair
     * @author zhouxinlei
     * @date 2019-09-12 15:25:55
     */
    public static KeyPair getKeyPair() throws Exception {
        KeyPairGenerator generator = KeyPairGenerator.getInstance(RsaConstant.ALGORITHM_NAME);
        generator.initialize(1024);
        return generator.generateKeyPair();
    }

    /**
     * 获取私钥
     *
     * @param privateKey 私钥字符串
     * @return java.security.PrivateKey
     * @author zhouxinlei
     * @date 2019-09-12 15:26:15
     */
    public static PrivateKey getPrivateKey(String privateKey) throws Exception {
        KeyFactory keyFactory = KeyFactory.getInstance(RsaConstant.ALGORITHM_NAME);
        byte[] decodedKey = Base64Utils.decoder(privateKey.getBytes());
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(decodedKey);
        return keyFactory.generatePrivate(keySpec);
    }

    /**
     * 获取公钥
     *
     * @param publicKey 公钥字符串
     * @return java.security.PublicKey
     * @author zhouxinlei
     * @date 2019-09-12 15:26:40
     */
    public static PublicKey getPublicKey(String publicKey) throws Exception {
        KeyFactory keyFactory = KeyFactory.getInstance(RsaConstant.ALGORITHM_NAME);
        byte[] decodedKey = Base64Utils.decoder(publicKey.getBytes());
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(decodedKey);
        return keyFactory.generatePublic(keySpec);
    }

    /**
     * RSA加密
     *
     * @param data  待加密数据
     * @param puKey 公钥
     * @return java.lang.String
     * @author zhouxinlei
     * @date 2019-09-12 15:27:07
     */
    public static String encrypt(String data, String puKey) throws Exception {
        PublicKey publicKey = getPublicKey(puKey);
        Cipher cipher = Cipher.getInstance(RsaConstant.ALGORITHM_NAME);
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        int inputLen = data.getBytes().length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offset = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段加密
        while (inputLen - offset > 0) {
            if (inputLen - offset > MAX_ENCRYPT_BLOCK) {
                cache = cipher.doFinal(data.getBytes(), offset, MAX_ENCRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(data.getBytes(), offset, inputLen - offset);
            }
            out.write(cache, 0, cache.length);
            i++;
            offset = i * MAX_ENCRYPT_BLOCK;
        }
        byte[] encryptedData = out.toByteArray();
        out.close();
        // 获取加密内容使用base64进行编码,并以UTF-8为标准转化成字符串
        // 加密后的字符串
        return new String(Base64Utils.encoder(encryptedData));
    }

    /**
     * RSA解密
     *
     * @param data   待解密数据
     * @param priKey 私钥
     * @return java.lang.String
     * @author zhouxinlei
     * @date 2019-09-12 15:27:29
     */
    public static String decrypt(String data, String priKey) throws Exception {
        PrivateKey privateKey = getPrivateKey(priKey);
        Cipher cipher = Cipher.getInstance(RsaConstant.ALGORITHM_NAME);
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] dataBytes = Base64.decodeBase64(data);
        int inputLen = dataBytes.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offset = 0;
        byte[] cache;
        int i = 0;
        //对数据分段解密
        while (inputLen - offset > 0) {
            if (inputLen - offset > MAX_DECRYPT_BLOCK) {
                cache = cipher.doFinal(dataBytes, offset, MAX_DECRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(dataBytes, offset, inputLen - offset);
            }
            out.write(cache, 0, cache.length);
            i++;
            offset = i * MAX_DECRYPT_BLOCK;
        }
        byte[] decryptedData = out.toByteArray();
        out.close();
        // 解密后的内容
        return new String(decryptedData, "UTF-8");
    }

    /**
     * 签名
     *
     * @param data       待签名数据
     * @param privateKey 私钥
     * @return java.lang.String
     * @author zhouxinlei
     * @date 2019-09-12 15:24:08
     */
    public static String sign(String data, PrivateKey privateKey) throws Exception {
        byte[] keyBytes = privateKey.getEncoded();
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(RsaConstant.ALGORITHM_NAME);
        PrivateKey key = keyFactory.generatePrivate(keySpec);
        Signature signature = Signature.getInstance(RsaConstant.MD5_RSA);
        signature.initSign(key);
        signature.update(data.getBytes());
        return new String(Base64Utils.encoder(signature.sign()));
    }

    /**
     * 验签
     *
     * @param srcData   原始字符串
     * @param publicKey 公钥
     * @param sign      签名
     * @return boolean 是否验签通过
     * @author zhouxinlei
     * @date 2019-09-12 15:23:38
     */
    public static boolean verify(String srcData, PublicKey publicKey, String sign) throws Exception {
        byte[] keyBytes = publicKey.getEncoded();
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(RsaConstant.ALGORITHM_NAME);
        PublicKey key = keyFactory.generatePublic(keySpec);
        Signature signature = Signature.getInstance(RsaConstant.MD5_RSA);
        signature.initVerify(key);
        signature.update(srcData.getBytes());
        return signature.verify(Base64Utils.decoder(sign.getBytes()));
    }

    public static void main(String[] args) {
        try {
            // 生成密钥对
            /*KeyPair keyPair = getKeyPair();
            String privateKey = new String(Base64Utils.encoder(keyPair.getPrivate().getEncoded()));
            String publicKey = new String(Base64Utils.encoder(keyPair.getPublic().getEncoded()));
            System.out.println("私钥:" + privateKey);
            System.out.println("公钥:" + publicKey);*/
            // RSA加密
            String data = "123456";
            String encryptData = encrypt(data, "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCWc2wYfYSjJiJCIWU+Fiyw6u8f9snqVzpbCN1euVDQZwnDHfnP9ZyViK7IrPU3GJxEFUxNc70ZXSj0QoZIbwqwaFQZLbxVOEm9AkPev5AvhqzXUrhIjclwMvWlnesDt81ZyiKhsaFvBN5vRR9Cce4q8y0s7u4kUb2/JlLyYG++NwIDAQAB");
            System.out.println("加密后内容:" + encryptData);
            // RSA解密
            String decryptData = decrypt(encryptData, "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAJZzbBh9hKMmIkIhZT4WLLDq7x/2yepXOlsI3V65UNBnCcMd+c/1nJWIrsis9TcYnEQVTE1zvRldKPRChkhvCrBoVBktvFU4Sb0CQ96/kC+GrNdSuEiNyXAy9aWd6wO3zVnKIqGxoW8E3m9FH0Jx7irzLSzu7iRRvb8mUvJgb743AgMBAAECgYAW2d0XMylMJmSZqrzX20JadLRxJGiyoSEJ5qTI2Fi/7oZ7Djh9L47i32m8rRhpj+FcLefy85tLGO05fEktA0CaIb8MumlBAyatKOTdE5s3uUS7ksRSPOWi9+Vq2d+l34PO5kI2Tqq+qy9iYscNfJ0lwRYYZ2FMHUUXeYFjEAdbYQJBAPDkZErvHy9pqTyG2mxnTNObUmIOVO0ba86OQELeyUl0Z6tMAm0l7vro56lDKbyedyEquo/Bs7NHNnOLcmhe5ykCQQCf4va9GVoh+//TeKiMwtpAWmIhYIaaJoleK45RQxDxd7G4WRotq0Aa4WCqynJsGcmScBJAD+fVn5zYnU6oBQZfAkEAxv81Zr2w2l8D4i9YovKXAbuuy7ghKhpb7a+PoG4ghCC4P0a0Pmx2xim+rhIAmYOiz/BXW8hdZb1vWvYYVeYIOQJAXRbbC7xQAwtiprBQiiwEwIUfL0mw9N1NC3wWj2+Z1M0YtzmBiWd0mnHwQ2A9gHz/JHiF17CrDXCx9FiabC8IxwJATmtIWTszUlhKD7Q462arVQzMnnPkNgOtC1KPM8V4Uqm2JFbPc/gIRJVVt66zyqW303nYVsb72l2+ZDgSLFxpqA==");
            System.out.println("解密后内容:" + decryptData);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.print("加解密异常");
        }
    }
}
