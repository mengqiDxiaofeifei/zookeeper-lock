package com.sparksys.mall.admin.entity;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 中文类名: 商品信息表
 * 中文描述: 商品信息表
 *
 * @author zhouxinlei
 * @date 2019-12-13 09:57:40
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class PmsProduct implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 商品ID
     */
    @TableId(value = "id", type = IdType.ID_WORKER)
    private Long id;

    /**
     * 商品名称
     */
    @TableField("name")
    private String name;

    /**
     * 标题
     */
    @TableField("title")
    private String title;

    /**
     * 商品详情
     */
    @TableField("detail")
    private String detail;

    /**
     * 商品图片
     */
    @TableField("image_url")
    private String imageUrl;

    /**
     * 价格
     */
    @TableField("price")
    private BigDecimal price;

    /**
     * 库存
     */
    @TableField("stock")
    private Integer stock;


}
