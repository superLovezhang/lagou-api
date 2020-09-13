package com.superflower.front.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.superflower.common.entity.Employee;
import com.superflower.front.mapper.EmployeeMapper;
import com.superflower.front.service.IEmployeeService;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zz
 * @since 2020-08-08
 */
@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements IEmployeeService {

    @Override
    public String findCompanyId(String userId, Boolean checkStatus) {
        QueryWrapper<Employee> wrapper = new QueryWrapper<>();
        wrapper.select("company_id", "status");
        wrapper.eq("id", userId);
        Employee employee = baseMapper.selectOne(wrapper);
        if (employee == null) return null;
        if (checkStatus && employee.getStatus() == 0) throw new RuntimeException("员工信息未认证");
        return employee.getCompanyId();
    }
}
