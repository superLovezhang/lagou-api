package com.superflower.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.superflower.admin.mapper.EmployeeMapper;
import com.superflower.admin.service.IEmployeeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.superflower.common.entity.Employee;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements IEmployeeService {

    @Override
    public Page<Employee> searchList(Long page, Long num, Integer status) {
        Page<Employee> employeePage = new Page<>(page, num);
        QueryWrapper<Employee> wrapper = new QueryWrapper<>();
        wrapper.eq("status", status);
        return baseMapper.selectPage(employeePage, wrapper);
    }
}
