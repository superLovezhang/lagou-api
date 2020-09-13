package com.superflower.front.controller;

import com.superflower.common.entity.Product;
import com.superflower.common.entity.vo.ProductVo;
import com.superflower.common.entity.vo.R;
import com.superflower.common.entity.vo.StatusCode;
import com.superflower.common.validate.CreateGroup;
import com.superflower.front.service.IEmployeeService;
import com.superflower.front.service.IProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Api(tags = "公司产品数据操作")
@RestController
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private IProductService productService;

    @Autowired
    private IEmployeeService employeeService;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @ApiOperation("增加公司产品")
    @PutMapping("/save")
    public R save(HttpServletRequest request, @Validated(CreateGroup.class) @RequestBody ProductVo productVo, String userId) {
        String companyId = employeeService.findCompanyId(userId, true);
        if (StringUtils.isEmpty(companyId)) return R.error(StatusCode.NOT_HAVE_COMPANY);
        Product product = new Product();
        BeanUtils.copyProperties(productVo, product);
        product.setCompanyId(companyId);
        boolean save = productService.save(product);
        return save ? R.success() : R.error();
    }

    @ApiOperation("删除公司产品")
    @DeleteMapping("/remove/{productId}")
    public R remove(HttpServletRequest request, @PathVariable String productId, String userId) {
        String companyId = employeeService.findCompanyId(userId, true);
        if (StringUtils.isEmpty(companyId)) return R.error(StatusCode.NOT_HAVE_COMPANY);
        redisTemplate.delete("company::" + companyId);
        String productIdDb = productService.findCompanyId(productId);
        if (!productIdDb.equals(companyId)) return R.error(StatusCode.UPDATE_PRODUCT_ERROR);
        boolean b = productService.removeById(productId);
        return b ? R.success() : R.error();
    }

    @ApiOperation("修改产品信息")
    @PostMapping("/modify/{id}")
    public R modify(HttpServletRequest request, @RequestBody ProductVo productVo, @PathVariable String id, String userId) {
        String companyId = employeeService.findCompanyId(userId, true);
        if (StringUtils.isEmpty(companyId)) return R.error(StatusCode.NOT_HAVE_COMPANY);
        redisTemplate.delete("company::" + companyId);
        String productIdDb = productService.findCompanyId(id);
        if (!productIdDb.equals(companyId)) return R.error(StatusCode.UPDATE_PRODUCT_ERROR);
        Product product = new Product();
        BeanUtils.copyProperties(productVo, product);
        product.setId(id);
        boolean b = productService.updateById(product);
        return b ? R.success() : R.error();
    }
}
