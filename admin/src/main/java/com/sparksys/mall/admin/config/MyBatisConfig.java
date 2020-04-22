package com.sparksys.mall.admin.config;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.core.MybatisXMLLanguageDriver;
import com.baomidou.mybatisplus.core.config.GlobalConfig;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import com.github.pagehelper.PageInterceptor;
import com.sparksys.mall.admin.properties.MyBatisProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.mapping.DatabaseIdProvider;
import org.apache.ibatis.plugin.Interceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.util.ObjectUtils;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.Properties;

/**
 * 中文类名：mybatis全局配置
 * 概要说明：mybatis全局配置
 *
 * @author zhouxinlei
 * @date 2019/5/26 0026
 */
@Configuration
@MapperScan("com.sparksys.mall.admin.dao")
@EnableTransactionManagement
@Slf4j
public class MyBatisConfig {
    @Autowired
    @Qualifier("adminRoutingDataSource")
    private DataSource dataSource;
    @Autowired
    private Interceptor[] interceptors;
    @Autowired(required = false)
    private DatabaseIdProvider databaseIdProvider;
    @Autowired
    private MyBatisProperties myBatisProperties;

    /**
     * MybatisSqlSessionFactoryBean配置
     *
     * @return MybatisSqlSessionFactoryBean
     * @throws IOException
     * @author zhouxinlei
     * @time 2019-07-20 02:34
     */
    @Bean
    public MybatisSqlSessionFactoryBean mybatisSqlSessionFactoryBean() throws IOException {
        MybatisSqlSessionFactoryBean mybatisPlus = new MybatisSqlSessionFactoryBean();
        log.info("myBatisProperties = {}", JSON.toJSONString(myBatisProperties));
        mybatisPlus.setDataSource(dataSource);
        // MP 全局配置，更多内容进入类看注释
        GlobalConfig globalConfig = new GlobalConfig();
        // 配置公共字段自动填写
        GlobalConfig.DbConfig dbConfig = new GlobalConfig.DbConfig();
        dbConfig.setIdType(IdType.ID_WORKER_STR);
        globalConfig.setDbConfig(dbConfig);
        mybatisPlus.setGlobalConfig(globalConfig);
        if (!ObjectUtils.isEmpty(this.interceptors)) {
            mybatisPlus.setPlugins(this.interceptors);
        }
        MybatisConfiguration mc = new MybatisConfiguration();
        mc.setDefaultScriptingLanguage(MybatisXMLLanguageDriver.class);
        mybatisPlus.setConfiguration(mc);
        if (this.databaseIdProvider != null) {
            mybatisPlus.setDatabaseIdProvider(this.databaseIdProvider);
        }
        mybatisPlus.setTypeAliasesPackage(myBatisProperties.getTypeAliasesPackage());
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        mybatisPlus.setMapperLocations(resolver.getResources(myBatisProperties.getMapperLocations()));
        return mybatisPlus;
    }

    /**
     * pageHelper 分页插件
     *
     * @return PageInterceptor
     * @author zhouxinlei
     * @time 2019-07-20 02:35
     */
    @Bean
    public PageInterceptor pageInterceptor() {
        PageInterceptor pageInterceptor = new PageInterceptor();
        Properties properties = new Properties();
        properties.setProperty("reasonable", "true");
        pageInterceptor.setProperties(properties);
        return pageInterceptor;
    }
}
