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

import com.github.aint.jblog.model.entity.Entity;
import com.github.aint.jblog.service.validation.validator.ExistsValidator;

/**
 * This annotation marks the field to check the existence of its value in a data source. Applies only to string
 * variables.
 * 
 * @author Olexandr Tyshkovets
 * @see ExistsValidator
 */
@Target(value = ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = ExistsValidator.class)
public @interface Exists {
    /**
     * The resource bundle code for error message.
     */
    String message();

    /**
     * Sets the field name that will be checked.
     */
    String field();

    /**
     * The entity which field will be checked.
     * 
     * @see Entity
     */
    Class<? extends Entity> entity();

    /**
     * Specifies the processing groups with which the constraint declaration is associated.
     */
    Class<?>[] groups() default {};

    /**
     * Specifies the payload with which the the constraint declaration is associated. No used.
     */
    Class<? extends Payload>[] payload() default {};

}
