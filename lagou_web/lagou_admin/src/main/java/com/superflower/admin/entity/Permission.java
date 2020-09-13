package com.superflower.admin.entity;

import java.util.ArrayList;
import java.util.Date;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author zz
 * @since 2020-08-14
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="Permission对象", description="")
public class Permission implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "权限主键")
    private String permissionId;

    @ApiModelProperty("子菜单 展示用")
    @TableField(exist = false)
    private ArrayList<Permission> children = new ArrayList<>();

    @ApiModelProperty(value = "权限名")
    private String name;

    @ApiModelProperty(value = "权限url")
    private String url;

    @ApiModelProperty(value = "图标")
    private String icon;

    @ApiModelProperty(value = "资源类型 0-目录 1-菜单 2-按钮")
    private Integer type;

    @ApiModelProperty(value = "父id")
    private String pid;

    @ApiModelProperty(value = "权限描述")
    private String description;

    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    @ApiModelProperty(value = "修改时间")
    private Date updateTime;
}
