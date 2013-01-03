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

import com.github.aint.jblog.service.other.impl.XmlUserRankConfiguration;

/**
 * This exception thrown when an error has occurred while performing a parse XML operation.
 * 
 * @author Olexandr Tyshkovets
 * @see UserRankConfigurationException
 * @see XmlUserRankConfiguration
 */
public class XmlUserRankConfigurationException extends UserRankConfigurationException {
    private static final long serialVersionUID = 465439210214201730L;

    /**
     * Constructs a new exception with {@code null} as its detail message.
     */
    public XmlUserRankConfigurationException() {
    }

    /**
     * Constructs a new exception with the specified detail message.
     * 
     * @see UserRankConfigurationException
     */
    public XmlUserRankConfigurationException(String message) {
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
    public XmlUserRankConfigurationException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a new exception with the specified cause.
     * 
     * @param cause
     *            the cause (which is saved for later retrieval by the {@link #getCause()} method)
     */
    public XmlUserRankConfigurationException(Throwable cause) {
        super(cause);
    }

}
