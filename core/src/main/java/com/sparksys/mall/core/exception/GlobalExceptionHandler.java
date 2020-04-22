package com.sparksys.mall.core.exception;

import com.sparksys.mall.core.result.ApiResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.sql.SQLException;

/**
 * 中文类名：全局异常处理
 * 概要说明：全局异常处理
 *
 * @author zhouxinlei
 * @date 2019/5/25 0025
 */
@ControllerAdvice
@RestController
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(DefinedValidationException.class)
    public ApiResult definedValidationException(Exception e) {
        log.error(e.getMessage());
        return ApiResult.validateFailed(e.getMessage());
    }

    @ExceptionHandler(ApiErrorException.class)
    public ApiResult customerException(Exception e) {
        log.error(e.getMessage());
        return ApiResult.failed(e.getMessage());
    }

    /**
     * 405
     *
     * @param
     * @return ApiResult
     * @author zhouxinlei
     * @date 2019/5/25 0025
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public ApiResult httpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        log.error(e.getMessage());
        return ApiResult.exception(405, "method 方法不支持");
    }

    /**
     * 404 没有找到访问资源
     *
     * @param
     * @return ApiResult
     * @author zhouxinlei
     * @date 2019/5/25 0025
     */
    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiResult notFoundPage404(NoHandlerFoundException e) {
        log.error(e.getMessage());
        return ApiResult.exception(404, "没有找到访问资源");
    }

    /**
     * 415 不支持媒体类型
     *
     * @param
     * @return ApiResult
     * @author zhouxinlei
     * @date 2019/5/25 0025
     */
    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    @ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
    public ApiResult httpMediaTypeNotSupportedException(HttpMediaTypeNotSupportedException e) {
        log.error(e.getMessage());
        return ApiResult.exception(415, "不支持媒体类型");
    }

    /**
     * 500 默认异常
     *
     * @param
     * @return ApiResult
     * @author zhouxinlei
     * @date 2019/5/25 0025
     */
    @ExceptionHandler(Exception.class)
    public ApiResult defaultException(Exception e) {
        log.error(e.getMessage());
        e.printStackTrace();
        return ApiResult.exception(500, e.getMessage());
    }

    /**
     * 数据库异常
     *
     * @param e
     * @return ApiResult
     * @author zhouxinlei
     * @date 2019/5/25 0025
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(SQLException.class)
    public ApiResult handleException(SQLException e) {
        log.error("数据库异常{}", e.getMessage());
        return ApiResult.exception(e.getErrorCode(), "数据库异常");
    }

}
