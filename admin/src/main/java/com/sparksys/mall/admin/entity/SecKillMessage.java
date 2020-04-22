package com.sparksys.mall.admin.entity;

import lombok.Data;

/**
 * 中文类名：秒杀消息
 * 中文描述：
 *
 * @author zhouxinlei
 * @date 2019-12-13 14:17:01
 */
@Data
public class SecKillMessage {
    /**
     * 秒杀用户
     */
    private UmsAdminBean umsAdminBean;
    /**
     * 秒杀商品id
     */
    private Long secKillId;
}
