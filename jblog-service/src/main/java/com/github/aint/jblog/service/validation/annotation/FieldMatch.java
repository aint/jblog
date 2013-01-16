/**
 * Copyright (C) 2012-2013  Olexandr Tyshkovets
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 */
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
