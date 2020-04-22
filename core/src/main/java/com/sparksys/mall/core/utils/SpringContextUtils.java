package com.sparksys.mall.core.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * 中文类名：获取bean公用方法
 * 概要说明：获取bean公用方法
 *
 * @author: zhouxinlei
 * @date: 2019/6/24
 */
@Component
public class SpringContextUtils implements ApplicationContextAware {

    private static ApplicationContext context;

    @Override
    public void setApplicationContext(ApplicationContext context)
            throws BeansException {
        SpringContextUtils.context = context;
    }

    public static ApplicationContext getContext() {
        return context;
    }

    public static Object getBean(String name) {
        return getContext().getBean(name);
    }

    public static <T> T getBean(Class<T> requiredType) {
        return getContext().getBean(requiredType);
    }

    public static <T> T getBean(String name, Class<T> clazz) {
        return getContext().getBean(name, clazz);
    }

}
