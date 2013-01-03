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
package com.github.aint.jblog.service.exception.data;

import com.github.aint.jblog.model.entity.User;
import com.github.aint.jblog.service.data.impl.UserServiceImpl;
import com.github.aint.jblog.service.mail.MailService;

/**
 * This exception thrown when an email address is duplicated.
 * 
 * @author Olexandr Tyshkovets
 * @see DuplicateException
 * @see UserServiceImpl#registerUser(User, MailService)
 */
public class DuplicateEmailException extends DuplicateException {
    private static final long serialVersionUID = -6173190488257924380L;

    /**
     * Constructs a new exception with {@code null} as its detail message.
     */
    public DuplicateEmailException() {
    }

    /**
     * Constructs a new exception with the specified {@code email}.
     * 
     * @param email
     *            the email, which is duplicated
     */
    public DuplicateEmailException(String email) {
        super("This email: " + email + " is duplicated");
    }

    /**
     * Constructs a new exception with the specified detail message and cause.
     * 
     * @param message
     *            the detail message
     * @param cause
     *            the cause (which is saved for later retrieval by the {@link #getCause()} method)
     */
    public DuplicateEmailException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a new exception with the specified cause.
     * 
     * @param cause
     *            the cause (which is saved for later retrieval by the {@link #getCause()} method)
     */
    public DuplicateEmailException(Throwable cause) {
        super(cause);
    }

}
