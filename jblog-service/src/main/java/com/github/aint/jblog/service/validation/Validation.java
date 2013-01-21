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

import java.util.Locale;

import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import com.github.aint.jblog.service.validation.interpolator.LocaleSpecificMessageInterpolator;

/**
 * This class is the entry point for Bean Validation instead of {@link javax.validation.Validation}.
 * 
 * @author Olexandr Tyshkovets
 */
public class Validation {

    /**
     * Returns a {@code Validator} with the specified {@code locale} of validation messages. Uses
     * {@code LocaleSpecificMessageInterpolator} as a {@code MessageInterpolator} implementation.
     * 
     * @param locale
     *            specifies a {@code Locale} of validation messages
     * @return a Java Bean (JSR-303) {@code Validator}
     * @see LocaleSpecificMessageInterpolator
     */
    public static Validator getValidator(Locale locale) {
        ValidatorFactory vf = javax.validation.Validation.buildDefaultValidatorFactory();
        return vf.usingContext()
                .messageInterpolator(new LocaleSpecificMessageInterpolator(vf.getMessageInterpolator(), locale))
                .getValidator();
    }

}
