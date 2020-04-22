package com.sparksys.mall.admin.util;

import org.redisson.api.*;

import java.util.concurrent.TimeUnit;

/**
 * 中文类名：redis分布式锁帮助类
 * 中文描述：redis分布式锁帮助类
 *
 * @author zhouxinlei
 * @date 2019-12-13 10:31:10
 */
public class RedissonUtil {

    private static RedissonClient redissonClient;

    private static LocalCachedMapOptions cachedMapOptions = LocalCachedMapOptions.defaults()
            .evictionPolicy(LocalCachedMapOptions.EvictionPolicy.LFU)
            .cacheSize(1000);

    public void setRedissonClient(RedissonClient redissonClient) {
        RedissonUtil.redissonClient = redissonClient;
    }

    /**
     * 加锁
     *
     * @param lockKey
     * @return
     */
    public static RLock lock(String lockKey) {
        RLock lock = redissonClient.getLock(lockKey);
        lock.lock();
        return lock;
    }

    /**
     * 释放锁
     *
     * @param lockKey
     */
    public static void unlock(String lockKey) {
        RLock lock = redissonClient.getLock(lockKey);
        lock.unlock();
    }

    /**
     * 释放锁
     *
     * @param lock
     */
    public static void unlock(RLock lock) {
        lock.unlock();
    }

    /**
     * 带超时的锁
     *
     * @param lockKey
     * @param timeout 超时时间   单位：秒
     */
    public static RLock lock(String lockKey, int timeout) {
        RLock lock = redissonClient.getLock(lockKey);
        lock.lock(timeout, TimeUnit.SECONDS);
        return lock;
    }

    /**
     * 带超时的锁
     *
     * @param lockKey
     * @param unit    时间单位
     * @param timeout 超时时间
     */
    public static RLock lock(String lockKey, TimeUnit unit, int timeout) {
        RLock lock = redissonClient.getLock(lockKey);
        lock.lock(timeout, unit);
        return lock;
    }

    /**
     * 尝试获取锁
     *
     * @param lockKey
     * @param waitTime  最多等待时间
     * @param leaseTime 上锁后自动释放锁时间
     * @return
     */
    public static boolean tryLock(String lockKey, int waitTime, int leaseTime) {
        RLock lock = redissonClient.getLock(lockKey);
        try {
            return lock.tryLock(waitTime, leaseTime, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            return false;
        }
    }

    /**
     * 尝试获取锁
     *
     * @param lockKey
     * @param unit      时间单位
     * @param waitTime  最多等待时间
     * @param leaseTime 上锁后自动释放锁时间
     * @return
     */
    public static boolean tryLock(String lockKey, TimeUnit unit, int waitTime, int leaseTime) {
        RLock lock = redissonClient.getLock(lockKey);
        try {
            return lock.tryLock(waitTime, leaseTime, unit);
        } catch (InterruptedException e) {
            return false;
        }
    }

    public static RLocalCachedMap getLocalCachedMap(String key) {
        return redissonClient.getLocalCachedMap(key, cachedMapOptions);
    }

    public static RMapCache getRMapCache(String key) {
        return redissonClient.getMapCache(key);
    }
}
