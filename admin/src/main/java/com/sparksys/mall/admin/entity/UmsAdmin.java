package com.sparksys.mall.admin.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 中文类名：后台用户表
 * 概要说明：后台用户表
 *
 * @author zhouxinlei
 * @date 2019/5/26 0026
 */
@Data
@ApiModel(description = "系统用户")
public class UmsAdmin implements Serializable {

    private static final long serialVersionUID = 8786294628256034142L;
    @ApiModelProperty(value = "id")
    @TableId(value = "id", type = IdType.ID_WORKER)
    private Long id;

    @ApiModelProperty(value = "用户账号")
    @TableField("username")
    private String username;

    @ApiModelProperty(value = "密码")
    @TableField("password")
    private String password;

    @ApiModelProperty(value = "头像")
    @TableField("icon")
    private String icon;

    @ApiModelProperty(value = "邮箱")
    @TableField("email")
    private String email;

    @ApiModelProperty(value = "昵称")
    @TableField("nick_name")
    private String nickName;

    @ApiModelProperty(value = "备注信息")
    @TableField("note")
    private String note;

    @ApiModelProperty(value = "创建时间")
    @TableField("create_time")
    private Date createTime;

    @ApiModelProperty(value = "最后登录时间")
    @TableField("login_time")
    private Date loginTime;

    @ApiModelProperty(value = "帐号启用状态：0->禁用；1->启用")
    @TableField("status")
    private Integer status;

    @ApiModelProperty(value = "排序字段")
    @TableField("sort_number")
    private Integer sortNumber;

    @ApiModelProperty(value = "角色")
    @TableField(exist = false)
    private List<String> roles;

    @ApiModelProperty(value = "权限")
    @TableField(exist = false)
    private List<String> permissions;

}
