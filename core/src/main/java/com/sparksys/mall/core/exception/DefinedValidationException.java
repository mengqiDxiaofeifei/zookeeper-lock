package com.sparksys.mall.core.exception;

/**
 * 中文类名: 自定义校验异常类
 * 中文描述: 自定义校验异常
 *
 * @author zhouxinlei
 * @date 2019-09-26 13:09:54
 */
public class DefinedValidationException extends RuntimeException {

    public DefinedValidationException(String message) {

        super(message);
    }
}
