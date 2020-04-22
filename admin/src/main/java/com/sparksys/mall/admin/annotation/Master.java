package com.sparksys.mall.admin.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 中文类名: Master注解
 * 中文描述: 用于强制使用主库的操作
 *
 * @author zhouxinlei
 * @date 2019-09-20 15:09:44
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Master {
}
