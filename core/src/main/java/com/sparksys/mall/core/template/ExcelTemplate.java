package com.sparksys.mall.core.template;

import com.sparksys.mall.core.entity.ColumnEntity;
import lombok.Data;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.util.ObjectUtils;

import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * 中文类名: excel模板类
 * 中文描述: excel模板类
 *
 * @author zhouxinlei
 * @date 2019-11-27 11:36:06
 */
@Data
public abstract class ExcelTemplate {

    private HSSFWorkbook hssfWorkbook;

    private HSSFSheet sheet;

    private String filePath;

    public String fontName;

    private List<ColumnEntity> columnEntityList;

    private String titleName;

    private int limitWith = 15000;

    private void initExcelData() {
        hssfWorkbook = new HSSFWorkbook();
        Font font = hssfWorkbook.createFont();
        font.setItalic(false);
        font.setFontName(fontName);
        sheet = hssfWorkbook.createSheet();
    }

    /**
     * 初始化标题
     *
     * @return void
     * @throws
     * @author zhouxinlei
     * @date 2019-11-27 15:58:56
     */
    public void initTitle() {
        HSSFRow hssfRow = sheet.createRow(0);
        hssfRow.setHeightInPoints(50);
        int cellSize = columnEntityList.size() - 1;
        for (int i = 0; i < columnEntityList.size(); i++) {
            HSSFCell hssfCell = hssfRow.createCell(i);
            if (i == 0) {
                hssfCell.setCellValue(titleName);
            }
            initTitleCellStyle(hssfCell);
        }
        CellRangeAddress region = new CellRangeAddress(0, 0, 0, cellSize);
        sheet.addMergedRegion(region);
    }

    public void initTableHeader() {
        int currentRowNum = sheet.getLastRowNum() + 1;
        HSSFRow hssfRow = sheet.createRow(currentRowNum);
        columnEntityList.stream().forEach(item -> {
            item.setWidth(item.getHeader().getBytes().length * 256 + 200);
            HSSFCell cell = hssfRow.createCell(item.getColumnIndex());
            cell.setCellValue(item.getHeader());
            initHeaderCellStyle(cell);
        });
    }


    /**
     * 初始化标题样式
     *
     * @param hssfCell
     * @return void
     * @throws
     * @author zhouxinlei
     * @date 2019-11-27 16:08:50
     */
    abstract void initTitleCellStyle(HSSFCell hssfCell);

    /**
     * 初始化表头样式
     *
     * @param hssfCell
     * @return void
     * @throws
     * @author zhouxinlei
     * @date 2019-11-27 16:08:50
     */
    abstract void initHeaderCellStyle(HSSFCell hssfCell);

    /**
     * 初始化表格样式
     *
     * @param hssfCell
     * @param horizontalAlignment
     * @return void
     * @throws
     * @author zhouxinlei
     * @date 2019-11-27 16:08:50
     */
    abstract void initTableCellStyle(HSSFCell hssfCell, HorizontalAlignment horizontalAlignment);

    /**
     * 填充数据
     *
     * @param dataList
     * @return void
     * @throws
     * @author zhouxinlei
     * @date 2019-11-27 15:59:10
     */
    <T> void processData(List<T> dataList) {
        List<String> attributeNameList = columnEntityList.stream().map(ColumnEntity::getAttributeName).collect(Collectors.toList());
        dataList.stream().forEach(item -> {
            int currentRowNum = sheet.getLastRowNum() + 1;
            HSSFRow hssfRow = sheet.createRow(currentRowNum);
            Class clazz = item.getClass();
            Field[] fields = clazz.getDeclaredFields();
            List<Field> fieldList = Arrays.asList(fields);
            AtomicInteger cellIndex = new AtomicInteger(0);
            fieldList.stream().forEach(field -> {
                try {
                    field.setAccessible(true);
                    String value = field.get(item).toString();
                    String attributeName = field.getName();
                    int index = attributeNameList.indexOf(attributeName);
                    if (index != -1) {
                        ColumnEntity columnEntity = columnEntityList.get(index);
                        HSSFCell hssfCell = hssfRow.createCell(cellIndex.get());
                        initTableCellStyle(hssfCell, columnEntity.getHorizontalAlignment());
                        hssfCell.setCellValue(value);
                        int length = value.getBytes().length * 256 + 200;
                        //这里把宽度最大限制到15000
                        if (length > limitWith) {
                            length = limitWith;
                        }
                        columnEntity.setWidth(Math.max(length, columnEntity.getWidth()));
                        columnEntityList.set(index, columnEntity);
                        cellIndex.getAndIncrement();
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            });
        });
        columnEntityList.stream().forEach(item -> sheet.setColumnWidth(item.getColumnIndex(), item.getWidth()));
    }


    public <T> void exportExcel(List<T> dataList) throws IOException {
        initExcelData();
        initTitle();
        initTableHeader();
        processData(dataList);
        FileOutputStream outputStream = new FileOutputStream(filePath);
        hssfWorkbook.write(outputStream);
        outputStream.close();
        System.out.println("导出excel数据成功!");
    }

    public HSSFCellStyle initHSSFCellStyle(String fontName, int fontHeight, boolean fullBorder, boolean bold,
                                           HSSFColor.HSSFColorPredefined hssfColorPredefined,
                                           HorizontalAlignment horizontalAlignment, VerticalAlignment verticalAlignment) {
        HSSFFont hssfFont = hssfWorkbook.createFont();
        hssfFont.setFontName(fontName);
        if (fontHeight != 0) {
            hssfFont.setFontHeightInPoints((short) fontHeight);
        }
        HSSFCellStyle hssfCellStyle = hssfWorkbook.createCellStyle();
        if (fullBorder) {
            if (bold) {
                hssfCellStyle.setBorderBottom(BorderStyle.MEDIUM);//下边框
                hssfCellStyle.setBorderLeft(BorderStyle.MEDIUM);//左边框
                hssfCellStyle.setBorderTop(BorderStyle.MEDIUM);//上边框
                hssfCellStyle.setBorderRight(BorderStyle.MEDIUM);//右边框
            } else {
                hssfCellStyle.setBorderBottom(BorderStyle.THIN);//下边框
                hssfCellStyle.setBorderLeft(BorderStyle.THIN);//左边框
                hssfCellStyle.setBorderTop(BorderStyle.THIN);//上边框
                hssfCellStyle.setBorderRight(BorderStyle.THIN);//右边框
            }
        }
        hssfCellStyle.setAlignment(horizontalAlignment);// 左右居中
        hssfCellStyle.setVerticalAlignment(verticalAlignment);// 上下居中
        hssfCellStyle.setWrapText(true);
        hssfCellStyle.setFont(hssfFont);
        if (!ObjectUtils.isEmpty(hssfColorPredefined)) {
            hssfCellStyle.setFillForegroundColor(hssfColorPredefined.getIndex());// 设置背景色  
            hssfCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        }
        return hssfCellStyle;
    }

}
