package com.sparksys.mall.core.entity;

import lombok.Data;

/**
 * 中文类名: RSA实体类
 * 中文描述: RSA实体类
 *
 * @author zhouxinlei
 * @date 2019-10-04 14:36:45
 */
@Data
public class RsaEntity {

    /**
     * 私钥
     */
    private String privateKey;
    /**
     * 公钥
     */
    private String publicKey;
}
