package com.superflower.admin.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.superflower.admin.service.IPositionService;
import com.superflower.common.entity.Position;
import com.superflower.common.entity.vo.PositionVo;
import com.superflower.common.entity.vo.R;
import com.superflower.common.entity.vo.StatusCode;
import com.superflower.common.validate.UpdateGroup;
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
@Api(tags = "职位管理")
@RestController
@RequestMapping("/api/position")
public class PositionController {
    @Autowired
    private IPositionService positionService;

    @ApiOperation("删除职位")
    @DeleteMapping("/remove/{positionId}")
    public R remove(@PathVariable String positionId) {
        boolean b = positionService.removeById(positionId);
        return b ? R.success() : R.error();
    }

    @ApiOperation("职位列表")
    @PostMapping("/list/{page}/{num}")
    public R list(@PathVariable Long page, @PathVariable Long num, @RequestBody PositionVo positionVo) {
        page = page >= 1 ? page : 1;
        num = num >= 1 ? num : 1;
        Page<Position> positionPage = positionService.searchList(page, num, positionVo);

        List<Position> rows = positionPage.getRecords();
        long pages = positionPage.getPages();
        long current = positionPage.getCurrent();
        HashMap<String, Object> map = new HashMap<>();
        map.put("rows", rows);
        map.put("pages", pages);
        map.put("current", current);

        return R.success(StatusCode.SUCCESS, map);
    }

    @ApiOperation("修改职位信息")
    @PostMapping("/modify/{positionId}")
    public R modify(@PathVariable String positionId, @Validated(UpdateGroup.class) @RequestBody PositionVo positionVo) {
        Position position = new Position();
        BeanUtils.copyProperties(positionVo, position);
        position.setId(positionId);
        boolean b = positionService.updateById(position);
        return b ? R.success() : R.error();
    }
}

