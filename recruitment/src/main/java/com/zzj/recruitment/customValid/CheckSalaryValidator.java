package com.zzj.recruitment.customValid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.math.BigDecimal;

/**
 * 薪资校验的Validator
 * version 1.0
 * Created by Grtwwh2019
 * since 2020-03-18 21:32:30
 */
public class CheckSalaryValidator implements ConstraintValidator<CheckSalary, Object> {

    private String lowsalary;

    private String highsalary;

    @Override
    public void initialize(CheckSalary constraintAnnotation) {
        this.lowsalary = constraintAnnotation.lowsalary();
        this.highsalary = constraintAnnotation.highsalary();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext constraintValidatorContext) {
        if (null == value) {
            return true;
        }

        BeanWrapper beanWrapper = new BeanWrapperImpl(value);
        Object low = beanWrapper.getPropertyValue(lowsalary);
        Object high = beanWrapper.getPropertyValue(highsalary);

        if (null == low || null == high) {
            return true;
        }

        int result = ((BigDecimal) high).compareTo((BigDecimal) low);

        if (result >= 0) {
            return true;
        }
        return false;
    }
}
