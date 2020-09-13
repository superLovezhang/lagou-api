package com.superflower.common.entity.vo;

import com.superflower.common.anno.Phone;
import com.superflower.common.validate.CreateGroup;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@Data
@EqualsAndHashCode
public class RegisterVo implements Serializable {
    @ApiModelProperty(value = "手机号")
    @Phone
    @NotEmpty
    private String phone;

    @NotEmpty(groups = CreateGroup.class)
    @ApiModelProperty(value = "验证码")
    private String verifyCode;

    @ApiModelProperty(value = "密码")
    @NotBlank(message = "密码不为空")
    private String password;
}
