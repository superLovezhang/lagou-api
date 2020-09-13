package com.superflower.admin.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.superflower.admin.service.IEmployeeService;
import com.superflower.common.entity.Employee;
import com.superflower.common.entity.vo.EmployeeVo;
import com.superflower.common.entity.vo.R;
import com.superflower.common.entity.vo.StatusCode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zz
 * @since 2020-08-08
 */
@Api(tags = "员工管理")
@RestController
@RequestMapping("/api/employee")
public class EmployeeController {
    @Autowired
    private IEmployeeService employeeService;

    @ApiOperation("员工列表")
    @GetMapping("/list/{page}/{num}")
    public R list(@PathVariable Long page, @PathVariable Long num, @RequestParam(required = false, defaultValue = "0") Integer status) {
        page = page >= 1 ? page : 1;
        num = num >= 1 ? num : 1;
        Page<Employee> employeePage = employeeService.searchList(page, num, status);

        List<Employee> rows = employeePage.getRecords();
        long pages = employeePage.getPages();
        long current = employeePage.getCurrent();
        HashMap<String, Object> map = new HashMap<>();
        map.put("rows", rows);
        map.put("pages", pages);
        map.put("current", current);

        return R.success(StatusCode.SUCCESS, map);
    }

    @ApiOperation("审核员工信息")
    @PostMapping("/review/{employeeId}")
    public R review(@PathVariable String employeeId) {
        Employee employee = new Employee();
        employee.setId(employeeId);
        employee.setStatus(1);
        boolean b = employeeService.updateById(employee);
        return b ? R.success() : R.error();
    }

    @ApiOperation("修改员工信息")
    @PostMapping("/modify/{employeeId}")
    public R modify(@PathVariable String employeeId, @Validated @RequestBody EmployeeVo employeeVo) {
        Employee employee = new Employee();
        BeanUtils.copyProperties(employeeVo, employee);
        employee.setId(employeeId);
        boolean b = employeeService.updateById(employee);
        return b ? R.success() : R.error();
    }
}

