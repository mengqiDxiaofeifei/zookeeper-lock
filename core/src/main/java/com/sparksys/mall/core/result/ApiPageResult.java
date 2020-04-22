package com.sparksys.mall.core.result;

import com.github.pagehelper.Page;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 中文类名：分页数据封装类
 * 概要说明：分页数据封装类
 *
 * @author zhouxinlei
 * @date 2019/5/21 0021
 */
@Data
public class ApiPageResult<T> implements Serializable {

    private static final long serialVersionUID = 1365552252832702673L;
    private int startIndex;
    private int pageNum;
    private int pageSize;
    private int totalPage;
    private int total;
    private List<T> list;

    /**
     * 分页数据组装处理
     *
     * @param tPage
     * @return PageBean<T>
     * @author zhouxinlei
     * @date 2019-09-09 18:08:40
     */
    public static <T> ApiPageResult<T> resetPage(Page<T> tPage) {
        int pageNum = tPage.getPageNum();
        int pageSize = tPage.getPageSize();
        int totalNum = new Long(tPage.getTotal()).intValue();
        int totalPage = tPage.getPages();
        int startIndex = (pageNum - 1) * pageSize;
        ApiPageResult<T> tPageBean = new ApiPageResult<>();
        tPageBean.setStartIndex(startIndex);
        tPageBean.setPageNum(pageNum);
        tPageBean.setTotal(totalNum);
        tPageBean.setTotalPage(totalPage);
        tPageBean.setList(tPage.getResult());
        tPageBean.setPageSize(pageSize);
        return tPageBean;
    }

}
