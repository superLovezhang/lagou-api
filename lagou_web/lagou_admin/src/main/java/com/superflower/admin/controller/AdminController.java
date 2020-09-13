package com.superflower.admin.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.superflower.admin.entity.Admin;
import com.superflower.admin.entity.AdminRole;
import com.superflower.admin.entity.Permission;
import com.superflower.admin.entity.vo.AdminVo;
import com.superflower.admin.service.IAdminRoleService;
import com.superflower.admin.service.IAdminService;
import com.superflower.admin.service.IRolePermissionService;
import com.superflower.common.entity.vo.R;
import com.superflower.common.entity.vo.StatusCode;
import com.superflower.common.utils.JwtUtils;
import com.superflower.common.validate.CreateGroup;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author zz
 * @since 2020-08-14
 */
@Api(tags = "后台用户管理")
@RestController
@RequestMapping("/api/admin")
public class AdminController {
    @Autowired
    private IAdminService adminService;

    @Autowired
    private IAdminRoleService adminRoleService;

    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    @Autowired
    private IRolePermissionService rolePermissionService;

    @Autowired
    @SuppressWarnings(value = "all")
    private DefaultKaptcha defaultKaptcha;

    @ApiOperation("删除后台用户")
    @DeleteMapping("/remove")
    public R remove(@RequestBody List<String> adminIds) {
        // 先删除该用户所有的角色信息
        for (String adminId : adminIds) {
            adminRoleService.deleteByAdminId(adminId);
        }
        // 再删除本用户
        boolean b = adminService.removeByIds(adminIds);
        return b ? R.success() : R.error();
    }

    @ApiOperation("修改用户信息")
    @PostMapping("/modify/{adminId}")
    public R modify(@PathVariable String adminId, @Validated @RequestBody AdminVo adminVo) {
        // 获取所有角色id 删除该角色拥有的角色 再保存
        ArrayList<String> roleIds = adminVo.getRoleIds();
        adminRoleService.deleteByAdminId(adminId);
        if (roleIds != null && roleIds.size() != 0) {
            ArrayList<AdminRole> adminRoles = new ArrayList<>();
            for (String roleId : roleIds) {
                AdminRole adminRole = new AdminRole();
                adminRole.setAdminId(adminId);
                adminRole.setRoleId(roleId);
                adminRoles.add(adminRole);
            }
            adminRoleService.saveBatch(adminRoles);
        }
        // 将密码进行加密处理
        if (!StringUtils.isEmpty(adminVo.getPassword()))
            adminVo.setPassword(bCryptPasswordEncoder.encode(adminVo.getPassword()));
        Admin admin = new Admin();
        BeanUtils.copyProperties(adminVo, admin);
        admin.setAdminId(adminId);
        boolean b = adminService.updateById(admin);
        return b ? R.success() : R.error();
    }

    @ApiOperation("增加后台用户")
    @PutMapping("/add")
    public R add(@Validated(CreateGroup.class) @RequestBody AdminVo adminVo) {
        // 将明文密码转成密文存储
        String password = adminVo.getPassword();
        adminVo.setPassword(bCryptPasswordEncoder.encode(password));
        Admin admin = new Admin();
        BeanUtils.copyProperties(adminVo, admin);
        boolean save = adminService.save(admin);
        if (!save) return R.error();
        // 将前台传进来的角色id数组与用户 一一对应保存
        ArrayList<String> roleIds = adminVo.getRoleIds();
        ArrayList<AdminRole> list = new ArrayList<>();
        for (String roleId : roleIds) {
            AdminRole adminRole = new AdminRole();
            adminRole.setRoleId(roleId);
            adminRole.setAdminId(admin.getAdminId());
            list.add(adminRole);
        }
        boolean b = adminRoleService.saveBatch(list);
        return b ? R.success() : R.error();
    }

    @ApiOperation("为用户修改角色")
    @PostMapping("/warrant/{adminId}")
    public R warrant(@PathVariable String adminId, @RequestBody List<String> roleIds) {
        // 每次修改角色 先删除所有拥有角色
        adminRoleService.removeById(adminId);
        ArrayList<AdminRole> adminRoles = new ArrayList<>();
        for (String roleId : roleIds) {
            AdminRole adminRole = new AdminRole();
            adminRole.setAdminId(adminId);
            adminRole.setRoleId(roleId);
        }
        if (adminRoles.size() == 0) return R.success();
        boolean b = adminRoleService.saveBatch(adminRoles);
        return b ? R.success() : R.error();
    }

    @ApiOperation("查询所有后台用户")
    @GetMapping("/list/{page}/{num}")
    public R list(@PathVariable Long page, @PathVariable Long num, @RequestParam(required = false) String adminName) {
        page = page >= 1 ? page : 1;
        num = num >= 1 ? num : 1;
        Page<Admin> adminPage = adminService.listPage(page, num, adminName);

        List<Admin> rows = adminPage.getRecords();
        for (Admin row : rows) {
            String adminId = row.getAdminId();
            ArrayList<String> ids = adminRoleService.roleIdsByAdminId(adminId);
            row.setRoleIds(ids);
        }
        long pages = adminPage.getPages();
        long current = adminPage.getCurrent();
        HashMap<String, Object> map = new HashMap<>();
        map.put("rows", rows);
        map.put("pages", pages);
        map.put("current", current);

        return R.success(StatusCode.SUCCESS, map);
    }

    @ApiOperation("根据token查询用户信息")
    @GetMapping("/info")
    public R info(@RequestParam String token) throws IOException {
        if (!JwtUtils.checkToken(token)) return R.error(StatusCode.LOGIN_NOT_VALID);
        String account = (new ObjectMapper().readValue((String) JwtUtils.decode(token).get("admin"), Admin.class)).getAccount();
        Admin admin = adminService.selectByAccount(account);
        ArrayList<Permission> permissions = rolePermissionService.treeByAdminId(admin.getAdminId());

        HashMap<String, Object> map = new HashMap<>();
        map.put("userInfo", admin);
        map.put("permissionList", permissions);
        return R.success(StatusCode.SUCCESS, map);
    }

    @ApiOperation("登录图形验证码")
    @GetMapping(value = "/verifyCode")
    public void verifyCode(HttpServletResponse response) throws IOException {
        //禁止缓存
        response.setDateHeader("Expires", 0);
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
        response.addHeader("Cache-Control", "post-check=0, pre-check=0");
        response.setHeader("Pragma", "no-cache");
        //设置响应格式为png图片
        response.setContentType("image/png");
        String code = defaultKaptcha.createText();
        // 发送验证码图形的同时 向前端发送带有图形验证码的cookie
        Cookie cookie = new Cookie("verifyCode", code);
        response.addCookie(cookie);
        BufferedImage image = defaultKaptcha.createImage(code);
        ServletOutputStream outputStream = response.getOutputStream();
        ImageIO.write(image, "png", outputStream);
        outputStream.flush();
        outputStream.close();
    }
}

