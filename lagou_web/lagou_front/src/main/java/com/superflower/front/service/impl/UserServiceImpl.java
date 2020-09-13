package com.superflower.front.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.superflower.common.entity.User;
import com.superflower.common.entity.vo.RegisterVo;
import com.superflower.front.mapper.UserMapper;
import com.superflower.front.service.IUserService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zz
 * @since 2020-08-08
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {
    @Override
    public User findByPhone(String phone) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("phone", phone);
        return baseMapper.selectOne(wrapper);
    }

    @Override
    public void saveRegister(RegisterVo registerVo) {
        User user = new User();
        BeanUtils.copyProperties(registerVo, user);
        baseMapper.insert(user);
    }
}
