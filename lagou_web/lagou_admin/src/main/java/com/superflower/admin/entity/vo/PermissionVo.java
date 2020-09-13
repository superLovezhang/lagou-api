package com.superflower.admin.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class PermissionVo implements Serializable {
    @NotBlank(message = "权限名称不能为空")
    @ApiModelProperty(value = "权限名")
    private String name;

    @NotBlank(message = "权限URL地址不能为空")
    @ApiModelProperty(value = "权限url")
    private String url;

    @ApiModelProperty(value = "图标")
    private String icon;

    @NotNull(message = "资源类型不能为空")
    @Range(min = 0, max = 2, message = "请填写正确的资源类型")
    @ApiModelProperty(value = "资源类型 0-目录 1-菜单 2-按钮")
    private Integer type;

    @ApiModelProperty(value = "父id")
    private String pid;

    @ApiModelProperty(value = "权限描述")
    private String description;
}
