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

import com.github.aint.jblog.model.entity.Article;
import com.github.aint.jblog.service.data.impl.ArticleServiceImpl;

/**
 * This exception thrown when a {@link Article} can't be found.
 * 
 * @author Olexandr Tyshkovets
 * @see EntityNotFoundException
 * @see ArticleServiceImpl
 */
public class ArticleNotFoundException extends EntityNotFoundException {
    private static final long serialVersionUID = -5622215107298012190L;

    /**
     * Constructs a new exception with {@code null} as its detail message.
     */
    public ArticleNotFoundException() {
    }

    /**
     * Constructs a new exception with the specified {@link Article}.
     * 
     * @param article
     *            a {@link Article}, which was not found
     */
    public ArticleNotFoundException(Article article) {
        super("Article not found: " + article);
    }

    /**
     * Constructs a new exception with the specified detail message.
     * 
     * @param message
     *            the detail message
     */
    public ArticleNotFoundException(String message) {
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
    public ArticleNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a new exception with the specified cause.
     * 
     * @param cause
     *            the cause (which is saved for later retrieval by the {@link #getCause()} method)
     */
    public ArticleNotFoundException(Throwable cause) {
        super(cause);
    }

}
