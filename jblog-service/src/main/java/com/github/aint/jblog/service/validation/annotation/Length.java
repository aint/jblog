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

/**
 * This annotation used for setting the minimal and maximal length of the marking field.
 * 
 * @author Olexandr Tyshkovets
 * @see ValidField
 */
@Target(value = ElementType.ANNOTATION_TYPE)
@Retention(value = RetentionPolicy.RUNTIME)
@Documented
public @interface Length {
    /**
     * The default value for the {@code min} field.
     */
    public static final int DEFAULT_MIN_LENGTH = Integer.MIN_VALUE;

    /**
     * The default value for the {@code max} field.
     */
    public static final int DEFAULT_MAX_LENGTH = Integer.MAX_VALUE;

    /**
     * Sets the minimal length of the marked field.
     */
    int min();

    /**
     * Sets the maximal length of the marked field.
     */
    int max();
}
