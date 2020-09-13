package com.superflower.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.superflower.admin.entity.Permission;
import com.superflower.admin.entity.Role;
import com.superflower.admin.entity.RolePermission;
import com.superflower.admin.mapper.RolePermissionMapper;
import com.superflower.admin.service.IAdminRoleService;
import com.superflower.admin.service.IPermissionService;
import com.superflower.admin.service.IRolePermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zz
 * @since 2020-08-15
 */
@Service
@Transactional
public class RolePermissionServiceImpl extends ServiceImpl<RolePermissionMapper, RolePermission> implements IRolePermissionService {
    @Autowired
    private IAdminRoleService adminRoleService;

    @Autowired
    private IPermissionService permissionService;

    @Override
    public List<Role> ListByPermissionId(String permissionId) {
        return baseMapper.ListByPermissionId(permissionId);
    }

    @Override
    public void deleteByRoleId(String roleId) {
        QueryWrapper<RolePermission> wrapper = new QueryWrapper<>();
        wrapper.eq("role_id", roleId);
        baseMapper.delete(wrapper);
    }

    @Override
    public void deleteByPermissionId(String permissionId) {
        QueryWrapper<RolePermission> wrapper = new QueryWrapper<>();
        wrapper.eq("permission_id", permissionId);
        baseMapper.delete(wrapper);
    }

    @Override
    public ArrayList<String> listByRoleId(String roleId) {
        QueryWrapper<RolePermission> wrapper = new QueryWrapper<>();
        wrapper.eq("role_id", roleId);
        List<RolePermission> list = baseMapper.selectList(wrapper);
        ArrayList<String> ids = new ArrayList<>();
        for (RolePermission rolePermission : list) {
            String permissionId = rolePermission.getPermissionId();
            ids.add(permissionId);
        }
        return ids;
    }

    @Override
    public ArrayList<Permission> treeByAdminId(String adminId) {
        ArrayList<Permission> list = new ArrayList<>();
        ArrayList<String> roleIds = adminRoleService.roleIdsByAdminId(adminId);
        for (String roleId : roleIds) {
            ArrayList<String> permissionIds = listByRoleId(roleId);
            List<Permission> permissionList = permissionService.listByIds(permissionIds);
            list.addAll(permissionList);
        }
        // 最后将权限列表 封装成树形结构
        return listToTree(list, "0");
    }

    private ArrayList<Permission> listToTree(ArrayList<Permission> list, String target) {
        ArrayList<Permission> result = new ArrayList<>();
        for (Permission permission : list) {
            if (permission.getPid().equals(target)) {
                String permissionId = permission.getPermissionId();
                ArrayList<Permission> permissions = listToTree(list, permissionId);
                ArrayList<Permission> children = permission.getChildren();
                children.addAll(permissions);
                result.add(permission);
            }
        }
        return result;
    }
}
