package com.superflower.front.controller;


import com.superflower.common.anno.Phone;
import com.superflower.common.entity.User;
import com.superflower.common.entity.vo.R;
import com.superflower.common.entity.vo.RegisterVo;
import com.superflower.common.entity.vo.StatusCode;
import com.superflower.common.entity.vo.UserVo;
import com.superflower.common.utils.CodeUtils;
import com.superflower.common.utils.JwtUtils;
import com.superflower.common.validate.CreateGroup;
import com.superflower.front.service.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author zz
 * @since 2020-08-08
 */
@Api(tags = "用户数据操作")
@RestController
@RequestMapping("/user")
@Validated
public class UserController {
    @Autowired
    private IUserService userService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @ApiOperation("登录")
    @PostMapping("/login")
    public R login(@RequestBody RegisterVo user) {
        String verifyCode = user.getVerifyCode();
        String password = user.getPassword();
        String phone = user.getPhone();

        User userFromDb = userService.findByPhone(phone);
        // 查询不到用户数据 表示未注册
        if (userFromDb == null) return R.error(StatusCode.REGISTER_NOT);
        // 账号为封禁状态 也不能登录
        if (userFromDb.getStatus().intValue() == 1) return R.error(StatusCode.ACCOUNT_FREEZE);
        // 如果验证码不为空 则为验证码登录
        if (!StringUtils.isEmpty(verifyCode)) {
            String verifyCodeDb = stringRedisTemplate.opsForValue().get(phone + "::verifyCode");
            if (StringUtils.isEmpty(verifyCodeDb)) return R.error(StatusCode.VERIFY_CODE_NOT_SEND);
            if (!verifyCode.equals(verifyCodeDb)) return R.error(StatusCode.VERIFY_CODE_ERROR);
        } else {
            // 密码登录
            boolean checkpw = BCrypt.checkpw(password, userFromDb.getPassword());
            if (!checkpw) return R.error(StatusCode.LOGIN_PASSWORD_ERROR);
        }
        String token = JwtUtils.encode(userFromDb);
        return R.success(StatusCode.LOGIN_SUCCESS, token);
    }

    @ApiOperation("给手机发送验证码")
    @GetMapping("/sendCode")
    public R sendCode(@Phone @RequestParam String phone) {
        String verifyCode = CodeUtils.generateRandom();
        Boolean isSend = CodeUtils.sendCode(phone, verifyCode);
        // 验证码发送失败就返回失败结果
        if (!isSend) return R.error(StatusCode.VERIFY_CODE_SEND_ERROR);
        // 发送成功就把验证码存入redis缓存中 然后返回成功结果
        stringRedisTemplate.opsForValue().set(phone + "::verifyCode", verifyCode, 5, TimeUnit.MINUTES);
        return R.success(StatusCode.VERIFY_CODE_SEND_SUCCESS, null);
    }

    @ApiOperation("注册")
    @PutMapping("/register")
    public R register(@Validated(CreateGroup.class) @RequestBody RegisterVo registerVo) {
        String phone = registerVo.getPhone();
        String verifyCode = registerVo.getVerifyCode();
        String verifyCodeDb = stringRedisTemplate.opsForValue().get(registerVo.getPhone() + "::verifyCode");
        // 验证码为空或者不匹配 返回错误结果
        if (StringUtils.isEmpty(verifyCodeDb)) return R.error(StatusCode.VERIFY_CODE_NOT_SEND);
        if (StringUtils.isEmpty(verifyCode)) return R.error(StatusCode.VERIFY_CODE_NOT_EMPTY);
        if (!verifyCode.equals(verifyCodeDb)) return R.error(StatusCode.VERIFY_CODE_ERROR);
        // 验证码正确 对手机号查询 是否已注册
        User byPhone = userService.findByPhone(phone);
        if (byPhone != null) return R.error(StatusCode.REGISTER_ERROR_PHONE_REPEAT);
        // 对密码进行加密 然后保存数据库 返回正确结果
        String password = BCrypt.hashpw(registerVo.getPassword(), BCrypt.gensalt());
        registerVo.setPassword(password);
        userService.saveRegister(registerVo);
        return R.success(StatusCode.REGISTER_SUCCESS, null);
    }

    @ApiOperation("查询用户信息")
    @Cacheable(value = "user", key = "#id")
    @GetMapping("/info/{id}")
    public R showInfo(@PathVariable String id) {
        User user = userService.getById(id);
        return R.success(StatusCode.SUCCESS, user);
    }

    @ApiOperation("修改个人信息")
    @PostMapping("/updateInfo")
    public R updateInfo(HttpServletRequest request, @Validated @RequestBody UserVo userVo, String userId) {
        redisTemplate.delete("user::" + userId);
        User user = new User();
        BeanUtils.copyProperties(userVo, user);
        user.setId(userId);
        boolean b = userService.updateById(user);
        return b ? R.success() : R.error();
    }
}

