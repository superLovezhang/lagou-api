package com.superflower.common.validate;

import com.superflower.common.anno.Phone;
import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PhoneValidator implements ConstraintValidator<Phone, String> {
    @Override
    public void initialize(Phone constraintAnnotation) {

    }

    @Override
    public boolean isValid(String phone, ConstraintValidatorContext constraintValidatorContext) {
        //获取默认提示信息
//        String defaultConstraintMessageTemplate = constraintValidatorContext.getDefaultConstraintMessageTemplate();
//        System.out.println("default message :" + defaultConstraintMessageTemplate);
//        //禁用默认提示信息
//        constraintValidatorContext.disableDefaultConstraintViolation();
        //设置提示语
        constraintValidatorContext.buildConstraintViolationWithTemplate("手机号格式错误").addConstraintViolation();
        String regex = "^(0|86|17951)?(13[0-9]|15[012356789]|166|17[3678]|18[0-9]|14[57])[0-9]{8}$";
        if(!StringUtils.isEmpty(phone)){
            return phone.matches(regex);
        }
        // 如果为空就不校验了
        return true;
    }
}
