package com.superflower.common.anno;

import com.superflower.common.validate.PhoneValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PhoneValidator.class)
public @interface Phone {
    
    /**
     * 校验不通过的message
     */
    String message() default "请输入正确的手机号";

    /**
     * 分组校验
     */
    Class<?>[] groups() default { };


    Class<? extends Payload>[] payload() default { };
}