package com.magic.system.validator;

import javax.validation.Constraint;
import java.lang.annotation.*;

/**
 * @author magic_lz
 * @version 1.0
 * @classname FullName
 * @date 2020/9/3 : 15:23
 */

@Documented
@Constraint(validatedBy = FullNameValidator.class)
@Target({ElementType.PARAMETER, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface FullName {

    String message() default "姓名格式错误！";

    Class[] groups() default {};

    Class[] payload() default {};
}
