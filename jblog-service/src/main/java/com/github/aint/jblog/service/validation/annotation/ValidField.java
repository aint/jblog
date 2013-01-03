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
 * This annotation used for marking which fields will be validates. Only from {@code String} fields. </p> Example of
 * usage:
 * 
 * <pre>
 * &#064;ValidField(length = @Length(min = 3, max = 30), regex = &quot;[а-яА-Я\\w]&quot;, fieldName = &quot;user name&quot;)
 * private String userName;
 * </pre>
 * 
 * equivalents
 * 
 * <pre>
 * &#064;ValidField(regex = &quot;[а-яА-Я\\w]{3,30}&quot;, fieldName = &quot;user name&quot;)
 * private String userName;
 * </pre>
 * 
 * @author Olexandr Tyshkovets
 * @see Length
 */
@Target(value = ElementType.FIELD)
@Retention(value = RetentionPolicy.RUNTIME)
@Documented
public @interface ValidField {
    /**
     * The default value for the {@code regex} field.
     */
    public static final String DEFAULT_REGEX = "default_regex";

    /**
     * The default value for the {@code fieldName} field.
     */
    public static final String DEFAULT_FIELD_NAME = "default_name";

    /**
     * The default value for the {@code mustMatch} field.
     */
    public static final String DEFAULT_MUST_MATCH_FIELD = "default_field";

    /**
     * This annotation used for the sets length of a marked field. The values of the {@code min} and {@code max} fields
     * append to the end of the {@code regex}, e.g. {@code regex}{{@code min},{@code max} . </p> If this field is missed
     * then the length of the field isn't limited.
     * 
     * @see Length
     */
    Length length() default @Length(min = Length.DEFAULT_MIN_LENGTH, max = Length.DEFAULT_MAX_LENGTH);

    /**
     * Sets a regular expression. If the {@link Length} annotation is not missed then forbidden to set the total length
     * of the regex, like {@code regex}{{@code min},{@code max} . </p> If this field is missed then the field isn't
     * limited.
     * 
     * @see Length
     */
    String regex() default DEFAULT_REGEX;

    /**
     * Sets the name of the field in an error message. </p> If this field is missed then the name of the field set the
     * real name received by reflection.
     */
    String fieldName() default DEFAULT_FIELD_NAME;

    /**
     * This method indicates a field's name which should be equal to the current field.
     */
    String mustMatch() default DEFAULT_MUST_MATCH_FIELD;

}
