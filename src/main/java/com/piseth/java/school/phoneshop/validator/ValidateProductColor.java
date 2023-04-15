package com.piseth.java.school.phoneshop.validator;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.TYPE;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Target({ TYPE, ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = { ProductColorValidator.class })
@Documented
public @interface ValidateProductColor {
	String message() default "Color not allowed for this brand";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
