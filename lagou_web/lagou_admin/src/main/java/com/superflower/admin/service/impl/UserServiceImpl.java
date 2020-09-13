package com.superflower.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.superflower.admin.mapper.UserMapper;
import com.superflower.admin.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.superflower.common.entity.User;
import com.superflower.common.entity.vo.UserSearchVo;
import com.superflower.common.utils.CodeUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zz
 * @since 2020-08-08
 */
@Service
@Transactional
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Override
    public Page<User> searchList(Long page, Long num, UserSearchVo userSearchVo) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        if (userSearchVo != null) {
            String username = userSearchVo.getUsername();
            userSearchVo.setUsername(null);
            if (!StringUtils.isEmpty(username)) wrapper.likeRight("username", username);
        }

        wrapper.allEq(CodeUtils.entityToMap(userSearchVo));
        Page<User> userPage = new Page<>(page, num);

        return baseMapper.selectPage(userPage, wrapper);
    }
}
