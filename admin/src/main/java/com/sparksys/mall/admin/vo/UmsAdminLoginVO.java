package com.sparksys.mall.admin.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 中文类名：用户登录参数
 * 概要说明：用户登录参数
 *
 * @author zhouxinlei
 * @date 2019/5/25 0025
 */
@Data
@ApiModel(description = "用户登录参数")
public class UmsAdminLoginVO {

    @ApiModelProperty(value = "用户账号")
    private String userName;

    @ApiModelProperty(value = "密码")
    private String password;
}
