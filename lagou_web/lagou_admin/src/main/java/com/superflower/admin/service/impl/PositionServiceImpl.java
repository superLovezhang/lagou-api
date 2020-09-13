package com.superflower.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.superflower.admin.mapper.PositionMapper;
import com.superflower.admin.service.IPositionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.superflower.common.entity.Position;
import com.superflower.common.entity.vo.PositionVo;
import com.superflower.common.utils.CodeUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;


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
public class PositionServiceImpl extends ServiceImpl<PositionMapper, Position> implements IPositionService {

    @Override
    public Page<Position> searchList(Long page, Long num, PositionVo positionVo) {
        Page<Position> positionPage = new Page<>(page, num);
        QueryWrapper<Position> wrapper = new QueryWrapper<>();
        String positionName = positionVo.getPositionName();
        positionVo.setPositionName(null);
        if (!StringUtils.isEmpty(positionName)) wrapper.like("position_name", positionName);
        wrapper.allEq(CodeUtils.entityToMap(positionVo));
        return baseMapper.selectPage(positionPage, wrapper);
    }
}
