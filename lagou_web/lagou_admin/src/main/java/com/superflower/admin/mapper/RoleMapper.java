package com.superflower.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.superflower.admin.entity.Role;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zz
 * @since 2020-08-14
 */
public interface RoleMapper extends BaseMapper<Role> {

    List<Role> findRolesByAdminId(String adminId);
}
