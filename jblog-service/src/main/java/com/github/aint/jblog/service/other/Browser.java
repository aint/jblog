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
package com.github.aint.jblog.service.other;

import com.github.aint.jblog.service.other.impl.BrowserServiceImpl;

/**
 * This enum lists the five top web browsers and their minimal supported versions.
 * 
 * @author Olexandr Tyshkovets
 * @see BrowserServiceImpl
 */
public enum Browser {
    /**
     * Apple Safari.
     */
    SAFARI("Safari", 5),
    /**
     * Google Chrome.
     */
    CHROME("Chrome", 15),
    /**
     * Mozilla Firefox.
     */
    FIREFOX("Firefox", 10),
    /**
     * Microsoft Internet Explorer. {@code -1} means that this browser is not supported.
     */
    MSIE("MSIE", -1),
    /**
     * Opera.
     */
    OPERA("Opera", 11);

    private String browserName;
    private int browserMinVersion;

    /**
     * Constructs a {@code Browser} with the given parameters.
     * 
     * @param browserName
     *            a browser's name
     * @param browserMinVersion
     *            a browser's minimal supported version
     */
    private Browser(String browserName, int browserMinVersion) {
        this.browserName = browserName;
        this.browserMinVersion = browserMinVersion;
    }

    /**
     * Returns the lookup {@code Browser} by the given {@code browserName}. Lookups with ignoring case.
     * 
     * @param browserName
     *            the browser's name
     * @return a {@code Browser} by the given {@code browserName}; {@code null} otherwise
     * @throws IllegalArgumentException
     *             if {@code browserName} is {@code null}
     */
    public static Browser lookUp(String browserName) {
        if (browserName == null) {
            throw new IllegalArgumentException("browserName can't be null");
        }

        for (Browser browser : values()) {
            if (browser.getBrowserName().equalsIgnoreCase(browserName)) {
                return browser;
            }
        }
        return null;
    }

    /**
     * @return the browser's name
     */
    public String getBrowserName() {
        return browserName;
    }

    /**
     * @return the browser's minimal supported version
     */
    public int getBrowserMinVersion() {
        return browserMinVersion;
    }

}
