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
 * This interface represents methods for defines a web browser's name and its version.
 * 
 * @author Olexandr Tyshkovets
 * @see BrowserServiceImpl
 */
public interface BrowserService {
    /**
     * Returns the browser's name or an empty {@code String} if can't defines the browser's name.
     * 
     * @return the browser's name
     */
    String getBrowserName();

    /**
     * Returns the browser's full version as a {@code String} or an empty {@code String} if can't defines the browser's
     * full version.
     * 
     * @return the browser's full version
     */
    public String getBrowserVersion();

    /**
     * Returns the browser's major version or {@code -1} if can't defines the browser's major version.
     * 
     * @return the browser's major version
     */
    public int getBrowserMajorVersion();

}
