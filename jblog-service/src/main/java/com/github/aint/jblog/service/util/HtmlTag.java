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
package com.github.aint.jblog.service.util;

import java.util.regex.Pattern;

/**
 * This enum enumerates HTML tags.
 * 
 * @author Olexandr Tyshkovets
 */
public enum HtmlTag {
    /**
     * {@code <a href="" [target=""]>...</a>} or {@code <a name="" [target=""]>...</a>}
     */
    A("<a[^>]*>(.*?)</a>"),
    /**
     * {@code <b>...</b>}
     */
    B("<b>(.*?)</b>"),
    /**
     * {@code <blockquote>...</blockquote>}
     */
    BLOCKQUOTE("<blockquote>(.*?)</blockquote>"),
    /**
     * {@code <del>...</del>}
     */
    DEL("<del>(.*?)</del>"),
    /**
     * {@code <i>...</i>}
     */
    I("<i>(.*?)</i>"),
    /**
     * {@code <img src="" [width="" height="" align="" vspace="" hspace="" border=""]>}
     */
    IMG("<img[^>]*>");

    private String regex;

    /**
     * Constructs a {@code HtmlTag} with the given {@code regex}.
     * 
     * @param regex
     *            the regular expression which answers a necessary tag
     */
    private HtmlTag(String regex) {
        this.regex = regex;
    }

    /**
     * Returns the lookup {@code HtmlTag} by the given {@code regex}. Lookups with ignoring case.
     * 
     * @param regex
     *            the regex of a necessary tag
     * @return a {@code HtmlTag} by the given {@code regex}; otherwise returns {@code null}
     * @throws IllegalArgumentException
     *             if the {@code regex} is {@code null}
     */
    public static HtmlTag lookUp(String regex) {
        if (regex == null) {
            throw new IllegalArgumentException("regex can't be null");
        }

        for (HtmlTag tag : values()) {
            if (tag.getRegex().equalsIgnoreCase(regex)) {
                return tag;
            }
        }

        return null;
    }

    /**
     * @return an HTML tag's {@link Pattern}
     */
    public Pattern getPattern() {
        return Pattern.compile(regex);
    }

    /**
     * @return an HTML tag's regex
     */
    public String getRegex() {
        return regex;
    }

}
