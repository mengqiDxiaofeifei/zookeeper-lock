package com.sparksys.mall.admin.dao;

import com.sparksys.mall.admin.entity.SecKillProduct;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

/**
 * 中文类名: 秒杀库存表 Mapper 接口
 * 中文描述: 秒杀库存表 Mapper 接口
 *
 * @author zhouxinlei
 * @date 2019-12-13 09:59:22
 */
@Repository
public interface SecKillProductDao extends BaseMapper<SecKillProduct> {

    /**
     * 扣减库存
     *
     * @param secKillProduct
     * @return int
     * @throws
     * @author zhouxinlei
     * @date 2019-12-13 14:55:32
     */
    @Update("update sec_kill_product set stock_count = stock_count - 1 where sec_kill_id = #{secKillId} and stock_count > 0")
    int reduceStock(SecKillProduct secKillProduct);
}
