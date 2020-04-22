package com.sparksys.mall.core.factory;

import com.sparksys.mall.core.entity.ColumnEntity;

/**
 * 中文类名: 列属性创建工厂
 * 中文描述: 列属性创建工厂
 *
 * @author zhouxinlei
 * @date 2019-11-28 16:15:55
 */
public interface ColumnFactory {

    /**
     * 创建列属性
     *
     * @param columnIndex
     * @param header
     * @param attributeName
     * @return ColumnEntity
     * @throws
     * @author zhouxinlei
     * @date 2019-11-28 17:25:00
     */
    ColumnEntity createColumnEntity(int columnIndex, String header, String attributeName);
}
