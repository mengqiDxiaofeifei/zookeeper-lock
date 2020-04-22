package com.sparksys.mall.core.result;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * 中文类名：页面响应实体类
 * 概要说明：页面响应实体类
 *
 * @author zhouxinlei
 * @date 2019/5/21 0021
 */
@NoArgsConstructor
@Setter
@Getter
public class ApiResult<T> implements Serializable {

    private static final long serialVersionUID = -219969750248052449L;
    private long code;
    private String message;
    private T data;


    private ApiResult(long code, String message) {
        this.code = code;
        this.message = message;
    }

    private ApiResult(long code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    private ApiResult(IErrorCode errorCode, T data) {
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
        this.data = data;
    }

    private ApiResult(IErrorCode errorCode) {
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
    }

    @JSONField(serialize = false)
    public boolean isSuccess() {
        return this.code == ErrorResult.SUCCESS.getCode();
    }

    /**
     * 成功返回结果
     *
     * @param message
     * @return ApiResult<T>
     * @author zhouxinlei
     * @date 2019/5/26 0026
     */
    public static <T> ApiResult<T> success(String message) {
        return new ApiResult<T>(ErrorResult.SUCCESS.getCode(), message);
    }

    /**
     * 成功返回结果
     *
     * @param errorCode
     * @return ApiResult<T>
     * @author zhouxinlei
     * @date 2019/5/26 0026
     */
    public static <T> ApiResult<T> success(IErrorCode errorCode) {
        return new ApiResult<T>(errorCode);
    }

    /**
     * 成功返回结果
     *
     * @param data
     * @return ApiResult<T>
     * @author zhouxinlei
     * @date 2019/5/26 0026
     */
    public static <T> ApiResult<T> success(T data) {
        return new ApiResult<T>(ErrorResult.SUCCESS, data);
    }

    /**
     * 成功返回结果
     *
     * @param data
     * @param message
     * @return ApiResult<T>
     * @author zhouxinlei
     * @date 2019/5/26 0026
     */
    public static <T> ApiResult<T> success(String message, T data) {
        return new ApiResult<T>(ErrorResult.SUCCESS.getCode(), message, data);
    }

    /**
     * 异常返回结果
     *
     * @param code
     * @param message
     * @return ApiResult<T>
     * @author zhouxinlei
     * @date 2019/5/26 0026
     */
    public static <T> ApiResult<T> exception(long code, String message) {
        return new ApiResult<T>(code, message);
    }

    /**
     * 异常返回结果
     *
     * @param code
     * @param message
     * @param data
     * @return ApiResult<T>
     * @author zhouxinlei
     * @date 2019/5/26 0026
     */
    public static <T> ApiResult<T> exception(long code, String message, T data) {
        return new ApiResult<T>(code, message, data);
    }

    /**
     * 异常返回结果
     *
     * @param message
     * @return ApiResult<T>
     * @author zhouxinlei
     * @date 2019/5/26 0026
     */
    public static <T> ApiResult<T> exception(String message) {
        return new ApiResult<T>(ErrorResult.FAILED.getCode(), message);
    }

    /**
     * 失败返回结果
     *
     * @param errorCode
     * @return ApiResult<T>
     * @author zhouxinlei
     * @date 2019/5/26 0026
     */
    public static <T> ApiResult<T> failed(IErrorCode errorCode) {
        return new ApiResult<T>(errorCode);
    }

    /**
     * 失败返回结果
     *
     * @param message
     * @return ApiResult<T>
     * @author zhouxinlei
     * @date 2019/5/26 0026
     */
    public static <T> ApiResult<T> failed(String message) {
        return new ApiResult<T>(ErrorResult.FAILED.getCode(), message);
    }

    public static <T> ApiResult<T> failed(T data) {
        return new ApiResult<T>(ErrorResult.FAILED, data);
    }

    /**
     * 失败返回结果
     *
     * @param
     * @return ApiResult<T>
     * @author zhouxinlei
     * @date 2019/5/26 0026
     */
    public static <T> ApiResult<T> failed() {
        return failed(ErrorResult.FAILED);
    }

    /**
     * 参数验证失败返回结果
     *
     * @param
     * @return ApiResult<T>
     * @author zhouxinlei
     * @date 2019/5/26 0026
     */
    public static <T> ApiResult<T> validateFailed() {
        return failed(ErrorResult.VALIDATE_FAILED);
    }

    /**
     * 参数验证失败返回结果
     *
     * @param message
     * @return ApiResult<T>
     * @author zhouxinlei
     * @date 2019/5/26 0026
     */
    public static <T> ApiResult<T> validateFailed(String message) {
        return new ApiResult<T>(ErrorResult.VALIDATE_FAILED.getCode(), message);
    }

    /**
     * 未登录返回结果
     *
     * @param data
     * @return ApiResult<T>
     * @author zhouxinlei
     * @date 2019/5/26 0026
     */
    public static <T> ApiResult<T> unauthorized(T data) {
        return new ApiResult<T>(ErrorResult.UNAUTHORIZED, data);
    }

    /**
     * 无权限返回结果
     *
     * @param
     * @return ApiResult<T>
     * @author zhouxinlei
     * @date 2019/5/26 0026
     */
    public static <T> ApiResult<T> unauthorized() {
        return new ApiResult<T>(ErrorResult.UNAUTHORIZED);
    }

    /**
     * 未授权返回结果
     *
     * @param data
     * @return ApiResult<T>
     * @author zhouxinlei
     * @date 2019/5/26 0026
     */
    public static <T> ApiResult<T> forbidden(T data) {
        return new ApiResult<T>(ErrorResult.FORBIDDEN, data);
    }

    /**
     * 未授权返回结果
     *
     * @param
     * @return ApiResult<T>
     * @author zhouxinlei
     * @date 2019/5/26 0026
     */
    public static <T> ApiResult<T> forbidden() {
        return new ApiResult<T>(ErrorResult.FORBIDDEN);
    }

}
