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

import com.github.aint.jblog.model.entity.Comment;
import com.github.aint.jblog.service.data.impl.CommentServiceImpl;

/**
 * This exception thrown when a {@link Comment} can't be found.
 * 
 * @author Olexandr Tyshkovets
 * @see EntityNotFoundException
 * @see CommentServiceImpl
 */
public class CommentNotFoundException extends EntityNotFoundException {
    private static final long serialVersionUID = -3582115517757604184L;

    /**
     * Constructs a new exception with {@code null} as its detail message.
     */
    public CommentNotFoundException() {
    }

    /**
     * Constructs a new exception with the specified {@link Comment}.
     * 
     * @param comment
     *            a {@link Comment}, which was not found
     */
    public CommentNotFoundException(Comment comment) {
        super("The comment was not found: " + comment);
    }

    /**
     * Constructs a new exception with the specified detail message.
     * 
     * @param message
     *            the detail message
     */
    public CommentNotFoundException(String message) {
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
    public CommentNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a new exception with the specified cause.
     * 
     * @param cause
     *            the cause (which is saved for later retrieval by the {@link #getCause()} method)
     */
    public CommentNotFoundException(Throwable cause) {
        super(cause);
    }

}
