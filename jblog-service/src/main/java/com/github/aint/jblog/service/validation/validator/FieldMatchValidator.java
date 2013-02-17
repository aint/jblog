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
package com.github.aint.jblog.service.validation.validator;

import java.lang.reflect.Field;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.aint.jblog.service.validation.annotation.FieldMatch;

/**
 * Checks equality of the specified fields.
 * 
 * @author Olexandr Tyshkovets
 * @see MatchFields
 */
public class FieldMatchValidator implements ConstraintValidator<FieldMatch, Object> {
    private static final Logger logger = LoggerFactory.getLogger(FieldMatchValidator.class);
    private String fieldName;
    private String matchFieldName;
    private Object fieldValue;
    private Object matchFieldValue;
    private String message;

    /**
     * {@inheritDoc}
     */
    @Override
    public void initialize(FieldMatch annotation) {
        this.fieldName = annotation.field();
        this.matchFieldName = annotation.matchField();
        this.message = annotation.message();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        try {
            getFieldsValues(value);
        } catch (IllegalAccessException e) {
            logger.error("The field is inaccessible.", e);
        }

        if (fieldValue == null && matchFieldValue == null) {
            return true;
        } else if (fieldValue == null || !fieldValue.equals(matchFieldValue)) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(message)
                    .addNode(matchFieldName)
                    .addConstraintViolation();
            return false;
        }

        return true;
    }

    /**
     * Gets the {@code fieldName} or {@code matchFieldName} fields' values.
     * 
     * @param obj
     *            the object from which the {@code fieldName} or {@code matchFieldName} fields' values are to be
     *            extracted
     * @throws IllegalArgumentException
     *             if the {@code fieldName} or {@code matchFieldName} fields aren't found
     * @throws IllegalAccessException
     *             if the {@code fieldName} or {@code matchFieldName} fields aren't inaccessible
     */
    private void getFieldsValues(Object obj) throws IllegalArgumentException, IllegalAccessException {
        Field[] fields = obj.getClass().getDeclaredFields();
        for (Field field : fields) {
            if (field.getName().equals(fieldName)) {
                field.setAccessible(true);
                fieldValue = field.get(obj);
            } else if (field.getName().equals(matchFieldName)) {
                field.setAccessible(true);
                matchFieldValue = field.get(obj);
            }
        }
    }

}
