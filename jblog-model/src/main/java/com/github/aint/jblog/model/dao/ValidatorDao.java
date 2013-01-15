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

import com.github.aint.jblog.model.entity.Entity;

/**
 * This interface represents the method to check the existence of objects in a data source.
 * 
 * @author Olexandr Tyshkovets
 */
public interface ValidatorDao {
    /**
     * Checks whether exists the {@code entity} with the given {@code value} of the {@code field}.
     * 
     * @param entity
     *            the entity which will be checked
     * @param field
     *            the field which will be checked
     * @param value
     *            the value which will be checked
     * @return {@code true} if the {@code entity} with the given {@code value} of the {@code field} exists;
     *         {@code false} otherwise
     */
    <T> boolean isExist(Class<? extends Entity> entity, String field, T value);

}
