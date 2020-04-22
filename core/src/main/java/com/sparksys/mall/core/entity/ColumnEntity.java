package com.sparksys.mall.core.entity;

import lombok.Data;
import org.apache.poi.ss.usermodel.HorizontalAlignment;

/**
 * 中文类名: 列属性配置
 * 中文描述:
 *
 * @author zhouxinlei
 * @date 2019-11-28 14:44:11
 */
@Data
public class ColumnEntity {

    private int columnIndex;

    private int width;

    private String header;

    private String attributeName;

    private HorizontalAlignment horizontalAlignment;


}
