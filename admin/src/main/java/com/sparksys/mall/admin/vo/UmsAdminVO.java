package com.sparksys.mall.admin.vo;

import com.sparksys.mall.core.dto.WebVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 中文类名：用户信息参数
 * 概要说明：用户信息参数
 *
 * @author zhouxinlei
 * @date 2019/5/25 0025
 */
@Data
@ApiModel(description = "用户信息参数")
@EqualsAndHashCode(callSuper = false)
public class UmsAdminVO extends WebVO {

    private Long id;

    @ApiModelProperty(value = "用户账号")
    private String username;

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "头像")
    private String icon;

    @ApiModelProperty(value = "邮箱")
    private String email;

    @ApiModelProperty(value = "昵称")
    private String nickName;

    @ApiModelProperty(value = "备注")
    private String note;

    @ApiModelProperty(value = "帐号启用状态：0->禁用；1->启用")
    private Integer status;

}
