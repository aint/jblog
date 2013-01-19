package com.github.aint.jblog.service.validation;

import java.util.Locale;

import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import com.github.aint.jblog.service.validation.interpolator.LocaleSpecificMessageInterpolator;

public class Validation {

    public static Validator getValidator(Locale locale) {
        ValidatorFactory vf = javax.validation.Validation.buildDefaultValidatorFactory();
        return vf.usingContext()
                .messageInterpolator(new LocaleSpecificMessageInterpolator(vf.getMessageInterpolator(), locale))
                .getValidator();
    }
}
