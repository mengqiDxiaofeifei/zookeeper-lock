package com.sparksys.mall.core.result;

/**
 * 中文类名：枚举一些常用API操作码
 * 概要说明：枚举一些常用API操作码
 *
 * @author zhouxinlei
 * @date 2019/5/21 0021
 */
public enum ErrorResult implements IErrorCode {
    /**
     * 成功
     */
    SUCCESS(200, "操作成功"),
    /**
     * 失败
     */
    FAILED(500, "操作失败"),
    /**
     * 参数检验
     */
    VALIDATE_FAILED(400, "参数检验失败"),
    /**
     * 登录
     */
    UNAUTHORIZED(401, "暂未登录或token已过期"),
    /**
     * 权限
     */
    FORBIDDEN(403, "没有相关权限"),

    MUCH_KILL(1000, "哎呦喂，人也太多了，请稍后！"),
    SUCCESS_KILL(1001, "秒杀成功"),
    END_KILL(1002, "秒杀结束");

    private long code;
    private String message;

    private ErrorResult(long code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public long getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
