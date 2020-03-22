package com.zzj.recruitment.customValid;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * 薪资自定义校验，
 * version 1.0
 * Created by Grtwwh2019
 * since 2020-03-18 21:27:28
 */
@Documented
@Constraint(
        validatedBy = {CheckSalaryValidator.class}
)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(CheckSalary.List.class)
public @interface CheckSalary {

    String lowsalary() default "low";

    String highsalary() default "high";

    String message() default "{com.zzj.recruitment.customValid.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    @Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    public @interface List {
        CheckSalary[] value();
    }
}
