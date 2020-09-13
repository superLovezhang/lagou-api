package com.superflower.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.superflower.admin.entity.Admin;
import com.superflower.admin.entity.Role;
import com.superflower.admin.mapper.AdminMapper;
import com.superflower.admin.service.IAdminService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.superflower.admin.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements IAdminService {
    @Autowired
    private IRoleService roleService;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        QueryWrapper<Admin> wrapper = new QueryWrapper<>();
        wrapper.eq("account", s);
        Admin admin = baseMapper.selectOne(wrapper);
        List<Role> roles = roleService.findRolesByAdminId(admin.getAdminId());
        admin.setRoles(roles);
        return admin;
    }

    @Override
    public Page<Admin> listPage(Long page, Long num, String adminName) {
        Page<Admin> adminPage = new Page<>(page, num);
        QueryWrapper<Admin> wrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(adminName)) wrapper.like("admin_name", adminName);
        return baseMapper.selectPage(adminPage, wrapper);
    }

    @Override
    public Admin selectByAccount(String account) {
        QueryWrapper<Admin> wrapper = new QueryWrapper<>();
        wrapper.eq("account", account);
        return baseMapper.selectOne(wrapper);
    }
}
