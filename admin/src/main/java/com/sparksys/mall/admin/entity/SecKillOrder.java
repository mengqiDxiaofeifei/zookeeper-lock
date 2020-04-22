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
 * 中文类名: 秒杀订单明细表
 * 中文描述: 秒杀订单明细表
 *
 * @author zhouxinlei
 * @date 2019-12-13 09:56:28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class SecKillOrder implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ID_WORKER)
    private Long id;

    /**
     * 秒杀商品id
     */
    @TableField("sec_kill_id")
    private Long secKillId;

    /**
     * 用户Id
     */
    @TableField("user_id")
    private Long userId;

    /**
     * 状态标示：-1指无效，0指成功，1指已付款
     */
    @TableField("state")
    private Integer state;

    /**
     * 创建时间
     */
    @TableField("create_time")
    private Date createTime;


}
