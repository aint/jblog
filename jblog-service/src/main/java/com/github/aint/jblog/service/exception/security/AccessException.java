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
package com.github.aint.jblog.service.exception.security;

import com.github.aint.jblog.model.entity.User;
import com.github.aint.jblog.service.data.UserService;

/**
 * This exception thrown when a caller doesn't have permission to perform some action.
 * 
 * @author Olexandr Tyshkovets
 * @see Exception
 * @see UserService#banUser(User, User, int, String)
 * @see UserService#unbanUser(User, User)
 */
public class AccessException extends Exception {
    private static final long serialVersionUID = -1177795191681296013L;

    /**
     * Constructs a new exception with {@code null} as its detail message.
     */
    public AccessException() {
    }

    /**
     * Constructs a new exception with the specified detail message.
     * 
     * @param message
     *            the detail message
     */
    public AccessException(String message) {
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
    public AccessException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a new exception with the specified cause.
     * 
     * @param cause
     *            the cause (which is saved for later retrieval by the {@link #getCause()} method)
     */
    public AccessException(Throwable cause) {
        super(cause);
    }

}
