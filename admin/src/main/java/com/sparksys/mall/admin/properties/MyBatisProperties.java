package com.sparksys.mall.admin.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 中文类名: mybatis配置属性常量
 * 中文描述: mybatis配置属性常量
 *
 * @author zhouxinlei
 * @date 2019-09-10 11:42:16
 */
@Data
@Component
@ConfigurationProperties(prefix = "mybatis")
public class MyBatisProperties {

    private String mapperLocations;

    private String typeAliasesPackage;
}
