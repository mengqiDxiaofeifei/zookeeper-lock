package com.sparksys.mall.admin.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 中文类名：Redisson配置属性
 * 中文描述：Redisson配置属性
 * @author zhouxinlei
 * @date 2019-12-13 10:36:54
 */
@Data
@Component
@ConfigurationProperties(prefix = "spring.redisson")
public class RedissonProperties {

    private int timeout = 3000;

    private String address;

    private String password;

    private int connectionPoolSize = 64;
    
    private int connectionMinimumIdleSize=10;

    private int slaveConnectionPoolSize = 250;

    private int masterConnectionPoolSize = 250;

    private String[] sentinelAddresses;

    private String masterName;

}
