package com.sparksys.mall.core.template;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;

/**
 * 中文类名: 业务表格类
 * 中文描述: 业务表格类
 *
 * @author zhouxinlei
 * @date 2019-11-27 16:04:33
 */
public class BusinessExcel extends ExcelTemplate {

    @Override
    void initTitleCellStyle(HSSFCell hssfCell) {
        HSSFCellStyle hssfCellStyle = initHSSFCellStyle(fontName, 16, true, true,
                HSSFColor.HSSFColorPredefined.BLUE_GREY,
                HorizontalAlignment.CENTER, VerticalAlignment.CENTER);
        hssfCell.setCellStyle(hssfCellStyle);
    }

    @Override
    void initHeaderCellStyle(HSSFCell hssfCell) {
        HSSFCellStyle hssfCellStyle = initHSSFCellStyle(fontName, 0, true, false, null,
                HorizontalAlignment.CENTER, VerticalAlignment.CENTER);
        hssfCell.setCellStyle(hssfCellStyle);
    }

    @Override
    void initTableCellStyle(HSSFCell hssfCell,HorizontalAlignment horizontalAlignment) {
        HSSFCellStyle hssfCellStyle = initHSSFCellStyle(fontName, 12, true, false, null,
                horizontalAlignment, VerticalAlignment.CENTER);
        hssfCell.setCellStyle(hssfCellStyle);
    }
}
