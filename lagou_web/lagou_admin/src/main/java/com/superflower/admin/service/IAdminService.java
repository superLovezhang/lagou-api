package com.superflower.admin.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.superflower.admin.entity.Admin;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zz
 * @since 2020-08-14
 */
public interface IAdminService extends IService<Admin>, UserDetailsService {

    Page<Admin> listPage(Long page, Long num, String adminName);

    Admin selectByAccount(String account);
}
