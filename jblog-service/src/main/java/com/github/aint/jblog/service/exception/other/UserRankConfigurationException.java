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
package com.github.aint.jblog.service.exception.other;

import com.github.aint.jblog.service.other.UserRankConfiguration;

/**
 * This exception thrown when some configuration error has occurred.
 * 
 * @author Olexandr Tyshkovets
 * @see Exception
 * @see UserRankNotFoundException
 * @see XmlUserRankConfigurationException
 * @see UserRankConfiguration
 */
public abstract class UserRankConfigurationException extends Exception {
    private static final long serialVersionUID = 6935069038623951308L;

    /**
     * Constructs a new exception with {@code null} as its detail message.
     */
    public UserRankConfigurationException() {
    }

    /**
     * Constructs a new exception with the specified detail message.
     */
    public UserRankConfigurationException(String message) {
        super(message);
    }

    /**
     * Constructs a new exception with the specified detail message and cause.
     * 
     * @param message
     *            the detail message
     * @param cause
     *            the cause (which is saved for later retrieval by the {@link #getCause()} method)
     */
    public UserRankConfigurationException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a new exception with the specified cause.
     * 
     * @param cause
     *            the cause (which is saved for later retrieval by the {@link #getCause()} method)
     */
    public UserRankConfigurationException(Throwable cause) {
        super(cause);
    }

}
