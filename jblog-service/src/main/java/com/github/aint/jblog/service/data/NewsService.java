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

import java.util.Date;
import java.util.List;

import com.github.aint.jblog.model.entity.News;
import com.github.aint.jblog.model.entity.User;
import com.github.aint.jblog.service.data.impl.NewsServiceImpl;
import com.github.aint.jblog.service.exception.security.AccessException;

/**
 * This interface represents methods to operate with a {@link News}.
 * 
 * @author Olexandr Tyshkovets
 * @see GenericEntityService
 * @see NewsServiceImpl
 */
public interface NewsService extends GenericEntityService<News> {
    /**
     * Adds a new {@link News}.
     * 
     * @param news
     *            a {@link News} which will be added
     * @param author
     *            a {@link User} who is an author of this news. The {@code author} should have {@code MODERATOR} or
     *            {@code ADMIN} role
     * @return an added news
     * @throws AccessException
     *             if the {@code author} doesn't have permission to perform this action. The {@code author} should have
     *             {@code MODERATOR} or {@code ADMIN} role
     * @see Role
     */
    News addNews(News news, User author) throws AccessException;

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
