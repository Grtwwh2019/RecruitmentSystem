package com.zzj.recruitment.customValid;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Date;

/**
 * 校验日期的校验类
 * version 1.0
 * Created by Grtwwh2019
 * since 2020-03-20 14:25:05
 */
public class CheckWorkDateIntervalValidator implements ConstraintValidator<CheckWorkDateInterval, Object> {

    private CheckWorkDateInterval checkWorkDateInterval;

    @Override
    public void initialize(CheckWorkDateInterval constraintAnnotation) {
        this.checkWorkDateInterval = constraintAnnotation;
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext constraintValidatorContext) {
        if (null == value) {
            return true;
        }

        BeanWrapper beanWrapper = new BeanWrapperImpl(value);
        Object startdate = beanWrapper.getPropertyValue(checkWorkDateInterval.startdate());
        Object enddate = beanWrapper.getPropertyValue(checkWorkDateInterval.enddate());

        if (null == startdate || null == enddate) {
            return true;
        }

        int result = ((Date) enddate).compareTo((Date) startdate);

        if (result >= 0) {
            return true;
        }
        return false;
    }
}
