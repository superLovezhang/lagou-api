package com.superflower.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.superflower.admin.entity.Permission;

import java.util.ArrayList;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zz
 * @since 2020-08-14
 */
public interface IPermissionService extends IService<Permission> {

    Permission getOneByUrl(String requestUrl);

    void deleteAllById(String permissionId);

    ArrayList<Permission> tree();
}
