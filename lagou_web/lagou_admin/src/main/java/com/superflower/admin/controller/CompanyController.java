package com.superflower.admin.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.superflower.admin.service.ICompanyService;
import com.superflower.common.entity.Company;
import com.superflower.common.entity.vo.CompanyVo;
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
 * 前端控制器
 * </p>
 *
 * @author zz
 * @since 2020-08-08
 */
@Api(tags = "企业管理")
@RestController
@RequestMapping("/api/company")
public class CompanyController {
    @Autowired
    private ICompanyService companyService;

    @ApiOperation("企业列表")
    @PostMapping("/list/{page}/{num}")
    public R list(@PathVariable Long page, @PathVariable Long num, @RequestParam String status) {
        page = page >= 1 ? page : 1;
        num = num >= 1 ? num : 1;
        Page<Company> companyPage = companyService.searchList(page, num, status);

        List<Company> rows = companyPage.getRecords();
        long pages = companyPage.getPages();
        long current = companyPage.getCurrent();
        HashMap<String, Object> map = new HashMap<>();
        map.put("rows", rows);
        map.put("pages", pages);
        map.put("current", current);

        return R.success(StatusCode.SUCCESS, map);
    }

    @ApiOperation("企业认证")
    @PostMapping("/certification/{companyId}")
    public R certification(@PathVariable String companyId) {
        Company company = new Company();
        company.setId(companyId);
        company.setStatus(1);
        boolean b = companyService.updateById(company);
        return b ? R.success() : R.error();
    }

    @ApiOperation("修改企业信息")
    @PostMapping("/modify/{companyId}")
    public R modify(@PathVariable String companyId, @Validated @RequestBody CompanyVo companyVo) {
        Company company = new Company();
        BeanUtils.copyProperties(companyVo, company);
        company.setId(companyId);
        boolean b = companyService.updateById(company);
        return b ? R.success() : R.error();
    }
}

