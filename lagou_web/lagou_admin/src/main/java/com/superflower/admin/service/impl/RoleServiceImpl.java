package com.superflower.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.superflower.admin.entity.Role;
import com.superflower.admin.mapper.RoleMapper;
import com.superflower.admin.service.IRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zz
 * @since 2020-08-14
 */
@Service
@Transactional
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {

    @Override
    public List<Role> findRolesByAdminId(String adminId) {
        return baseMapper.findRolesByAdminId(adminId);
    }

    @Override
    public Page<Role> listByName(Long page, Long num, String roleName) {
        Page<Role> rolePage = new Page<>(page, num);
        QueryWrapper<Role> wrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(roleName)) wrapper.like("role_name", roleName);
        return baseMapper.selectPage(rolePage, wrapper);
    }
}
