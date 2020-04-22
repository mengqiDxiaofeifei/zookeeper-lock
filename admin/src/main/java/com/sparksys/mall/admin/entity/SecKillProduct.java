package com.sparksys.mall.admin.entity;

import com.baomidou.mybatisplus.annotation.IdType;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 中文类名: 秒杀库存表
 * 中文描述: 秒杀库存表
 *
 * @author zhouxinlei
 * @date 2019-12-13 09:56:53
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class SecKillProduct implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 商品库存id
     */
    @TableId(value = "sec_kill_id", type = IdType.ID_WORKER)
    private Long secKillId;

    /**
     * 商品ID
     */
    @TableField("product_id")
    private Long productId;

    /**
     * 商品名称
     */
    @TableField("name")
    private String name;

    /**
     * 库存数量
     */
    @TableField("stock_count")
    private Integer stockCount;

    /**
     * 秒杀开启时间
     */
    @TableField("start_time")
    private Date startTime;

    /**
     * 秒杀结束时间
     */
    @TableField("end_time")
    private Date endTime;

    /**
     * 创建时间
     */
    @TableField("create_time")
    private Date createTime;

    /**
     * 版本号
     */
    @TableField("version")
    private Integer version;


}
