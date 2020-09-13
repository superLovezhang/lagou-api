package com.superflower.common.config;

import com.superflower.common.entity.vo.R;
import com.superflower.common.entity.vo.StatusCode;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Set;

@RestControllerAdvice
public class GlobalExceptionHandle {
    /**
     * 单个参数异常处理
     *
     * @param ex
     * @return
     */
    @ExceptionHandler(value = ConstraintViolationException.class)
    public R constraintViolationException(ConstraintViolationException ex) {

        // 获取具体的错误信息
        Set<ConstraintViolation<?>> violations = ex.getConstraintViolations();
        // 打印数据
        violations.forEach(e -> System.out.println(e.getMessage()));

        return R.error(StatusCode.PARAMS_NOT_VALID);
    }

    /**
     * form表单参数校验绑定异常处理
     *
     * @param ex
     * @return
     */
    @ExceptionHandler(value = BindException.class)
    public R bindException(BindException ex) {

        BindingResult bindingResult = ex.getBindingResult();

        // 获取所有的错误信息
        List<ObjectError> allErrors = bindingResult.getAllErrors();

        // 输出
        allErrors.forEach(e -> System.out.println(e.getDefaultMessage()));

        return R.error(StatusCode.PARAMS_NOT_VALID);
    }

    /**
     * JSON参数校验绑定异常处理
     *
     * @param ex
     * @return
     */
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public R methodArgumentNotValidException(MethodArgumentNotValidException ex) {

        BindingResult bindingResult = ex.getBindingResult();

        // 获取所有的错误信息
        List<ObjectError> allErrors = bindingResult.getAllErrors();

        // 输出
        allErrors.forEach(e -> System.out.println(e.getDefaultMessage()));

        return R.error(StatusCode.PARAMS_NOT_VALID);
    }

    @ExceptionHandler(value = Exception.class)
    public R exception(Exception e) {
        String message = e.getMessage();
        return R.error(StatusCode.ERROR).put("message", message);
    }
}
