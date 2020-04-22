package com.sparksys.mall.core.constant;

/**
 * 中文类名: 通用核心常量
 * 中文描述: 通用核心常量
 *
 * @author zhouxinlei
 * @date 2019-10-10 09:43:11
 */
public class CoreConstant {

    /**
     * 中文类名: jwt常量
     * 中文描述: jwt常量
     *
     * @author zhouxinlei
     * @date 2019-09-20 14:18:13
     */
    public static final class JwtTokenConstant {

        /**
         * JWT存储的请求头
         */
        public static final String JWT_TOKEN_HEADER = "Authorization";
        /**
         * JWT加解密使用的密钥
         */
        public static final String JWT_SECRET = "secret";
        /**
         * JWT的超期限时间(60*60*24)
         */
        public static final Long JWT_EXPIRATION = 604800L;
        /**
         * JWT负载中拿到开头
         */
        public static final String JWT_TOKEN_HEAD = "Bearer ";

        public static final String CLAIM_KEY_USERNAME = "sub";

        public static final String CLAIM_KEY_CREATED = "created";

    }

}
