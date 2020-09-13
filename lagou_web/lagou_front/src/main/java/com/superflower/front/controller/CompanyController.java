package com.superflower.front.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.superflower.common.entity.Company;
import com.superflower.common.entity.Employee;
import com.superflower.common.entity.Product;
import com.superflower.common.entity.vo.CompanyVo;
import com.superflower.common.entity.vo.R;
import com.superflower.common.entity.vo.StatusCode;
import com.superflower.front.service.ICompanyService;
import com.superflower.front.service.IEmployeeService;
import com.superflower.front.service.IProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotBlank;
import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author zz
 * @since 2020-08-08
 */
@Api(tags = "公司数据操作")
@RestController
@RequestMapping("/company")
public class CompanyController {
    @Autowired
    private ICompanyService companyService;

    @Autowired
    private IEmployeeService employeeService;

    @Autowired
    private IProductService productService;

    @ApiOperation("创建公司")
    @PutMapping("/save")
    public R save(HttpServletRequest request, @Validated @RequestBody CompanyVo companyVo, String userId) {
        // 查询该员工是否有所属公司 有就失败返回 没有继续
        String companyId = employeeService.findCompanyId(userId, false);
        if (!StringUtils.isEmpty(companyId)) return R.error(StatusCode.COMPANY_IS_EXIST);
        // 查询该公司是否已存在 存在就直接将公司id赋值给员工
        String companyName = companyVo.getCompanyName();
        Company company = companyService.findByName(companyName);
        if (company == null) {
            // 保存公司信息
            company = new Company();
            BeanUtils.copyProperties(companyVo, company);
            boolean save = companyService.save(company);
            if (!save) return R.error();
        }
        // 更改员工所属公司
        Employee employee = new Employee();
        employee.setId(userId);
        employee.setCompanyId(company.getId());
        boolean b = employeeService.updateById(employee);
        return b ? R.success() : R.error();
    }

    @ApiOperation("更改公司信息")
    @CacheEvict(value = "company", key = "#id")
    @PostMapping("/update/{id}")
    public R updateCompany(HttpServletRequest request,
                           @RequestBody CompanyVo companyVo,
                           @PathVariable String id,
                           String userId
    ) {
        String companyId = employeeService.findCompanyId(userId, true);
        if (companyId == null || !id.equals(companyId)) return R.error(StatusCode.UPDATE_COMPANY_ERROR);
        Company company = new Company();
        BeanUtils.copyProperties(companyVo, company);
        company.setId(id);
        boolean b = companyService.updateById(company);
        return b ? R.success() : R.error();
    }

    @ApiOperation("根据id查询公司信息")
    @Cacheable(value = "company", key = "#id")
    @GetMapping("/gatherCompany/{id}")
    public R gatherCompany(@PathVariable String id) {
        Company company = companyService.getById(id);
        List<Product> list = productService.findListByCompanyId(company.getId());
        company.setProductList(list);
        return R.success(StatusCode.SUCCESS, company);
    }

    @ApiOperation("查询公司列表")
    @GetMapping("/companyList/{page}/{num}")
    public R companyList(@PathVariable Long page, @PathVariable Long num, @RequestBody CompanyVo companyVo) {
        page = page >= 1 ? page - 1 : 0;
        num = num >= 1 ? num : 1;
        Page<Company> companyPage = companyService.listByQuery(page, num, companyVo);
        List<Company> rows = companyPage.getRecords();
        long pages = companyPage.getPages();
        long current = companyPage.getCurrent();
        HashMap<String, Object> map = new HashMap<>();
        map.put("pages", pages);
        map.put("current", current);
        map.put("rows", rows);

        return R.success(StatusCode.SUCCESS, map);
    }
}

