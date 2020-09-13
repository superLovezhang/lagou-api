package com.superflower.admin.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.superflower.admin.entity.Role;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zz
 * @since 2020-08-14
 */
public interface IRoleService extends IService<Role> {

    List<Role> findRolesByAdminId(String adminId);

    Page<Role> listByName(Long page, Long num, String roleName);
}
