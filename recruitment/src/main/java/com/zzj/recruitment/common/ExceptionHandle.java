package com.zzj.recruitment.common;

import com.zzj.recruitment.common.constant.ResponseCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Set;

/**
 * version 1.0
 * Created by Grtwwh2019
 * since 2020-03-19 14:16:03
 */
// 该注解表示开启了全局异常的捕获
@ControllerAdvice
@Slf4j
public class ExceptionHandle {

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ServerResponse validatorHandle(Exception e) {
        String errorInfo = "";
        String logInfo = "";
        if (e instanceof MethodArgumentNotValidException) {
            MethodArgumentNotValidException methodE = (MethodArgumentNotValidException) e;
            BindingResult bindingResult = methodE.getBindingResult();
            if (bindingResult != null) {
                if (bindingResult.hasErrors()) {
                    List<ObjectError> allErrors = bindingResult.getAllErrors();
                    for (ObjectError allError : allErrors) {
                        logInfo = allError + "[ " + allError.getDefaultMessage() + "]";
                        errorInfo = allError.getDefaultMessage();
                    }
                    List<FieldError> fieldErrors = bindingResult.getFieldErrors();
                    for (FieldError fieldError : fieldErrors) {
                        logInfo = fieldError + "[" + fieldError.getField() + " " + fieldError.getDefaultMessage()
                                + "]";
                        errorInfo = "[" + fieldError.getField() + " " + fieldError.getDefaultMessage()
                                + "]";
                    }
                    log.info("参数校验失败：" + logInfo);
                    return ServerResponse.createResponseByErrorCustom(ResponseCode.ILLEGAL_ARGUMENTS.getCode(), errorInfo);
                }
            }
        }
        if(e instanceof ConstraintViolationException){
            ConstraintViolationException exs = (ConstraintViolationException) e;

            Set<ConstraintViolation<?>> violations = exs.getConstraintViolations();

            for (ConstraintViolation<?> item : violations) {
                errorInfo = errorInfo + "[" + item.getMessage() + "]";
            }
            return ServerResponse.createResponseByErrorCustom(ResponseCode.ILLEGAL_ARGUMENTS.getCode(), errorInfo);
        }
        log.error("未知错误：", e);
        return ServerResponse.createResponseByErrorMsg("未知错误");
    }

}
