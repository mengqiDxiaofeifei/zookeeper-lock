package com.sparksys.mall.core.factory;

import com.sparksys.mall.core.entity.ColumnEntity;
import org.apache.poi.ss.usermodel.HorizontalAlignment;

/**
 * 中文类名: 居中列属性创建类
 * 中文描述: 居中列属性创建类
 *
 * @author zhouxinlei
 * @date 2019-11-28 17:47:49
 */
public class CenterColumn implements ColumnFactory {
    @Override
    public ColumnEntity createColumnEntity(int columnIndex, String header, String attributeName) {
        ColumnEntity columnEntity = new ColumnEntity();
        columnEntity.setColumnIndex(columnIndex);
        columnEntity.setHeader(header);
        columnEntity.setAttributeName(attributeName);
        columnEntity.setHorizontalAlignment(HorizontalAlignment.CENTER);
        return columnEntity;
    }
}
