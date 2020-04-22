package com.sparksys.mall.admin.config;

import com.sparksys.mall.admin.component.AdminRoutingDataSource;
import com.sparksys.mall.core.enums.DBTypeEnum;
import com.sparksys.mall.core.utils.HashMapUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import javax.sql.DataSource;
import java.util.Map;

/**
 * 中文类名：数据源配置
 * 中文描述：数据源配置
 *
 * @author zhouxinlei
 * @time 2019-07-20 02:34
 */
@Configuration
@Order(1)
public class DataSourceConfig {

    /**
     * @return DataSource
     * @author zhouxinlei
     * @time 2019-07-20 02:34
     */
    @Bean
    @ConfigurationProperties("spring.datasource.master")
    public DataSource masterDataSource() {
        return DataSourceBuilder.create().build();
    }

    /**
     * @param masterDataSource
     * @param slave1DataSource
     * @return DataSource
     * @author zhouxinlei
     * @time 2019-07-20 02:34
     */
    @Bean
    public DataSource adminRoutingDataSource(@Qualifier("masterDataSource") DataSource masterDataSource,
                                             @Qualifier("slave1DataSource") DataSource slave1DataSource) {
        Map<Object, Object> targetDataSources = HashMapUtils.newInstance(2);

        targetDataSources.put(DBTypeEnum.MASTER, masterDataSource);
        targetDataSources.put(DBTypeEnum.SLAVE1, slave1DataSource);

        AdminRoutingDataSource adminRoutingDataSource = new AdminRoutingDataSource();

        adminRoutingDataSource.setDefaultTargetDataSource(masterDataSource);
        adminRoutingDataSource.setTargetDataSources(targetDataSources);

        return adminRoutingDataSource;
    }

    /**
     * @return DataSource
     * @author zhouxinlei
     * @time 2019-07-20 02:34
     */
    @Bean
    @ConfigurationProperties("spring.datasource.slave1")
    public DataSource slave1DataSource() {
        return DataSourceBuilder.create().build();
    }

}
