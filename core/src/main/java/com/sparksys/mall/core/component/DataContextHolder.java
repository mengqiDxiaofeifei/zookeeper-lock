package com.sparksys.mall.core.component;

import com.sparksys.mall.core.enums.DBTypeEnum;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 中文类名：主从数据库线程切换
 * 概要说明：
 *
 * @author zhouxinlei
 * @date 2019/5/29 0029
 */
@Slf4j
public class DataContextHolder {
    private static final ThreadLocal<DBTypeEnum> contextHolder = new ThreadLocal<>();

    private static final AtomicInteger counter = new AtomicInteger(-1);

    public static void set(DBTypeEnum dbType) {
        contextHolder.set(dbType);
    }

    public static DBTypeEnum get() {
        return contextHolder.get();
    }

    public static void master() {
        set(DBTypeEnum.MASTER);
        log.debug("当前已切换到master");
    }

    public static void slave() {
        set(DBTypeEnum.SLAVE1);
    }
}
