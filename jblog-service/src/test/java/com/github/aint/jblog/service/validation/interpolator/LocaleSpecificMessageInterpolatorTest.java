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

import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

import java.util.Locale;

import javax.validation.MessageInterpolator;

import org.mockito.Mock;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * @author Olexandr Tyshkovets
 */
public class LocaleSpecificMessageInterpolatorTest {
    @Mock
    private MessageInterpolator interpolator;

    @BeforeMethod
    public void beforeMethod() {
        initMocks(this);
    }

    @Test
    public void interpolate() {
        LocaleSpecificMessageInterpolator inter = new LocaleSpecificMessageInterpolator(interpolator, null);

        final String message = "some message";
        inter.interpolate(message, null);

        verify(interpolator).interpolate(message, null);
    }

    @Test
    public void interpolateWithNotNullLocale() {
        final Locale locale = new Locale("uk");
        LocaleSpecificMessageInterpolator inter = new LocaleSpecificMessageInterpolator(interpolator, locale);

        final String message = "some message";
        inter.interpolate(message, null);

        verify(interpolator).interpolate(message, null, locale);
    }

    @Test
    public void interpolateLocale() {
        final Locale locale = new Locale("ru");
        LocaleSpecificMessageInterpolator inter = new LocaleSpecificMessageInterpolator(interpolator, locale);

        final String message = "some message";
        inter.interpolate(message, null, locale);

        verify(interpolator).interpolate(message, null, locale);
    }
}
