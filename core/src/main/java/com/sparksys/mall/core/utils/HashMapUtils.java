package com.sparksys.mall.core.utils;

import java.util.HashMap;

/**
 * 中文类名: hashMap封装
 * 中文描述:
 *
 * @author zhouxinlei
 * @date 2019-10-05 10:42:21
 */
public class HashMapUtils {


    /**
     * 创建hashMap实例指定初始化大小
     *
     * @param size
     * @return HashMap<K, V>
     * @throws
     * @author zhouxinlei
     * @date 2019-10-05 10:45:34
     */
    public static <K, V> HashMap<K, V> newInstance(int size) {
        return new HashMap<K, V>(size);
    }

    /**
     * 创建hashMap实例
     *
     * @param
     * @return HashMap<K, V>
     * @throws
     * @author zhouxinlei
     * @date 2019-10-05 10:45:48
     */
    public static <K, V> HashMap<K, V> newInstance() {
        return new HashMap<K, V>();
    }
}
