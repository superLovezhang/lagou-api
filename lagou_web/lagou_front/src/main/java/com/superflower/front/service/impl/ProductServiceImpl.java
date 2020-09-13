package com.superflower.front.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.superflower.common.entity.Product;
import com.superflower.front.mapper.ProductMapper;
import com.superflower.front.service.IProductService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zz
 * @since 2020-08-09
 */
@Service
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements IProductService {

    @Override
    public List<Product> findListByCompanyId(String id) {
        QueryWrapper<Product> wrapper = new QueryWrapper<>();
        wrapper.eq("company_id", id);
        return baseMapper.selectList(wrapper);
    }

    @Override
    public String findCompanyId(String productId) {
        QueryWrapper<Product> wrapper = new QueryWrapper<>();
        wrapper.select("company_id");
        wrapper.eq("id", productId);
        Product product = baseMapper.selectOne(wrapper);
        if (product == null) throw new RuntimeException("该产品信息不存在");
        return product.getCompanyId();
    }
}
