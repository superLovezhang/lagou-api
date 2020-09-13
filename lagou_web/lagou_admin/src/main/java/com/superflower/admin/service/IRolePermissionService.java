package com.superflower.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.superflower.admin.entity.Permission;
import com.superflower.admin.entity.Role;
import com.superflower.admin.entity.RolePermission;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zz
 * @since 2020-08-15
 */
public interface IRolePermissionService extends IService<RolePermission> {

    List<Role> ListByPermissionId(String permissionId);

    void deleteByRoleId(String roleId);

    void deleteByPermissionId(String permissionId);

    ArrayList<String> listByRoleId(String roleId);

    ArrayList<Permission> treeByAdminId(String adminId);
}
