package com.sparksys.mall.core.dto;

import lombok.Data;

/**
 * 中文类名: 公共入参VO
 * 中文描述: 公共入参VO
 *
 * @author zhouxinlei
 * @date 2019-10-04 17:12:29
 */
@Data
public class WebVO {

    /**
     * 当前页
     */
    private Integer pageNum = 1;
    /**
     * 每页显示条数
     */
    private Integer pageSize = 10;
}
