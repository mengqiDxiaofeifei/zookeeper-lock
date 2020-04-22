package com.sparksys.mall.core.exception;

/**
 * 中文类名: 自定义异常
 * 中文描述: 自定义异常
 *
 * @author zhouxinlei
 * @date 2019-09-26 13:19:25
 */
public class ApiErrorException extends Exception {

    public ApiErrorException(String message) {
        super(message);
    }
}
