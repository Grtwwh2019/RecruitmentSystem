package com.zzj.recruitment.customValid;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * 校验两个日期之间，开始日期不能大于结束日期
 * version 1.0
 * Created by Grtwwh2019
 * since 2020-03-20 14:21:53
 */
@Documented
@Constraint(
        validatedBy = {CheckWorkDateIntervalValidator.class}
)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(CheckWorkDateInterval.List.class)
public @interface CheckWorkDateInterval {

    String startdate() default "startdate";

    String enddate() default "enddate";

    String message() default "{javax.custom.validation.CheckWorkDateInterval.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    @Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    public @interface List {
        CheckWorkDateInterval[] value();
    }
}
