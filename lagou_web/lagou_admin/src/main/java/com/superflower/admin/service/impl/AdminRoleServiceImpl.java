package com.superflower.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.superflower.admin.entity.AdminRole;
import com.superflower.admin.mapper.AdminRoleMapper;
import com.superflower.admin.service.IAdminRoleService;
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
public class AdminRoleServiceImpl extends ServiceImpl<AdminRoleMapper, AdminRole> implements IAdminRoleService {
    @Override
    public void deleteByRoleId(String roleId) {
        QueryWrapper<AdminRole> wrapper = new QueryWrapper<>();
        wrapper.eq("role_id", roleId);
        baseMapper.delete(wrapper);
    }

    @Override
    public ArrayList<String> roleIdsByAdminId(String adminId) {
        QueryWrapper<AdminRole> wrapper = new QueryWrapper<>();
        wrapper.eq("admin_id", adminId);
        List<AdminRole> adminRoles = baseMapper.selectList(wrapper);
        ArrayList<String> ids = new ArrayList<>();
        for (AdminRole adminRole : adminRoles) {
            ids.add(adminRole.getRoleId());
        }
        return ids;
    }

    @Override
    public void deleteByAdminId(String adminId) {
        QueryWrapper<AdminRole> wrapper = new QueryWrapper<>();
        wrapper.eq("admin_id", adminId);
        baseMapper.delete(wrapper);
    }
}
