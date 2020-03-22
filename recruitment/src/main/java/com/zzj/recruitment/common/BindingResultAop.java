package com.zzj.recruitment.common;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import java.util.List;

/**
 * version 1.0
 * Created by Grtwwh2019
 * since 2020-03-19 12:16:33
 */
//@Component
//@Aspect
@Slf4j
public class BindingResultAop {

    /**
     * 切入点
     * 设置切入点为Controller层
     * AspectJ支持命名切入点，方法必须是返回void类型
     */
    @Pointcut("execution(* com.zzj.recruitment.controller..*.*(..))")
    public void aopMethod(){}

    /**
     * 检查 Controller 方法的参数是否有效
     * 环绕通知：目标方法执行前后分别执行一些代码，发生异常的时候执行其相应逻辑
     * @param joinPoint
     * @return
     * @throws Throwable
     */
    @Around("aopMethod()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        BindingResult bindingResult = null;
        for (Object arg : joinPoint.getArgs()) {
            if (arg instanceof BindingResult) {
                bindingResult = (BindingResult) arg;
            }
        }
        if (bindingResult != null) {
            if (bindingResult.hasErrors()) {
                String errorInfo = "";
                String logInfo = "";
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
                log.info("校验失败：" + logInfo);
                return ServerResponse.createResponseByErrorMsg(errorInfo);
            }
        }
        return joinPoint.proceed();
    }

}
