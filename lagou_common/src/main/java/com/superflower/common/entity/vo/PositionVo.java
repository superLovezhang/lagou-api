package com.superflower.common.entity.vo;

import com.superflower.common.validate.CreateGroup;
import com.superflower.common.validate.UpdateGroup;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.*;
import java.io.Serializable;

@Data
public class PositionVo implements Serializable {
    @NotBlank(message = "职位名称不能为空", groups = {CreateGroup.class})
    @ApiModelProperty(value = "职位名称")
    private String positionName;

    @Null(groups = {CreateGroup.class, UpdateGroup.class})
    @ApiModelProperty(value = "行业领域 0-移动互联网 1-电商 2-金融 3-企业服务 4-教育 5-文娱|内容 6-游戏 7-消费生活 8-硬件")
    private Integer field;

    @Null(groups = {CreateGroup.class, UpdateGroup.class})
    @ApiModelProperty(value = "公司规模  0-少于15人 1-15-50人 2-50-150人 3-150-500人 4-500-2000人 5-2000人以上")
    private Integer scale;

    @Null(groups = {CreateGroup.class, UpdateGroup.class})
    @ApiModelProperty(value = "融资阶段 0-未融资 1-天使轮 2-A轮 3-B轮 4-C轮 5-D轮及以上 6-上市公司 7-不需要融资")
    private Integer financing;

    @Range(min = 0, max = 4, message = "学历值非法")
    @NotNull(message = "学历值必须传", groups = {CreateGroup.class})
    @ApiModelProperty(value = "学历 0-大专 1-本科 2-硕士 3-博士 4-不要求")
    private Integer education;

    @Range(min = 0, max = 5, message = "工作经验值非法")
    @NotNull(message = "工作经验值必须传", groups = {CreateGroup.class})
    @ApiModelProperty(value = "经验 0- 应届毕业生 1- 3年及其以下 2- 3-5年 3- 5-10年 4- 10年以上 5-不要求")
    private Integer experience;

    @Range(min = 0, max = 3, message = "招聘类型值非法")
    @NotNull(message = "招聘类型值必须传", groups = {CreateGroup.class})
    @ApiModelProperty(value = "招聘类型 0-全职 1-兼职 3-实习")
    private Integer recruitmentType;

    @Min(value = 1, message = "起始薪资不能小于1K")
    @NotNull(message = "起始薪资不能为空", groups = {CreateGroup.class})
    @ApiModelProperty(value = "薪资起始")
    private Integer salaryBegin;

    @Min(value = 1, message = "薪资上限不能小于1K")
    @NotNull(message = "最高薪资不能为空", groups = {CreateGroup.class})
    @ApiModelProperty(value = "薪资上限")
    private Integer salaryEnd;

    @NotBlank(message = "职位关键词不能为空", groups = {CreateGroup.class})
    @ApiModelProperty(value = "职位关键词 逗号分隔")
    private String positionKeyword;

    @NotBlank(message = "职位描述不能为空", groups = {CreateGroup.class})
    @ApiModelProperty(value = "职位描述")
    private String positionDescription;

    @NotBlank(message = "工作地址不能为空", groups = {CreateGroup.class})
    @ApiModelProperty(value = "工作地址")
    private String workAddress;

    @NotBlank(message = "职位诱惑不能为空", groups = {CreateGroup.class})
    @ApiModelProperty(value = "职位诱惑")
    private String temptation;

    @NotBlank(message = "职位所属部门不能为空", groups = {CreateGroup.class})
    @ApiModelProperty(value = "职位所属部门")
    private String department;

    @Range(min = 0, max = 1, message = "排序值非法")
    @ApiModelProperty(value = "时间排序 0-默认 1-最新")
    private Integer orderBy;
}
