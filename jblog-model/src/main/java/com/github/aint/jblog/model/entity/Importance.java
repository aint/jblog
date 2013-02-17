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

/**
 * This enum lists a {@link News}' importance.
 * 
 * @author Olexandr Tyshkovets
 * @see News#getImportance()
 */
public enum Importance {
    /**
     * Lesser importance of a news.
     */
    LOW("low"),
    /**
     * Middle importance of a news.
     */
    MIDDLE("middle"),
    /**
     * Higher importance of a news.
     */
    HIGH("high");

    private String importance;

    /**
     * Constructs a {@code Importance} with the given {@code importance}.
     * 
     * @param roleName
     *            the role's name
     */
    private Importance(String importance) {
        this.importance = importance;
    }

    /**
     * Returns the lookup {@code Importance} by the given {@code importance}. Lookups with ignoring case.
     * 
     * @param importance
     *            the news's importance
     * @return a {@code Importance} by the given {@code importance}; {@code null} otherwise
     */
    public static Importance lookUp(String importance) {
        for (Importance im : values()) {
            if (im.getImportance().equalsIgnoreCase(importance)) {
                return im;
            }
        }
        return null;
    }

    /**
     * @return the importance
     */
    public String getImportance() {
        return importance;
    }

}
