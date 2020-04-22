package com.sparksys.mall.admin.util;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * 中文类名：redis操作工具类
 * 概要说明：redis操作工具类
 *
 * @author zhouxinlei
 * @date 2019/5/27 0027
 */
@Component
@Slf4j
public class RedisUtil {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    public RedisSerializer<String> serializer() {
        RedisSerializer<String> serializer = null;
        if (!ObjectUtils.isEmpty(redisTemplate)) {
            serializer = redisTemplate.getStringSerializer();
        }
        log.debug("serializer is {}" + serializer);
        return serializer;

    }


    public boolean set(String key, String value) {
        RedisCallback<Boolean> redisCallback = (x) -> x.set(serializer().serialize(key), serializer().serialize(value));
        boolean result = redisTemplate.execute(redisCallback);
        return result;
    }


    public <T> boolean set(String key, T data) {
        String value = JSON.toJSONString(data);
        RedisCallback<Boolean> redisCallback = (x) -> x.set(serializer().serialize(key), serializer().serialize(value));
        boolean result = redisTemplate.execute(redisCallback);
        return result;
    }

    public String get(String key) {
        RedisCallback<String> redisCallback = (x) -> serializer().deserialize(x.get(serializer().serialize(key)));
        String result = redisTemplate.execute(redisCallback);
        return result;
    }

    public <T> T getBean(String key, Class<T> clazz) {
        RedisCallback<String> redisCallback = (x) -> serializer().deserialize(x.get(serializer().serialize(key)));
        String result = redisTemplate.execute(redisCallback);
        T data = JSON.parseObject(result, clazz);
        return data;
    }

    /**
     * 自增长
     *
     * @param key   key值
     * @param delta 自增间距
     * @return java.lang.Long
     * @throws
     * @author zhouxinlei
     * @date 2019-10-11 16:23:58
     */
    public Long increment(String key, long delta) {
        return redisTemplate.opsForValue().increment(key, delta);
    }

    /**
     * 自减
     *
     * @param key   key值
     * @param delta 自减间距
     * @return java.lang.Long
     * @throws
     * @author zhouxinlei
     * @date 2019-10-11 16:23:58
     */
    public Long decrement(String key, long delta) {
        return redisTemplate.opsForValue().decrement(key, delta);
    }


    public boolean expire(String key, long expire, TimeUnit timeUnit) {
        boolean result = true;
        if (expire == -1) {
            RedisCallback<Boolean> redisCallback = (x) -> x.expire(key.getBytes(), expire);
            result = redisTemplate.execute(redisCallback);
        } else {
            result = redisTemplate.expire(key, expire, timeUnit);
        }
        return result;
    }

    /**
     * 删除
     *
     * @param key
     * @return boolean
     * @throws
     * @author zhouxinlei
     * @date 2019-10-11 16:24:31
     */
    public boolean remove(String key) {
        RedisCallback<Boolean> redisCallback = (x) -> {
            x.del(key.getBytes());
            return true;
        };
        redisTemplate.execute(redisCallback);
        boolean result = redisTemplate.execute(redisCallback);
        return result;
    }


    /**
     * 批量删除
     *
     * @param keys
     * @return boolean
     * @throws
     * @author zhouxinlei
     * @date 2019-10-11 16:24:44
     */
    public boolean removeAll(Set<String> keys) {
        RedisCallback<Boolean> redisCallback = (x) -> {
            keys.forEach(n -> x.del(n.getBytes()));
            return true;
        };
        boolean result = redisTemplate.execute(redisCallback);
        return result;
    }


    /**
     * 判断是否存在key
     *
     * @param key
     * @return boolean
     * @throws
     * @author zhouxinlei
     * @date 2019-10-11 16:24:59
     */
    public boolean exists(String key) {
        String value = get(key);
        if (ObjectUtils.isEmpty(value)) {
            return true;
        }
        return false;
    }

    /**
     * 模糊查询key值
     *
     * @param key
     * @return Set<String>
     * @throws
     * @author zhouxinlei
     * @date 2019-10-11 16:25:33
     */
    public Set<String> fuzzyQuery(String key) {
        Set<String> keys = redisTemplate.keys(key + "*");
        return keys;
    }


    /**
     * 如果key不存在时插入值
     *
     * @param key
     * @param value
     * @return boolean
     * @throws
     * @author zhouxinlei
     * @date 2019-10-11 16:25:53
     */
    public boolean setIfAbsent(String key, String value) {
        return redisTemplate.opsForValue().setIfAbsent(key, value);
    }


    public void multiSet(Map<String, String> maps) {
        redisTemplate.opsForValue().multiSet(maps);
    }

    public <T> void addLeftPushList(String key, List<T> objectList) {
        redisTemplate.opsForList().leftPush(key, objectList);
    }


    public <T> void addRightPushList(String key, List<T> objectList) {
        redisTemplate.opsForList().rightPush(key, objectList);
    }


    public <T> List<T> getLeftPushList(String key) {
        List<T> list = (List<T>) redisTemplate.opsForList().leftPop(key);
        return list;
    }


    public <T> List<T> getRightPushList(String key) {
        List<T> list = (List<T>) redisTemplate.opsForList().rightPop(key);
        return list;
    }
}
