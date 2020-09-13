package com.superflower.front.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.superflower.common.entity.Position;
import com.superflower.common.entity.vo.PositionVo;
import com.superflower.common.utils.CodeUtils;
import com.superflower.front.mapper.PositionMapper;
import com.superflower.front.service.IPositionService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.util.HashMap;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zz
 * @since 2020-08-08
 */
@Service
public class PositionServiceImpl extends ServiceImpl<PositionMapper, Position> implements IPositionService {

    @Override
    public Page<Position> findPageByQuery(Long page, Long num, PositionVo positionVo) {
        String name = positionVo.getPositionName();
        Integer orderBy = positionVo.getOrderBy();
        Integer salaryBegin = positionVo.getSalaryBegin();
        Integer salaryEnd = positionVo.getSalaryEnd();

        // 将一些不用做查询的字段 进行填null
        positionVo.setPositionName(null);
        positionVo.setOrderBy(null);
        positionVo.setSalaryBegin(null);
        positionVo.setSalaryEnd(null);
        positionVo.setPositionKeyword(null);
        positionVo.setTemptation(null);
        positionVo.setDepartment(null);
        positionVo.setPositionDescription(null);

        Page<Position> positionPage = new Page<>(page, num);
        QueryWrapper<Position> wrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(name))  wrapper.like("position_name", name);
        if (orderBy != null) {
            if (orderBy == 0) wrapper.orderByAsc("create_time");
            if (orderBy == 1) wrapper.orderByDesc("create_time");
        }
        if (salaryBegin != null) {
            wrapper.ge("salary_begin", salaryBegin);
            wrapper.le("salary_begin", salaryEnd);
        }
        HashMap<String, Object> map = CodeUtils.entityToMap(positionVo);
        wrapper.allEq(map, false);
        return baseMapper.selectPage(positionPage, wrapper);
    }
}
