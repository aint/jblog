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
package com.github.aint.jblog.service.data;

import java.util.List;

import com.github.aint.jblog.model.entity.Entity;
import com.github.aint.jblog.service.exception.data.EntityNotFoundException;

/**
 * This interface represents base methods for all entities.
 * 
 * @author Olexandr Tyshkovets
 * @param <T>
 *            the entity class
 * @see Entity
 * @see ArticleService
 * @see CommentService
 * @see PublicMessageService
 * @see UserService
 */
public interface GenericEntityService<T extends Entity> {
    /**
     * Returns an entity by the given primary {@code id}.
     * 
     * @param id
     *            an entity's primary id
     * @return an entity with the given {@code id}
     * @throws EntityNotFoundException
     *             if an entity not found
     */
    T get(Long id) throws EntityNotFoundException;

    /**
     * Returns all entities.
     * 
     * @return a list of entities
     */
    List<T> getAll();

    /**
     * Returns all entities by the page number.
     * 
     * @param pageNumber
     *            the page number, numbered from 1
     * @param pageSize
     *            the page size
     * @param head
     *            if {@code true} pagination begins with a head; otherwise with a tail
     * @return a list of entities
     * @throws IllegalArgumentException
     *             if {@code pageNumber} <= 0
     */
    List<T> getAllOnPage(int pageNumber, int pageSize, boolean head);

    /**
     * Returns an amount of entities.
     * 
     * @return an amount of entities
     */
    long getCount();

    /**
     * Checks entity existence by the given primary {@code id}.
     * 
     * @param id
     *            an entity's primary id
     * @return {@code true} if an entity with the given {@code id} exists; {@code false} otherwise
     */
    boolean isExist(Long id);

}
