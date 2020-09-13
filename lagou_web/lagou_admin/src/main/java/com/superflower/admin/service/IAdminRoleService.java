package com.superflower.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.superflower.admin.entity.AdminRole;

import java.util.ArrayList;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zz
 * @since 2020-08-15
 */
public interface IAdminRoleService extends IService<AdminRole> {

    void deleteByRoleId(String roleId);

    ArrayList<String> roleIdsByAdminId(String adminId);

    void deleteByAdminId(String adminId);
}
