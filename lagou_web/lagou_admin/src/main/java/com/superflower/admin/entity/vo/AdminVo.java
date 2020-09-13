package com.superflower.admin.entity.vo;

import com.superflower.common.validate.CreateGroup;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;

@Data
public class AdminVo implements Serializable {
    @NotBlank(message = "管理名不能为空", groups = CreateGroup.class)
    @ApiModelProperty(value = "管理名")
    private String adminName;

    @NotBlank(message = "账号不能为空", groups = CreateGroup.class)
    @ApiModelProperty(value = "账号")
    private String account;

    @NotBlank(message = "密码不能为空", groups = CreateGroup.class)
    @ApiModelProperty(value = "密码")
    private String password;

    @NotNull(message = "用户所属角色不能为空", groups = CreateGroup.class)
    @ApiModelProperty(value = "角色列表")
    private ArrayList<String> roleIds;
}
