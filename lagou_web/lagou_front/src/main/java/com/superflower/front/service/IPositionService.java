package com.superflower.front.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.superflower.common.entity.Position;
import com.superflower.common.entity.vo.PositionVo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zz
 * @since 2020-08-08
 */
public interface IPositionService extends IService<Position> {

    Page<Position> findPageByQuery(Long page, Long num, PositionVo positionVo);
}
