package com.superflower.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.superflower.admin.entity.Permission;
import com.superflower.admin.mapper.PermissionMapper;
import com.superflower.admin.service.IPermissionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
 * @since 2020-08-14
 */
@Service
@Transactional
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements IPermissionService {

    @Override
    public Permission getOneByUrl(String requestUrl) {
        QueryWrapper<Permission> wrapper = new QueryWrapper<>();
        wrapper.eq("url", requestUrl);
        return baseMapper.selectOne(wrapper);
    }

    @Override
    public void deleteAllById(String permissionId) {
        QueryWrapper<Permission> wrapper = new QueryWrapper<>();
        wrapper.eq("permission_id", permissionId).or().eq("pid", permissionId);
        baseMapper.delete(wrapper);
    }

    @Override
    public ArrayList<Permission> tree() {
        List<Permission> permissions = baseMapper.selectList(null);
        ArrayList<Permission> list = new ArrayList<>();
        for (Permission permission : permissions) {
            if (permission.getPid().equals("0")) {
                list.add(permission);
                getTree(permissions, permission);
            }
        }
        return list;
    }

    private void getTree(List<Permission> source, Permission target) {
        for (Permission permission : source) {
            if (permission.getPid().equals(target.getPermissionId())) {
                ArrayList<Permission> children = target.getChildren();
                children.add(permission);
                getTree(source, permission);
            }
        }
    }
}
