package com.superflower.common.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
//@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@ToString
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="User对象", description="")
public class User implements Serializable {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "用户主键")
    private String id;

    @ApiModelProperty(value = "用户名")
    private String username;

    @ApiModelProperty(value = "头像地址")
    private String avatar;

    @JsonIgnore
    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "性别 0-女 1-男")
    private Integer sex;

    @ApiModelProperty(value = "手机号")
    private String phone;

    @ApiModelProperty(value = "个人优势")
    private String advantage;

    @ApiModelProperty(value = "所在城市")
    private String city;

    @ApiModelProperty(value = "邮箱")
    private String email;

    @ApiModelProperty(value = "身份 0-学生 1-非学生")
    private Integer identity;

    @ApiModelProperty(value = "社交主页")
    private String socialHomepage;

    @ApiModelProperty(value = "生日")
    private String birthday;

    @ApiModelProperty(value = "简历地址 多个以逗号分隔")
    private String resume;

    @ApiModelProperty(value = "账户状态 0-正常 1-封禁")
    private Integer status;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    @ApiModelProperty(value = "更新日期")
    private Date updateTime;

    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty(value = "创建日期")
    private Date createTime;


}
