package com.sparksys.mall.core.utils;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * 中文类名: ListUtils工具类
 * 中文描述: ListUtils工具类
 *
 * @author zhouxinlei
 * @date 2019-09-26 14:54:08
 */
public class ListUtils {
    /**
     * 判断list是否为空
     *
     * @param list
     * @return boolean
     * @throws
     * @author zhouxinlei
     * @date 2019-11-20 11:22:32
     */
    public static boolean isEmpty(List list) {
        return list == null || list.size() == 0;
    }

    /**
     * 判断list是否不为空
     *
     * @param list
     * @return boolean
     * @throws
     * @author zhouxinlei
     * @date 2019-11-20 11:22:32
     */
    public static boolean isNotEmpty(List list) {
        return !isEmpty(list);
    }


    public static <T> List<T> single(T value) {
        List<T> list = new ArrayList();
        list.add(value);
        return list;
    }

    /**
     * 获取指定区间的集合
     *
     * @param list
     * @param start
     * @param end
     * @return List<T>
     * @throws
     * @author zhouxinlei
     * @date 2019-11-20 10:36:15
     */
    public static <T> List<T> getBetweenElement(List<T> list, Integer start, Integer end) {
        if (list == null || list.size() == 0) {
            return null;
        }
        ArrayList newList = new ArrayList<T>(end - start);
        for (int i = start; i < end; i++) {
            if (i > list.size() - 1) {
                break;
            }
            newList.add(list.get(i));
        }
        return newList;
    }

    /**
     * set转list
     *
     * @param set
     * @return List<T>
     * @throws
     * @author zhouxinlei
     * @date 2019-11-20 10:36:48
     */
    public static <T> List<T> setToList(Set<T> set) {
        return set.stream().collect(Collectors.toList());
    }

    /**
     * list转set
     *
     * @param list
     * @return Set<T>
     * @throws
     * @author zhouxinlei
     * @date 2019-11-20 10:37:04
     */
    public static <T> Set<T> listToSet(List<T> list) {
        return list.stream().collect(Collectors.toSet());
    }

    /**
     * 数组转list
     *
     * @param ids
     * @return List<T>
     * @throws
     * @author zhouxinlei
     * @date 2019-11-20 10:37:22
     */
    public static <T> List<T> arrayToList(T[] ids) {
        return Arrays.stream(ids).collect(Collectors.toList());
    }

    /**
     * 数组转set
     *
     * @param ids
     * @return List<T>
     * @throws
     * @author zhouxinlei
     * @date 2019-11-20 10:37:22
     */
    public static <T> Set<T> arrayToSet(T[] ids) {
        return Arrays.stream(ids).collect(Collectors.toSet());

    }

    /**
     * list转String
     *
     * @param list
     * @return List<T>
     * @throws
     * @author zhouxinlei
     * @date 2019-11-20 10:37:22
     */
    public static String listToString(List<String> list) {
        String str = "";
        if (isNotEmpty(list)) {
            str = list.stream().collect(Collectors.joining(","));
        }
        return str;
    }

    /**
     * String转list
     *
     * @param data
     * @return List<T>
     * @throws
     * @author zhouxinlei
     * @date 2019-11-20 10:37:22
     */
    public static List<String> stringToList(String data) {
        if (StringUtils.isNotEmpty(data)) {
            String[] str = data.split(",");
            return Arrays.asList(str);
        } else {
            return null;
        }
    }

    /**
     * 过滤重复id
     *
     * @param keyExtractor
     * @return Predicate<T>
     * @throws
     * @author zhouxinlei
     * @date 2019-11-20 11:21:13
     */
    public static <T> Predicate<T> distinctById(Function<? super T, Object> keyExtractor) {
        Map<Object, Boolean> map = new ConcurrentHashMap<>();
        return t -> map.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }


    /**
     * 差集（扣除）
     *
     * @param a
     * @param b
     * @return List<T>
     * @throws
     * @author zhouxinlei
     * @date 2019-09-29 22:45:18
     */
    public static <T> List<T> intersection(List<T> a, List<T> b) {

        return a.stream().filter(x -> !b.contains(x)).collect(Collectors.toList());
    }

    /**
     * 取并集
     *
     * @param a
     * @param b
     * @return java.util.List<T>
     * @throws
     * @author zhouxinlei
     * @date 2019-09-29 23:03:22
     */
    public static <T> List<T> unionList(List<T> a, List<T> b) {
        return new ArrayList<>(CollectionUtils.union(a, b));
    }
}
