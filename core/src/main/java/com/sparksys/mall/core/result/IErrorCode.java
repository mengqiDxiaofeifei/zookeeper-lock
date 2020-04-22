package com.sparksys.mall.core.result;

/**
 * 中文类名：封装API的错误码
 * 概要说明：封装API的错误码
 *
 * @author zhouxinlei
 * @date 2019/5/21 0021
 */
public interface IErrorCode {
    /**
     * code
     *
     * @param
     * @return long
     * @throws
     * @author zhouxinlei
     * @date 2019-09-27 16:56:40
     */
    long getCode();

    /**
     * getMessage
     *
     * @param
     * @return String
     * @throws
     * @author zhouxinlei
     * @date 2019-09-27 16:56:49
     */
    String getMessage();
}
