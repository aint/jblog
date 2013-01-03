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
package com.github.aint.jblog.model.dao;

import java.util.List;

import com.github.aint.jblog.model.entity.Entity;

/**
 * This interface represents basic persistence methods for all entities.
 * 
 * @param <T>
 *            the entity class
 * @author Olexandr Tyshkovets
 * @see Entity
 * @see ArticleDao
 * @see CommentDao
 * @see NewsDao
 * @see PublicMessageDao
 * @see UserDao
 */
public interface GenericDao<T extends Entity> {
    /**
     * Saves an entity in a data source.
     * 
     * @param entity
     *            an instance of an entity
     */
    void save(T entity);

    /**
     * Returns an entity by the given primary {@code id}.
     * 
     * @param id
     *            an entity's primary id
     * @return an entity with the given {@code id}
     */
    T get(Long id);

    /**
     * Returns all entities.
     * 
     * @return a list of entities
     */
    List<T> getAll();

    /**
     * Returns entities by the page number.
     * 
     * @param pageNumber
     *            the page number, numbered from 1
     * @param pageSize
     *            the page size
     * @param head
     *            if {@code true} pagination begins with a head; otherwise with a tail
     * @return a list of entities
     */
    List<T> getAllOnPage(int pageNumber, int pageSize, boolean head);

    /**
     * Returns an amount of entities.
     * 
     * @return an amount of entities
     */
    Long getCount();

    /**
     * Deletes an entity by the given primary {@code id}.
     * 
     * @param id
     *            an entity's primary id
     */
    void delete(Long id);

    /**
     * Deletes an entity from a data source.
     * 
     * @param entity
     *            an instance of an entity
     */
    void delete(T entity);

}
