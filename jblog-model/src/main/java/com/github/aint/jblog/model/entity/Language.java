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
package com.github.aint.jblog.model.entity;

import java.util.Locale;

/**
 * This enum lists available languages.
 * 
 * @author Olexandr Tyshkovets
 * @see User#getLanguage()
 */
public enum Language {
    /**
     * English.
     */
    ENGLISH("en"),
    /**
     * Russian.
     */
    RUSSIAN("ru"),
    /**
     * Ukrainian.
     */
    UKRAINIAN("uk");

    private String languageCode;

    /**
     * Constructs a {@code Language} with the given {@code languageCode}.
     * 
     * @param languageCode
     *            a language code, like "en" or "uk"
     */
    private Language(String languageCode) {
        this.languageCode = languageCode;
    }

    /**
     * Returns the lookup {@code Language} by the given {@code languageCode}. Lookups with ignoring case.
     * 
     * @param languageCode
     *            a language code, like "en" or "uk"
     * @return a {@code Language} by the given {@code languageCode}; {@code null} otherwise
     */
    public static Language lookUp(String languageCode) {
        for (Language language : values()) {
            if (language.getLanguageCode().equalsIgnoreCase(languageCode)) {
                return language;
            }
        }
        return null;
    }

    /**
     * @return a locale from language
     */
    public Locale getLocale() {
        return new Locale(languageCode);
    }

    /**
     * @return a language code, like "en" or "uk"
     */
    public String getLanguageCode() {
        return languageCode;
    }

}
