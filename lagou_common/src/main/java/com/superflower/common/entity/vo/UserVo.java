package com.superflower.common.entity.vo;

import com.superflower.common.anno.Phone;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Date;

@Data
public class UserVo implements Serializable {
    private static final long serialVersionUID=1L;

    @NotBlank(message = "用户名不能为空")
    @ApiModelProperty(value = "用户名")
    private String username;

    @NotBlank(message = "头像不能为空")
    @ApiModelProperty(value = "头像地址")
    private String avatar;

    @Range(min = 0, max = 1, message = "性别只能为0或1")
    @NotNull(message = "性别不能为空")
    @ApiModelProperty(value = "性别 0-女 1-男")
    private Integer sex;

    @Phone
    @NotEmpty(message = "手机号不能为空")
    @ApiModelProperty(value = "手机号")
    private String phone;

    @ApiModelProperty(value = "个人优势")
    private String advantage;

    @NotBlank(message = "居住城市不能为空")
    @ApiModelProperty(value = "所在城市")
    private String city;

    @NotEmpty(message = "邮箱不能为空")
    @Email
    @ApiModelProperty(value = "邮箱")
    private String email;

    @Range(min = 0, max = 1, message = "身份只能为0或1")
    @NotNull(message = "身份不能为空")
    @ApiModelProperty(value = "身份 0-学生 1-非学生")
    private Integer identity;

    @ApiModelProperty(value = "社交主页")
    private String socialHomepage;

    @Pattern(regexp = "^[1-2][0-9][0-9][0-9]-[0-1]{0,1}[0-9]-[0-3]{0,1}[0-9]$", message = "生日格式不正确")
    @ApiModelProperty(value = "生日")
    private String birthday;

    @ApiModelProperty(value = "简历地址 多个以逗号分隔")
    private String resume;

}
