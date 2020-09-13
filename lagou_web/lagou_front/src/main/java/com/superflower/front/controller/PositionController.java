package com.superflower.front.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.superflower.common.entity.Company;
import com.superflower.common.entity.Employee;
import com.superflower.common.entity.Position;
import com.superflower.common.entity.vo.PositionVo;
import com.superflower.common.entity.vo.R;
import com.superflower.common.entity.vo.StatusCode;
import com.superflower.common.validate.CreateGroup;
import com.superflower.common.validate.UpdateGroup;
import com.superflower.front.service.ICompanyService;
import com.superflower.front.service.IEmployeeService;
import com.superflower.front.service.IPositionService;
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
import javax.validation.groups.Default;
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
@Api(tags = "职位数据操作")
@RestController
@RequestMapping("/position")
public class PositionController {
    @Autowired
    private IPositionService positionService;

    @Autowired
    private IEmployeeService employeeService;

    @Autowired
    private ICompanyService companyService;

    @ApiOperation("发布职位")
    @PutMapping("/publish")
    public R publish(HttpServletRequest request, @Validated(CreateGroup.class) @RequestBody PositionVo positionVo, String userId) {
        String companyId = employeeService.findCompanyId(userId, true);
        if (StringUtils.isEmpty(companyId)) return R.error(StatusCode.NOT_HAVE_COMPANY);
        // 获取到当前员工所属公司信息 赋值
        Company company = companyService.getById(companyId);
        Position position = new Position();
        BeanUtils.copyProperties(positionVo, position);
        position.setCompanyId(companyId);
        position.setCompanyName(company.getCompanyName());
        position.setAbbreviation(company.getAbbreviation());
        position.setLogo(company.getLogo());
        position.setStatus(company.getStatus());
        position.setField(company.getField());
        position.setScale(company.getScale());
        position.setFinancing(company.getFinancing());
        position.setPublishEmployee(userId);

        boolean save = positionService.save(position);
        return save ? R.success() : R.error();
    }

    @ApiOperation("根据Id查询职位信息")
    @Cacheable(value = "position", key = "#id")
    @GetMapping("/watch/{id}")
    public R watch(@PathVariable String id) {
        Position position = positionService.getById(id);
        String employeeId = position.getPublishEmployee();
        Employee employee = employeeService.getById(employeeId);
        position.setPublishEmployeeInfo(employee);
        // 查看数加1
        Position position1 = new Position();
        position1.setId(id);
        position1.setWatchNum(position.getWatchNum() + 1);
        positionService.updateById(position1);
        return R.success(StatusCode.SUCCESS, position);
    }

    @ApiOperation("根据查询条件查询职位列表")
    @GetMapping("/list/{page}/{num}")
    public R list(@Validated @RequestBody PositionVo positionVo, @PathVariable Long page, @PathVariable Long num) {
        page = page >= 1 ? page - 1 : 0;
        num = num >= 1 ? num : 1;
        Page<Position> result = positionService.findPageByQuery(page, num, positionVo);

        List<Position> rows = result.getRecords();
        long pages = result.getPages();
        long current = result.getCurrent();
        HashMap<String, Object> map = new HashMap<>();
        map.put("current", current);
        map.put("pages", pages);
        map.put("rows", rows);

        return R.success(StatusCode.SUCCESS, map);
    }

    @ApiOperation("修改职位信息")
    @CacheEvict(value = "position", key = "#positionId")
    @PostMapping("/modify/{positionId}")
    public R modify(HttpServletRequest request,
                    @Validated(UpdateGroup.class) @RequestBody PositionVo positionVo,
                    @PathVariable String positionId,
                    String userId) {
        Position positionById = positionService.getById(positionId);
        if (positionById == null) return R.error(StatusCode.POSITION_NOT_EXIST);
        if (!positionById.getPublishEmployee().equals(userId)) return R.error(StatusCode.UPDATE_POSITION_ERROR);
        Position position = new Position();
        BeanUtils.copyProperties(positionVo, position);
        position.setId(positionId);
        boolean b = positionService.updateById(position);
        return b ? R.success() : R.error();
    }

    @ApiOperation("删除职位")
    @CacheEvict(value = "position", key = "#positionId")
    @DeleteMapping("/remove/{positionId}")
    public R remove(HttpServletRequest request, @PathVariable String positionId, String userId) {
        Position positionById = positionService.getById(positionId);
        if (positionById == null) return R.error(StatusCode.POSITION_NOT_EXIST);
        if (!positionById.getPublishEmployee().equals(userId)) return R.error(StatusCode.UPDATE_POSITION_ERROR);
        boolean b = positionService.removeById(positionId);
        return b ? R.success() : R.error();
    }
}

