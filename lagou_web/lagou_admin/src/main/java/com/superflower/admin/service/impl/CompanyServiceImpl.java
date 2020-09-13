package com.superflower.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.superflower.admin.mapper.CompanyMapper;
import com.superflower.admin.service.ICompanyService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.superflower.common.entity.Company;
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
public class CompanyServiceImpl extends ServiceImpl<CompanyMapper, Company> implements ICompanyService {

    @Override
    public Page<Company> searchList(Long page, Long num, String status) {
        Page<Company> companyPage = new Page<>(page, num);
        QueryWrapper<Company> wrapper = new QueryWrapper<>();
        wrapper.eq("status", status);
        return baseMapper.selectPage(companyPage, wrapper);
    }
}
