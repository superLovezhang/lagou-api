package com.superflower.common.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class UserSearchVo implements Serializable {
    @ApiModelProperty(value = "用户名")
    private String username;

    @ApiModelProperty(value = "性别 0-女 1-男")
    private Integer sex;

    @ApiModelProperty(value = "手机号")
    private String phone;

    @ApiModelProperty(value = "所在城市")
    private String city;

    @ApiModelProperty(value = "身份 0-学生 1-非学生")
    private Integer identity;
}
