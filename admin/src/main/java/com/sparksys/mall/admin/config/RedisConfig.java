package com.sparksys.mall.admin.config;

import com.sparksys.mall.admin.properties.RedisProperties;
import com.sparksys.mall.admin.util.RedisObjectSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.time.Duration;

/**
 * 中文类名：redis缓存配置
 * 概要说明：redis缓存配置
 *
 * @author zhouxinlei
 * @date 2019/5/27 0027
 */
@Configuration
public class RedisConfig {

    @Autowired
    public RedisProperties redisProperties;

    /**
     * 设置连接池属性
     *
     * @return
     */
    @Bean
    public JedisPoolConfig jedisPoolConfig() {
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxIdle(redisProperties.getJedis().getPool().getMaxIdle());
        poolConfig.setMinIdle(redisProperties.getJedis().getPool().getMinIdle());
        poolConfig.setMaxTotal(redisProperties.getJedis().getPool().getMaxActive());
        poolConfig.setMaxWaitMillis(redisProperties.getJedis().getPool().getMaxWait());
        poolConfig.setTestOnBorrow(true);
        return poolConfig;
    }

    /**
     * JedisPool连接池
     *
     * @param
     * @return JedisPool
     * @throws
     * @author zhouxinlei
     * @date 2019-09-27 18:39:41
     */
    @Bean("jedisPool")
    public JedisPool jedisPool() {
        JedisPool jedisPool = new JedisPool(jedisPoolConfig(),
                redisProperties.getCache().getHost(),
                redisProperties.getCache().getPort(),
                redisProperties.getCache().getTimeout());
        return jedisPool;
    }

    /**
     * 配置redis连接工厂
     *
     * @return RedisConnectionFactory
     * @author zhouxinlei
     * @date 2019/5/28 0028
     */
    @Bean("redisConnectionFactory")
    @DependsOn("jedisPoolConfig")
    public RedisConnectionFactory redisConnectionFactory() {
        JedisClientConfiguration clientConfig = JedisClientConfiguration.builder()
                .usePooling().poolConfig(jedisPoolConfig())
                .and().readTimeout(Duration.ofMillis(redisProperties.getCache().getTimeout()))
                .build();
        // 单点redis
        RedisStandaloneConfiguration redisConfig = new RedisStandaloneConfiguration();
        // 哨兵redis
        // RedisSentinelConfiguration redisConfig = new RedisSentinelConfiguration();
        // 集群redis
        // RedisClusterConfiguration redisConfig = new RedisClusterConfiguration();
        redisConfig.setHostName(redisProperties.getCache().getHost());
//        redisConfig.setPassword(redisProperties.getCache().getPassword());
        redisConfig.setPort(redisProperties.getCache().getPort());
        redisConfig.setDatabase(redisProperties.getCache().getDatabase());
        return new JedisConnectionFactory(redisConfig, clientConfig);
    }

    /**
     * 配置redisTemplate
     *
     * @return RedisTemplate<String, Object>
     * @author zhouxinlei
     * @date 2019/5/28 0028
     */
    @Bean(name = "redisTemplate")
    public RedisTemplate<String, Object> redisTemplate() {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory());
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new RedisObjectSerializer());
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }
}
