package com.superflower.common.entity.vo;

import com.superflower.common.validate.CreateGroup;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
public class ProductVo implements Serializable {
    @NotBlank(message = "产品名称不能为空", groups = CreateGroup.class)
    @ApiModelProperty(value = "产品名称")
    private String name;

    @NotBlank(message = "产品关键词不能为空", groups = CreateGroup.class)
    @ApiModelProperty(value = "产品关键词 逗号分隔")
    private String productKeyword;

    @NotBlank(message = "产品图片不能为空", groups = CreateGroup.class)
    @ApiModelProperty(value = "产品图片")
    private String productImg;

    @NotBlank(message = "产品介绍不能为空", groups = CreateGroup.class)
    @ApiModelProperty(value = "产品介绍")
    private String introduction;
}
