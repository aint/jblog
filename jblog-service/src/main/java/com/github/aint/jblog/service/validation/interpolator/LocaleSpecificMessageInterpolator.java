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
package com.github.aint.jblog.service.validation.interpolator;

import java.util.Locale;

import javax.validation.MessageInterpolator;

/**
 * Delegates to a {@code MessageInterpolator} implementation but enforce a given {@code Locale}. Use
 * {@code LocaleSpecificMessageInterpolator} to use a specific {@code Locale} value.
 * 
 * @author Olexandr Tyshkovets
 */
public class LocaleSpecificMessageInterpolator implements MessageInterpolator {
    private final MessageInterpolator interpolator;
    private final Locale locale;

    /**
     * Constructs a {@code LocaleSpecificMessageInterpolator} with the given {@code interpolator} and {@code locale}.
     */
    public LocaleSpecificMessageInterpolator(MessageInterpolator interpolator, Locale locale) {
        this.locale = locale;
        this.interpolator = interpolator;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String interpolate(String message, Context context) {
        return locale == null ? interpolator.interpolate(message, context) :
                interpolator.interpolate(message, context, locale);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String interpolate(String message, Context context, Locale locale) {
        return interpolator.interpolate(message, context, locale);
    }

}
