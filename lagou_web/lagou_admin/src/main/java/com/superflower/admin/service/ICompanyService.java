package com.superflower.admin.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.superflower.common.entity.Company;
import com.superflower.common.entity.vo.CompanyVo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zz
 * @since 2020-08-08
 */
public interface ICompanyService extends IService<Company> {

    Page<Company> searchList(Long page, Long num, String status);
}
