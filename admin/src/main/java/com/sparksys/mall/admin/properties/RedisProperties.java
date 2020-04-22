package com.sparksys.mall.admin.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.stereotype.Component;

/**
 * 中文类名: redis配置属性
 * 中文描述: redis配置属性
 *
 * @author zhouxinlei
 * @date 2019-09-10 10:41:51
 */
@Data
@Component
@ConfigurationProperties(prefix = "spring.redis")
public class RedisProperties {

    @NestedConfigurationProperty
    private JedisBean jedis;

    @NestedConfigurationProperty
    private CacheBean cache;

    @Data
    public static class JedisBean {

        private PoolBean pool;

        @Data
        public static class PoolBean {
            private int maxActive;
            private int maxWait;
            private int maxIdle;
            private int minIdle;
        }
    }

    @Data
    public static class CacheBean {
        private String host;
        private int database;
        private int port;
        private int expire;
        private int timeout;
    }
}
