package com.superflower.admin.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.superflower.admin.service.IUserService;
import com.superflower.common.entity.User;
import com.superflower.common.entity.vo.R;
import com.superflower.common.entity.vo.StatusCode;
import com.superflower.common.entity.vo.UserSearchVo;
import com.superflower.common.entity.vo.UserVo;
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
 *  前端控制器
 * </p>
 *
 * @author zz
 * @since 2020-08-08
 */
@Api(tags = "用户管理")
@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private IUserService userService;

    @ApiOperation("用户列表")
    @PostMapping("/list/{page}/{num}")
    public R list(@PathVariable Long page, @PathVariable Long num, @RequestBody UserSearchVo userSearchVo) {
        page = page >= 1 ? page : 1;
        num = num >= 1 ? num : 1;
        Page<User> userPage = userService.searchList(page, num, userSearchVo);

        List<User> rows = userPage.getRecords();
        long pages = userPage.getPages();
        long current = userPage.getCurrent();
        HashMap<String, Object> map = new HashMap<>();
        map.put("rows", rows);
        map.put("pages", pages);
        map.put("current", current);

        return R.success(StatusCode.SUCCESS, map);
    }

    @ApiOperation("修改用户信息")
    @PostMapping("/modify/{userId}")
    public R modify(@PathVariable String userId, @Validated @RequestBody UserVo userVo) {
        User user = new User();
        BeanUtils.copyProperties(userVo, user);
        user.setId(userId);
        boolean b = userService.updateById(user);

        return b ? R.success() : R.error();
    }

    @ApiOperation("修改账号状态")
    @PostMapping("/updateStatus/{userId}")
    public R updateStatus(@PathVariable String userId, @RequestParam Integer status) {
        User user = new User();
        user.setId(userId);
        user.setStatus(status);
        boolean b = userService.updateById(user);
        return b ? R.success() : R.error();
    }
}

