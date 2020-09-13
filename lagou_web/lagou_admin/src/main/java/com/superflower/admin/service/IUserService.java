package com.superflower.admin.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.superflower.common.entity.User;
import com.superflower.common.entity.vo.UserSearchVo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zz
 * @since 2020-08-08
 */
public interface IUserService extends IService<User> {

    Page<User> searchList(Long page, Long num, UserSearchVo userSearchVo);
}
