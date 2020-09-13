package com.superflower.admin.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.superflower.admin.entity.Role;
import com.superflower.admin.entity.RolePermission;
import com.superflower.admin.entity.vo.RoleVo;
import com.superflower.admin.service.IAdminRoleService;
import com.superflower.admin.service.IRolePermissionService;
import com.superflower.admin.service.IRoleService;
import com.superflower.common.entity.vo.R;
import com.superflower.common.entity.vo.StatusCode;
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
 * 前端控制器
 * </p>
 *
 * @author zz
 * @since 2020-08-14
 */
@Api(tags = "角色管理")
@RestController
@RequestMapping("/api/role")
public class RoleController {
    @Autowired
    private IRoleService roleService;

    @Autowired
    private IRolePermissionService rolePermissionService;

    @Autowired
    private IAdminRoleService adminRoleService;

    @ApiOperation("获取所有角色")
    @GetMapping("/list/{page}/{num}")
    public R list(@PathVariable Long page, @PathVariable Long num, @RequestParam(required = false) String roleName) {
        page = page >= 1 ? page : 1;
        num = num >= 1 ? num : 1;
        Page<Role> rolePage = roleService.listByName(page, num, roleName);

        List<Role> rows = rolePage.getRecords();
        for (Role row : rows) {
            String roleId = row.getRoleId();
            ArrayList<String> permissionIds = rolePermissionService.listByRoleId(roleId);
            row.setPermissionIds(permissionIds);
        }
        long current = rolePage.getCurrent();
        long pages = rolePage.getPages();
        HashMap<String, Object> map = new HashMap<>();
        map.put("rows", rows);
        map.put("current", current);
        map.put("pages", pages);
        return R.success(StatusCode.SUCCESS, map);
    }

    @ApiOperation("添加角色")
    @PutMapping("/save")
    public R save(@Validated @RequestBody RoleVo roleVo) {
        Role role = new Role();
        BeanUtils.copyProperties(roleVo, role);
        boolean save = roleService.save(role);
        return save ? R.success() : R.error();
    }

    @ApiOperation("编辑角色")
    @PostMapping("/modify/{roleId}")
    public R modify(@PathVariable String roleId, @Validated @RequestBody RoleVo roleVo) {
        Role role = new Role();
        BeanUtils.copyProperties(roleVo, role);
        role.setRoleId(roleId);
        boolean b = roleService.updateById(role);
        return b ? R.success() : R.error();
    }

    @ApiOperation("删除角色")
    @DeleteMapping("/remove/{roleId}")
    public R remove(@PathVariable String roleId) {
        // 删除关联表中所有包含该角色的数据
        adminRoleService.deleteByRoleId(roleId);
        // 再删除本表数据
        boolean b = roleService.removeById(roleId);
        return b ? R.success() : R.error();
    }

    @ApiOperation("给角色修改权限")
    @PostMapping("/addPermission/{roleId}")
    public R addPermission(@PathVariable String roleId, @RequestBody List<String> permissionIds) {
        // 修改之前 先给角色的所有权限删除
        rolePermissionService.deleteByRoleId(roleId);
        ArrayList<RolePermission> list = new ArrayList<>();
        for (String permissionId : permissionIds) {
            RolePermission rolePermission = new RolePermission();
            rolePermission.setRoleId(roleId);
            rolePermission.setPermissionId(permissionId);
            list.add(rolePermission);
        }
        boolean b = rolePermissionService.saveBatch(list);
        return b ? R.success() : R.error();
    }
}

