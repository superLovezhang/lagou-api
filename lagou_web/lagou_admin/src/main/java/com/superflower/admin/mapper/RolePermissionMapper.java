package com.superflower.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.superflower.admin.entity.Role;
import com.superflower.admin.entity.RolePermission;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zz
 * @since 2020-08-15
 */
public interface RolePermissionMapper extends BaseMapper<RolePermission> {

    List<Role> ListByPermissionId(String permissionId);
}
