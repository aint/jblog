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
package com.github.aint.jblog.service.validation;

import java.util.Map;

import com.github.aint.jblog.service.exception.validator.ValidationException;
import com.github.aint.jblog.service.validation.impl.AnnotationBasedValidator;

/**
 * This interface represents a factory method to validate an object.
 * 
 * @author Olexandr Tyshkovets
 * @see AnnotationBasedValidator
 */
public interface Validator {
    /**
     * This method validates the given {@code object}.
     * 
     * @param object
     *            an object for validation
     * @return an unmodifiable view of a {@link Map} of error messages or an empty {@code Map} if there's none. The
     *         {@code Map}'s keys - names of the {@code object}'s fields
     * @throws ValidationException
     *             if an error has occurred while performing a validate operation
     */
    <T> Map<String, String> validate(T object) throws ValidationException;

}
