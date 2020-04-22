package com.sparksys.mall.admin.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 中文类名: 系统用户bean
 * 中文描述: 系统用户bean
 *
 * @author zhouxinlei
 * @date 2019-09-20 11:31:02
 */
@Data
public class UmsAdminBean implements Serializable {

    private static final long serialVersionUID = 8786294628256034142L;
    private Long id;

    private String username;
    /**
     * 头像
     */
    private String icon;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 备注信息
     */
    private String note;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 最后登录时间
     */
    private Date loginTime;

    /**
     * 帐号启用状态：0->禁用；1->启用
     */
    private Integer status;

    /**
     * 角色
     */
    private List<String> roles;

    /**
     * 权限
     */
    private List<String> permissions;

}
