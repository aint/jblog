package com.github.aint.jblog.service.validation.interpolator;

import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

import java.util.Locale;

import javax.validation.MessageInterpolator;

import org.mockito.Mock;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

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
