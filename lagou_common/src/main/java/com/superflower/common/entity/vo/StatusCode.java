package com.superflower.common.entity.vo;

public enum StatusCode {
    SUCCESS("操作成功", 20000l),
    LOGIN_SUCCESS("登录成功", 20001l),
    VERIFY_CODE_SEND_SUCCESS("验证码发送成功", 20002l),
    REGISTER_SUCCESS("注册成功", 20003l),
    ERROR("操作失败" ,50000l),
    UPDATE_COMPANY_ERROR("不能修改别人的企业信息", 50001l),
    UPDATE_POSITION_ERROR("不能修改别人的职位信息", 50002l),
    POSITION_NOT_EXIST("该职位不存在", 50003l),
    COMPANY_IS_EXIST("不能重复创建公司信息", 50004l),
    NOT_HAVE_COMPANY("请先创建一个公司信息", 50005l),
    UPDATE_PRODUCT_ERROR("不能修改别人的公司产品信息", 50006l),
    LOGIN_PASSWORD_ERROR("账号或密码错误", 50010l),
    LOGIN_NOT_VALID("登录状态异常", 50011l),
    LOGIN_ERROR("登录失败", 50012l),
    LOGIN_NOT("未登录", 50013l),
    ACCOUNT_FREEZE("账号冻结", 50014l),
    VERIFY_CODE_NOT_EMPTY("验证码不能为空", 50020l),
    VERIFY_CODE_ERROR("验证码错误", 50021l),
    VERIFY_CODE_SEND_ERROR("验证码发送失败", 50022l),
    VERIFY_CODE_NOT_SEND("请发送验证码", 50023l),
    PARAMS_NOT_VALID("参数错误", 50030l),
    REGISTER_ERROR_PHONE_REPEAT("该手机号已被注册", 50040l),
    REGISTER_NOT("该手机号未注册", 50041l),
    NOT_HAVE_ACCESS("没有权限，请联系管理员", 50050l);

    private String message;
    private long code;

    public String getMessage() {
        return message;
    }

    public long getCode() {
        return code;
    }

    StatusCode(String message, long code) {
        this.message = message;
        this.code = code;
    }
}
