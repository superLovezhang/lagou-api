package com.superflower.common.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author zz
 * @since 2020-08-09
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="Company对象", description="")
public class Company implements Serializable {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "公司主键")
    private String id;

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

    @ApiModelProperty(value = "公司地址")
    private String companyAddress;


    @ApiModelProperty(value = "公司福利 逗号分隔")
    private String welfare;

    @ApiModelProperty(value = "公司介绍")
    private String companyIntroduction;

    @ApiModelProperty(value = "是否删除 0-未删除 1-已删除")
    private Integer isDeleted;

    @TableField(exist = false)
    @ApiModelProperty(value = "公司产品列表")
    private List<Product> productList;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty(value = "创建日期")
    private Date createTime;


}
