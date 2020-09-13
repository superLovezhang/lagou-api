package com.superflower.admin.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class RoleVo implements Serializable {
    @NotBlank(message = "角色名称不能为空")
    @ApiModelProperty(value = "角色名")
    private String roleName;

    @NotBlank(message = "角色描述不能为空")
    @ApiModelProperty(value = "角色描述")
    private String roleDescription;

    @Range(min = 0, max = 1)
    @NotNull(message = "状态不能为空")
    @ApiModelProperty(value = "角色状态 0-正常 1-禁用")
    private Integer status;

}
