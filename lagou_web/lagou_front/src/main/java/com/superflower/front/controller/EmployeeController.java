package com.superflower.front.controller;


import com.superflower.common.entity.Employee;
import com.superflower.common.entity.User;
import com.superflower.common.entity.vo.EmployeeVo;
import com.superflower.common.entity.vo.R;
import com.superflower.common.entity.vo.StatusCode;
import com.superflower.front.service.IEmployeeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zz
 * @since 2020-08-08
 */
@Api(tags = "员工数据操作")
@RestController
@RequestMapping("/employee")
public class EmployeeController {
    @Autowired
    private IEmployeeService employeeService;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @ApiOperation("创建员工")
    @PutMapping("/create")
    public R create(HttpServletRequest request, @Validated @RequestBody EmployeeVo employeeVo, String userId) {
        Employee employee = new Employee();
        BeanUtils.copyProperties(employeeVo, employee);
        employee.setId(userId);
        employeeService.removeById(userId);
        boolean b = employeeService.save(employee);
        return b ? R.success() : R.error();
    }

    @ApiOperation("根据id查询员工信息")
    @GetMapping("/info/{id}")
    public R info(@PathVariable String id) {
        Employee employee = (Employee) redisTemplate.opsForValue().get("employee::" + id);
        if (employee == null) employee = employeeService.getById(id);
        return R.success(StatusCode.SUCCESS, employee);
    }

    @ApiOperation("更改员工信息")
    @PostMapping("/modify")
    public R modify(HttpServletRequest request, @RequestBody EmployeeVo employeeVo, String userId) {
        redisTemplate.delete("employee::" + userId);
        Employee employee = new Employee();
        BeanUtils.copyProperties(employeeVo, employee);
        employee.setId(userId);
        employee.setStatus(0);
        boolean b = employeeService.updateById(employee);
        return b ? R.success() : R.error();
    }
}

