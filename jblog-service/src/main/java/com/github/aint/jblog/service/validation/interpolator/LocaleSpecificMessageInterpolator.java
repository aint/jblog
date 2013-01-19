package com.github.aint.jblog.service.validation.interpolator;

import java.util.Locale;

import javax.validation.MessageInterpolator;

public class LocaleSpecificMessageInterpolator implements MessageInterpolator {
    private final MessageInterpolator interpolator;
    private final Locale locale;

    public LocaleSpecificMessageInterpolator(MessageInterpolator interpolator, Locale locale) {
        this.locale = locale;
        this.interpolator = interpolator;
    }

    @Override
    public String interpolate(String message, Context context) {
        return interpolator.interpolate(message, context, locale);
    }

    @Override
    public String interpolate(String message, Context context, Locale locale) {
        return interpolator.interpolate(message, context, locale);
    }

}
