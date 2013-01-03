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
package com.github.aint.jblog.service.validation.impl;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.github.aint.jblog.service.exception.validator.ValidationException;
import com.github.aint.jblog.service.validation.Validator;
import com.github.aint.jblog.service.validation.annotation.Length;
import com.github.aint.jblog.service.validation.annotation.ValidField;

/**
 * This class is implementation of the {@link Validator} interface with using the {@code ValidField} annotation.
 * 
 * @author Olexandr Tyshkovets
 * @see ValidField
 * @see Length
 */
public class AnnotationBasedValidator implements Validator {
    /**
     * {@inheritDoc}
     */
    @Override
    public <T> Map<String, String> validate(T object) throws ValidationException {
        Field[] fields = object.getClass().getDeclaredFields();
        Map<String, String> errorMsgMap = new HashMap<String, String>(fields.length);
        for (Field field : fields) {
            ValidField validFieldAnn = field.getAnnotation(ValidField.class);
            if (validFieldAnn == null) {
                continue;
            }
            String fieldValue = null;
            field.setAccessible(true);
            try {
                fieldValue = (String) field.get(object);
            } catch (IllegalAccessException e) {
                throw new ValidationException("The " + field.getName() + " field is inaccessible", e);
            }

            String fieldName = validFieldAnn.fieldName().equals(ValidField.DEFAULT_FIELD_NAME) ? field.getName() :
                    validFieldAnn.fieldName();

            if (fieldValue == null) {
                errorMsgMap.put(field.getName(), fieldName + " can't be empty");
                continue;
            }

            if (!ValidField.DEFAULT_MUST_MATCH_FIELD.equals(validFieldAnn.mustMatch()) &&
                    !isEqualField(object, fields, validFieldAnn.mustMatch(), fieldValue)) {
                errorMsgMap.put(field.getName(), fieldName + " must equals " + validFieldAnn.mustMatch());
                continue;
            }

            Length lengthAnn = validFieldAnn.length();
            int min = lengthAnn.min();
            int max = lengthAnn.max();
            boolean ignoreLength = isIgnoreLength(min, max);
            if (!ignoreLength && fieldValue.length() < min) {
                errorMsgMap.put(field.getName(), fieldName + " can't be less than " + min);
                continue;
            }
            if (!ignoreLength && fieldValue.length() > max) {
                errorMsgMap.put(field.getName(), fieldName + " can't be more than " + max);
                continue;
            }

            Pattern pattern = getPattern(validFieldAnn.regex(), ignoreLength, min, max);
            if (pattern == null) {
                continue;
            }
            Matcher matcher = pattern.matcher(fieldValue);
            if (!matcher.matches()) {
                errorMsgMap.put(field.getName(), fieldName + " is uncorrect");
                continue;
            }
        }

        return Collections.unmodifiableMap(errorMsgMap);
    }

    /**
     * Checks whether length can be ignored.
     * 
     * @param min
     *            the minimal length
     * @param max
     *            the maximal length
     * @return {@code true} if the length is ignored; {@code false} otherwise
     * @throws IllegalArgumentException
     *             if {@code min} < 1 or {@code min} > {@code max}
     */
    private boolean isIgnoreLength(int min, int max) {
        if (min == Length.DEFAULT_MIN_LENGTH && max == Length.DEFAULT_MAX_LENGTH) {
            return true;
        }

        if (min < 1) {
            throw new IllegalArgumentException("min can't be less 1");
        }
        if (min > max) {
            throw new IllegalArgumentException("min can't be more max");
        }

        return false;
    }

    /**
     * Compiles the given regular expression into a {@link Pattern}.
     * 
     * @param regex
     *            the expression to be compiled
     * @param ignoreLength
     *            if {@code true} the length is ignored
     * @param min
     *            the minimal length
     * @param max
     *            the maximal length
     * @return a {@link Pattern}
     */
    private Pattern getPattern(String regex, boolean ignoreLength, int min, int max) {
        if (regex.equals(ValidField.DEFAULT_REGEX)) {
            return null;
        }
        if (ignoreLength) {
            return Pattern.compile(regex);
        } else {
            return Pattern.compile(regex + String.format("{%d,%d}", min, max));
        }
    }

    private boolean isEqualField(Object object, Field[] fields, String fieldName, String currentFieldValue)
            throws ValidationException {
        for (Field field : fields) {
            if (field.getName().equals(fieldName)) {
                String fieldValue = null;
                try {
                    fieldValue = (String) field.get(object);
                } catch (IllegalAccessException e) {
                    throw new ValidationException("The " + field.getName() + " field is inaccessible", e);
                }
                if (currentFieldValue.equals(fieldValue)) {
                    return true;
                } else {
                    return false;
                }
            }
        }

        return false;
    }

}
