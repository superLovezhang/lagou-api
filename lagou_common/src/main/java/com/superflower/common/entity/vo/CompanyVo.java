package com.superflower.common.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class CompanyVo implements Serializable {

    private static final long serialVersionUID=1L;

    @NotBlank(message = "公司名称不能为空")
    @ApiModelProperty(value = "公司名称")
    private String companyName;

    @NotBlank(message = "公司简称不能为空")
    @ApiModelProperty(value = "公司简称")
    private String abbreviation;

    @NotBlank(message = "公司logo不能为空")
    @ApiModelProperty(value = "公司logo地址 默认null")
    private String logo;

    @Range(min = 0, max = 8, message = "行业领域值非法")
    @NotNull(message = "行业领域值必须传")
    @ApiModelProperty(value = "行业领域 0-移动互联网 1-电商 2-金融 3-企业服务 4-教育 5-文娱|内容 6-游戏 7-消费生活 8-硬件")
    private Integer field;

    @NotBlank(message = "公司地址不能为空")
    @ApiModelProperty(value = "公司地址")
    private String companyAddress;

    @Range(min = 0, max = 5, message = "公司规模值非法")
    @NotNull(message = "公司规模值必须传")
    @ApiModelProperty(value = "公司规模  0-少于15人 1-15-50人 2-50-150人 3-150-500人 4-500-2000人 5-2000人以上")
    private Integer scale;

    @Range(min = 0, max = 7, message = "融资阶段值非法")
    @NotNull(message = "融资阶段值必须传")
    @ApiModelProperty(value = "融资阶段 0-未融资 1-天使轮 2-A轮 3-B轮 4-C轮 5-D轮及以上 6-上市公司 7-不需要融资")
    private Integer financing;

    @NotBlank(message = "公司福利不能为空")
    @ApiModelProperty(value = "公司福利 逗号分隔")
    private String welfare;

    @NotBlank(message = "公司介绍不能为空")
    @ApiModelProperty(value = "公司介绍")
    private String companyIntroduction;

}
