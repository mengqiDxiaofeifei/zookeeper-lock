package com.sparksys.mall.core.enums;

/**
 * 中文类名: 时间格式枚举类
 * 中文描述: 时间格式枚举类
 *
 * @author zhouxinlei
 * @date 2019-11-20 11:31:26
 */
public enum DateFormatEnum {


    DEFAULT("yyyyMMddHHmmss"),
    FULL("yyyyMMddHHmmssSSS"),
    YMD("yyyyMMdd"),
    UDL_YMD("yyyy-MM-dd"),
    SLASH_YMD("yyyy/MM/dd"),
    FORMAT_DEFAULT("yyyy-MM-dd HH:mm:ss"),
    SLASH_FORMAT_DEFAULT("yyyy/MM/dd HH:mm:ss");

    private String pattern;

    DateFormatEnum(String pattern) {
        this.pattern = pattern;
    }

    public String getPattern() {
        return pattern;
    }
}
