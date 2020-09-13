package com.superflower.front.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.superflower.common.entity.Company;
import com.superflower.common.entity.vo.CompanyVo;
import com.superflower.common.utils.CodeUtils;
import com.superflower.front.mapper.CompanyMapper;
import com.superflower.front.service.ICompanyService;
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
public class CompanyServiceImpl extends ServiceImpl<CompanyMapper, Company> implements ICompanyService {
    @Override
    public Company findByName(String companyName) {
        QueryWrapper<Company> wrapper = new QueryWrapper<>();
        wrapper.eq("company_name", companyName);
        return baseMapper.selectOne(wrapper);
    }

    @Override
    public Page<Company> listByQuery(Long page, Long num, CompanyVo companyVo) {
        Page<Company> companyPage = new Page<>(page, num);
        QueryWrapper<Company> wrapper = new QueryWrapper<>();
        wrapper.allEq(CodeUtils.entityToMap(companyVo));
        return baseMapper.selectPage(companyPage, wrapper);
    }
}
