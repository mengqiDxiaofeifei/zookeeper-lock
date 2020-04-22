package com.sparksys.mall.admin.component;

import com.sparksys.mall.core.component.DataContextHolder;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.lang.Nullable;

/**
 * 中文类名：动态路由获取
 * 概要说明：
 *
 * @author zhouxinlei
 * @date 2019/5/29 0029
 */
public class AdminRoutingDataSource extends AbstractRoutingDataSource {

    @Nullable
    @Override
    protected Object determineCurrentLookupKey() {
        return DataContextHolder.get();
    }
}
