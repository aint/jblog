package com.github.aint.jblog.service.validation.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.github.aint.jblog.model.entity.Entity;
import com.github.aint.jblog.service.validation.validator.UniqueValidator;

/**
 * This annotation marks the field to check it's value for the uniqueness in a data source. Applies only to string
 * variables.
 * 
 * @author Olexandr Tyshkovets
 * @see UniqueValidator
 */
@Target(value = ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = UniqueValidator.class)
public @interface Unique {
    /**
     * The resource bundle code for error message.
     */
    String message();

    /**
     * Sets the field name that will be checked.
     */
    String field();

    /**
     * Specifies the processing groups with which the constraint declaration is associated.
     */
    Class<?>[] groups() default {};

    /**
     * Specifies the payload with which the the constraint declaration is associated. No used.
     */
    Class<? extends Payload>[] payload() default {};

    /**
     * The entity which field will be checked.
     * 
     * @see Entity
     */
    Class<? extends Entity> entity();

}
