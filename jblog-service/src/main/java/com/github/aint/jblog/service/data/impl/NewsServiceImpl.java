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
package com.github.aint.jblog.service.data.impl;

import java.util.Date;
import java.util.List;

import com.github.aint.jblog.model.dao.NewsDao;
import com.github.aint.jblog.model.entity.News;
import com.github.aint.jblog.model.entity.Role;
import com.github.aint.jblog.model.entity.User;
import com.github.aint.jblog.service.data.NewsService;
import com.github.aint.jblog.service.exception.data.NewsNotFoundException;
import com.github.aint.jblog.service.exception.security.AccessException;

/**
 * This class implementing the {@code NewsService} interface.
 * 
 * @author Olexandr Tyshkovets
 * @see NewsService
 */
public class NewsServiceImpl implements NewsService {
    private final NewsDao newsDao;

    /**
     * Constructs a {@code NewsServiceImpl} with the given {@code newsDao}.
     * 
     * @param newsDao
     *            the object, which necessary for operations with a data source
     * @see NewsDao
     */
    public NewsServiceImpl(NewsDao newsDao) {
        this.newsDao = newsDao;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public News addNews(News news, User author) throws AccessException {
        if (author.getRole() != Role.MODERATOR && author.getRole() != Role.ADMIN) {
            throw new AccessException("the caller must have MODERATOR or ADMIN role");
        }
        news.setAuthor(author);
        news.setCreationDate(new Date());
        newsDao.save(news);
        return news;
    }

    @Override
    public List<News> getNewsCreatedSince(Date createdSince) {
        return newsDao.getNewsCreatedSince(createdSince);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public News get(Long id) throws NewsNotFoundException {
        News news = newsDao.get(id);
        if (news == null) {
            throw new NewsNotFoundException("The news with the given id: " + id + " was not found");
        }
        return news;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<News> getAll() {
        return newsDao.getAll();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<News> getAllOnPage(int pageNumber, int pageSize, boolean head) {
        if (pageNumber <= 0) {
            throw new IllegalArgumentException("pageNumber cant'be <= 0");
        }

        return newsDao.getAllOnPage(pageNumber, pageSize, head);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public long getCount() {
        return newsDao.getCount();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isExist(Long id) {
        return newsDao.get(id) != null;
    }

}
