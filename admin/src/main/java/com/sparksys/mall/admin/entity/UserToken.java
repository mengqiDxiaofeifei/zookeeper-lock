package com.sparksys.mall.admin.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

/**
 * 中文类名: token实体类
 * 中文描述: token实体类
 *
 * @author zhouxinlei
 * @date 2019-09-26 15:09:38
 */
@Data
@Builder
@ApiModel(description = "token")
public class UserToken {

    @ApiModelProperty(value = "token")
    private String token;

    @ApiModelProperty(value = "JWT负载开头")
    private String tokenHead;

    @ApiModelProperty(value = "token有效期(s)")
    private Long expiration;
}
