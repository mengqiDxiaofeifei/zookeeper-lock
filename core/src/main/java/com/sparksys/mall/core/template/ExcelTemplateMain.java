package com.sparksys.mall.core.template;

import com.alibaba.fastjson.JSON;
import com.sparksys.mall.core.entity.ColumnEntity;
import com.sparksys.mall.core.entity.UserEntity;
import com.sparksys.mall.core.factory.SimpleColumnFactory;
import org.apache.commons.lang3.RandomUtils;
import org.apache.poi.ss.usermodel.HorizontalAlignment;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 中文类名: excel测试
 * 中文描述: excel测试
 *
 * @author zhouxinlei
 * @date 2019-11-27 17:56:43
 */
public class ExcelTemplateMain {

    public static void main(String[] args) throws IOException {
        List<UserEntity> userEntities = new ArrayList<>(10);
        for (int i = 0; i < 10; i++) {
            UserEntity userEntity = new UserEntity();
            userEntity.setName("zhouxinlei" + i + RandomUtils.nextInt());
            userEntity.setNickName("周鑫磊" + i);
            userEntity.setSex("男" + i);
            userEntities.add(userEntity);
        }
        List<ColumnEntity> columnEntityList = new ArrayList<>(3);
        ColumnEntity columnEntity0 = SimpleColumnFactory.createColumnEntity(HorizontalAlignment.LEFT)
                .createColumnEntity(0, "账号", "name");
        columnEntityList.add(columnEntity0);


        ColumnEntity columnEntity1 = SimpleColumnFactory.createColumnEntity(HorizontalAlignment.CENTER)
                .createColumnEntity(1, "昵称", "nickName");
        columnEntityList.add(columnEntity1);

        ColumnEntity columnEntity2 = SimpleColumnFactory.createColumnEntity(HorizontalAlignment.CENTER)
                .createColumnEntity(2, "性别", "sex");
        columnEntityList.add(columnEntity2);

        System.out.println(JSON.toJSONString(columnEntityList));
        ExcelTemplate excelTemplate = new BusinessExcel();
        excelTemplate.setTitleName("上海市人员调动");
        excelTemplate.setFilePath("D://test.xls");
        excelTemplate.setColumnEntityList(columnEntityList);
        excelTemplate.setFontName("宋体");
        excelTemplate.exportExcel(userEntities);
    }

}
