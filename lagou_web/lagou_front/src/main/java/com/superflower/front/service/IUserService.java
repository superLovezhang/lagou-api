package com.superflower.front.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.superflower.common.entity.User;
import com.superflower.common.entity.vo.RegisterVo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zz
 * @since 2020-08-08
 */
public interface IUserService extends IService<User> {

    User findByPhone(String phone);

    void saveRegister(RegisterVo registerVo);
}
