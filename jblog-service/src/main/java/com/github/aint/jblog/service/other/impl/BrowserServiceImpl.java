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
package com.github.aint.jblog.service.other.impl;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.github.aint.jblog.service.other.Browser;
import com.github.aint.jblog.service.other.BrowserService;

/**
 * This class implementing the {@code BrowserService} interface.
 * 
 * @author Olexandr Tyshkovets
 * @see BrowserService
 * @see Browser
 */
public class BrowserServiceImpl implements BrowserService {
    private static final Pattern browserPattern = Pattern
            .compile("(.)*(Chrome|Firefox|MSIE|Opera|Safari)(/| )([\\d|.]+)(.)*");
    private final String userAgent;
    private String browserName;
    private String browserVersion;
    private int[] browserFullVersion;
    private int browserMajorVersion;

    /**
     * Constructs a {@code BrowserDefinitionImpl} and parse the browser's {@code user-agent}.
     * 
     * @param userAgent
     *            the browser's {@code user-agent}
     * @throws IllegalArgumentException
     *             if {@code userAgent} is {@code null}
     */
    public BrowserServiceImpl(String userAgent) {
        this.userAgent = userAgent;
        this.browserName = "";
        this.browserVersion = "";
        this.browserFullVersion = new int[] {};
        this.browserMajorVersion = -1;
        parseUserAgent();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getBrowserName() {
        return browserName;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getBrowserVersion() {
        return browserVersion;
    }

    /**
     * Returns the browser's full version as {@code int} array or an empty {@code int} array if can't defines the
     * browser's full version.
     * 
     * @return the browser's full version
     */
    public int[] getBrowserFullVersion() {
        return browserFullVersion;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getBrowserMajorVersion() {
        return browserMajorVersion;
    }

    /**
     * Parse the browser's {@code user-agent} and defines the browser's name and its version.
     * 
     * @throws NumberFormatException
     *             it never happens
     * @throws IllegalArgumentException
     *             if {@code userAgent} is {@code null}
     */
    private void parseUserAgent() {
        if (userAgent == null) {
            throw new IllegalArgumentException("userAgent can't be null");
        }

        Matcher matcher = null;
        if (userAgent.contains("Presto")) {
            matcher = browserPattern.matcher(userAgent.replaceAll("Version", "Opera"));
        } else if (userAgent.contains("AppleWebKit")) {
            // why fucking Chrome's user-name contains the "Safari" word?
            if (userAgent.contains("Chrome")) {
                matcher = browserPattern.matcher(userAgent.replaceAll("Safari", " "));
            } else {
                matcher = browserPattern.matcher(userAgent.replaceAll("Safari", " ").replaceAll("Version", "Safari"));
            }
        } else {
            matcher = browserPattern.matcher(userAgent);
        }

        if (matcher.matches()) {
            browserName = matcher.group(2).trim();
            browserVersion = matcher.group(4);
            String[] version = browserVersion.split("\\.");
            browserFullVersion = new int[version.length];
            for (int i = 0; i < version.length; i++) {
                browserFullVersion[i] = Integer.parseInt(version[i]);
            }
            browserMajorVersion = browserFullVersion.length == 0 ? -1 : browserFullVersion[0];
        }
    }

}
