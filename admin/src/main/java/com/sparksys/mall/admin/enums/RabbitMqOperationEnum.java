package com.sparksys.mall.admin.enums;

/**
 * 中文类名: rabbitmq操作枚举类
 * 中文描述: rabbitmq操作枚举类
 *
 * @author zhouxinlei
 * @date 2019-12-13 20:00:51
 */
public enum RabbitMqOperationEnum {

    /**
     * 处理成功
     */
    ACCEPT,
    /**
     * 可以重试的错误
     */
    RETRY,

    /**
     * 无需重试的错误
     */
    REJECT,
}
