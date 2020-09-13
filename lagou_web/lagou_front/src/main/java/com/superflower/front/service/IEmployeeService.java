package com.superflower.front.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.superflower.common.entity.Employee;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zz
 * @since 2020-08-08
 */
public interface IEmployeeService extends IService<Employee> {

    String  findCompanyId(String userId, Boolean checkStatus);
}
