package com.github.aint.jblog.service.validation.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.github.aint.jblog.service.validation.validator.FieldMatchValidator;

/**
 * This annotation marks the class to check two specified fields for the equality. The two {@code null} fields are
 * equal.
 * 
 * @author Olexandr Tyshkovets
 * @see FieldMatchValidator
 */
@Target(value = { ElementType.TYPE, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = FieldMatchValidator.class)
public @interface FieldMatch {
    /**
     * The resource bundle code for error message.
     */
    String message();

    /**
     * Sets the field name that will be checked for equality with the {@code matchField}.
     */
    String field();

    /**
     * Sets the field name that should match the {@code field}.
     */
    String matchField();

    /**
     * Specifies the processing groups with which the constraint declaration is associated.
     */
    Class<?>[] groups() default {};

    /**
     * Specifies the payload with which the the constraint declaration is associated. No used.
     */
    Class<? extends Payload>[] payload() default {};

}
