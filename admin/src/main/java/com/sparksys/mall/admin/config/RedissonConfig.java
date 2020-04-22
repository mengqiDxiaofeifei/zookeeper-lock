package com.sparksys.mall.admin.config;

import com.sparksys.mall.admin.properties.RedissonProperties;
import com.sparksys.mall.admin.util.RedissonUtil;
import org.apache.commons.lang3.StringUtils;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.config.SingleServerConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

/**
 * 中文类名：Redisson配置
 * 中文描述：Redisson配置
 *
 * @author zhouxinlei
 * @date 2019-12-13 10:42:07
 */
@Configuration
public class RedissonConfig {


    @Autowired
    private RedissonProperties redssionProperties;

    /**
     * 单机模式自动装配
     *
     * @param
     * @return RedissonClient
     * @throws
     * @author zhouxinlei
     * @date 2019-12-13 10:39:28
     */
    @Bean
    @ConditionalOnClass(value = RedissonProperties.class)
    public RedissonClient redissonClient() {
        Config config = new Config();
        SingleServerConfig serverConfig = config.useSingleServer()
                .setAddress(redssionProperties.getAddress())
                .setTimeout(redssionProperties.getTimeout())
                .setConnectionPoolSize(redssionProperties.getConnectionPoolSize())
                .setConnectionMinimumIdleSize(redssionProperties.getConnectionMinimumIdleSize());
        if (StringUtils.isNotBlank(redssionProperties.getPassword())) {
            serverConfig.setPassword(redssionProperties.getPassword());
        }
        return Redisson.create(config);
    }

    /**
     * 装配locker类，并将实例注入到RedissLockUtil中
     *
     * @return RedissonLockUtil
     * @throws
     * @author zhouxinlei
     * @date 2019-12-13 10:39:07
     */
    @Bean
    public RedissonUtil redissonUtil() {
        RedissonUtil redissonUtil = new RedissonUtil();
        redissonUtil.setRedissonClient(redissonClient());
        return redissonUtil;
    }
}
