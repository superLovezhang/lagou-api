package com.superflower.common.validate;

import javax.validation.groups.Default;

/**
 * 创建分组类的时候 实现了Default
 * 在校验的时候指名分组类型时 不光校验指名分组 还会校验默认分组
 */
public interface UpdateGroup extends Default {
}
