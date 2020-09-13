package com.superflower.admin.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.superflower.admin.entity.Permission;
import com.superflower.admin.entity.vo.PermissionVo;
import com.superflower.admin.service.IPermissionService;
import com.superflower.admin.service.IRolePermissionService;
import com.superflower.common.entity.vo.R;
import com.superflower.common.entity.vo.StatusCode;
import com.superflower.common.validate.UpdateGroup;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zz
 * @since 2020-08-14
 */
@Api(tags = "资源权限管理")
@RestController
@RequestMapping("/api/permission")
public class PermissionController {
    @Autowired
    private IPermissionService permissionService;

    @Autowired
    private IRolePermissionService rolePermissionService;

    @ApiOperation("获取所有资源权限")
    @GetMapping("/tree")
    public R list() {
        ArrayList<Permission> list = permissionService.tree();
        return R.success(StatusCode.SUCCESS, list);
    }

    @ApiOperation("添加资源权限")
    @PutMapping("/save")
    public R save(@Validated @RequestBody PermissionVo permissionVo) {
        Permission permission = new Permission();
        BeanUtils.copyProperties(permissionVo, permission);
        boolean save = permissionService.save(permission);
        return save ? R.success() : R.error();
    }

    @ApiOperation("删除资源权限")
    @DeleteMapping("/remove/{permissionId}")
    public R remove(@PathVariable String permissionId) {
        // 删除资源权限 先将角色权限表所有相关数据删除
        rolePermissionService.deleteByPermissionId(permissionId);
        // 再删除本表数据及其所有子权限数据
        permissionService.deleteAllById(permissionId);
        return R.success();
    }

    @ApiOperation("修改资源权限信息")
    @PostMapping("/modify/{permissionId}")
    public R modify(@PathVariable String permissionId, @Validated(UpdateGroup.class) @RequestBody PermissionVo permissionVo) {
        Permission permission = new Permission();
        BeanUtils.copyProperties(permissionVo, permission);
        permission.setPermissionId(permissionId);
        boolean b = permissionService.updateById(permission);
        return b ? R.success() : R.error();
    }
}

