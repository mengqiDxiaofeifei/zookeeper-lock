package com.sparksys.mall.core.factory;

import org.apache.poi.ss.usermodel.HorizontalAlignment;

/**
 * 中文类名: 简单列属性创建工厂
 * 中文描述:
 *
 * @author zhouxinlei
 * @date 2019-11-28 17:55:13
 */
public class SimpleColumnFactory {

    public static ColumnFactory createColumnEntity(HorizontalAlignment horizontalAlignment) {
        ColumnFactory columnFactory = null;
        switch (horizontalAlignment) {
            case CENTER:
                columnFactory = new CenterColumn();
                break;
            case LEFT:
                columnFactory = new LeftColumn();
                break;
            case RIGHT:
                columnFactory = new RightColumn();
                break;
            default:
                break;
        }
        return columnFactory;
    }
}
