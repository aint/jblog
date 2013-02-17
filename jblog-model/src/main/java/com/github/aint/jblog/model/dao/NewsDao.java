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

import java.util.Date;
import java.util.List;

import com.github.aint.jblog.model.dao.hibernate.NewsHibernateDao;
import com.github.aint.jblog.model.entity.News;

/**
 * This interface represents persistence methods for {@link News} objects.
 * 
 * @author Olexandr Tyshkovets
 * @see GenericDao
 * @see NewsHibernateDao
 */
public interface NewsDao extends GenericDao<News> {
    /**
     * Returns {@link News} created since {@code creationDate}.
     * 
     * @param createdSince
     *            the creation date of news
     * @return a list of {@link News} created since {@code creationDate}
     */
    List<News> getNewsCreatedSince(Date createdSince);

    /**
     * Returns all pinned news.
     * 
     * @return the list of pinned news
     */
    List<News> getAllPinnedNews();

}
