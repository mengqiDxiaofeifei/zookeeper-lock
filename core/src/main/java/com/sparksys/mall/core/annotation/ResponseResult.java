package com.sparksys.mall.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 中文类名: Response结果返回注解
 * 中文描述: Response结果返回注解
 *
 * @author zhouxinlei
 * @date 2019-09-26 10:06:17
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ResponseResult {

}
