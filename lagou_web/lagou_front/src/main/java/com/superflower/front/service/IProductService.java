package com.superflower.front.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.superflower.common.entity.Product;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zz
 * @since 2020-08-09
 */
public interface IProductService extends IService<Product> {

    List<Product> findListByCompanyId(String id);

    String findCompanyId(String productId);
}
