package com.superflower.common.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.superflower.common.entity.vo.EmployeeVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * <p>
 * 
 * </p>
 *
 * @author zz
 * @since 2020-08-09
 */
@Data
@ToString
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="Position对象", description="")
public class Position implements Serializable {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "职位id")
    private String id;

    @ApiModelProperty(value = "企业id")
    private String companyId;

    @ApiModelProperty(value = "公司名称")
    private String companyName;

    @ApiModelProperty(value = "公司简称")
    private String abbreviation;

    @ApiModelProperty(value = "公司logo地址 默认null")
    private String logo;

    @ApiModelProperty(value = "行业领域 0-移动互联网 1-电商 2-金融 3-企业服务 4-教育 5-文娱|内容 6-游戏 7-消费生活 8-硬件")
    private Integer field;

    @ApiModelProperty(value = "公司规模  0-少于15人 1-15-50人 2-50-150人 3-150-500人 4-500-2000人 5-2000人以上")
    private Integer scale;

    @ApiModelProperty(value = "融资阶段 0-未融资 1-天使轮 2-A轮 3-B轮 4-C轮 5-D轮及以上 6-上市公司 7-不需要融资")
    private Integer financing;

    @ApiModelProperty(value = "认证状态 0-未认证 1-已认证")
    private Integer status;

    @ApiModelProperty(value = "职位名称")
    private String positionName;

    @ApiModelProperty(value = "学历 0-大专 1-本科 2-硕士 3-博士 4-不要求")
    private Integer education;

    @ApiModelProperty(value = "经验 0- 应届毕业生 1- 3年及其以下 2- 3-5年 3- 5-10年 4- 10年以上 5-不要求")
    private Integer experience;

    @ApiModelProperty(value = "招聘类型 0-全职 1-兼职 3-实习")
    private Integer recruitmentType;

    @ApiModelProperty(value = "薪资起始")
    private Integer salaryBegin;

    @ApiModelProperty(value = "薪资上限")
    private Integer salaryEnd;

    @ApiModelProperty(value = "职位关键词 逗号分隔")
    private String positionKeyword;

    @ApiModelProperty(value = "职位描述")
    private String positionDescription;

    @ApiModelProperty(value = "工作地址")
    private String workAddress;

    @ApiModelProperty(value = "职位诱惑")
    private String temptation;

    @ApiModelProperty(value = "职位所属部门")
    private String department;

    @ApiModelProperty(value = "发布者id")
    private String publishEmployee;

    @TableField(exist = false)
    @ApiModelProperty(value = "发布者信息")
    private Employee publishEmployeeInfo;

    @ApiModelProperty(value = "查阅数量")
    private Integer watchNum;

    @ApiModelProperty(value = "是否删除 0-未删除 1-已删除")
    private Integer isDeleted;

    @TableField(exist = false)
    @ApiModelProperty(value = "所属公司信息")
    private Company companyInfo;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty(value = "创建时间")
    private Date createTime;


}
