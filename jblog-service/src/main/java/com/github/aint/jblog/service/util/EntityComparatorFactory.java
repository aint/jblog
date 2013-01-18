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
package com.github.aint.jblog.service.util;

import java.util.Comparator;

import com.github.aint.jblog.model.entity.Entity;
import com.github.aint.jblog.service.util.impl.AnonymousMessageComparatorFactory;
import com.github.aint.jblog.service.util.impl.ArticleComparatorFactory;
import com.github.aint.jblog.service.util.impl.CommentComparatorFactory;
import com.github.aint.jblog.service.util.impl.UserComparatorFactory;

/**
 * This interface provides comparators of entities which sort by time, id, name and rating.
 * 
 * @param <T>
 *            the entity class
 * 
 * @author Olexandr Tyshkovets
 * @see Entity
 * @see AnonymousMessageComparatorFactory
 * @see ArticleComparatorFactory
 * @see CommentComparatorFactory
 * @see UserComparatorFactory
 */
public interface EntityComparatorFactory<T extends Entity> {
    /**
     * @return a {@link Comparator} which sorts by date
     */
    Comparator<T> getDateComparator();

    /**
     * @return a {@code Comparator} which sorts by id
     */
    Comparator<T> getIdComparator();

    /**
     * @return a {@code Comparator} which sorts by name
     */
    Comparator<T> getNameComparator();

    /**
     * @return a {@code Comparator} which sorts by rating
     */
    Comparator<T> getRatingComparator();

}
