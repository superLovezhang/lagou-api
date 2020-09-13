package com.superflower.common.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
public class EmployeeVo implements Serializable {
    @NotBlank(message = "名称不能为空")
    @ApiModelProperty(value = "员工名称")
    private String name;

    @NotBlank(message = "性别不能为空")
    @ApiModelProperty(value = "员工性别 0-女 1-男")
    private String sex;

    @NotBlank(message = "头像不能为空")
    @ApiModelProperty(value = "头像地址")
    private String avatar;

    @NotBlank(message = "职位不能为空")
    @ApiModelProperty(value = "职位")
    private String position;

    @Email(message = "邮箱地址非法")
    @ApiModelProperty(value = "接收简历邮箱")
    private String email;

}
