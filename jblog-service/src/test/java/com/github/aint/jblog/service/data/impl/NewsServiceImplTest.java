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

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.mockito.Mock;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.github.aint.jblog.model.dao.NewsDao;
import com.github.aint.jblog.model.entity.News;
import com.github.aint.jblog.model.entity.Role;
import com.github.aint.jblog.model.entity.User;
import com.github.aint.jblog.service.data.NewsService;
import com.github.aint.jblog.service.exception.data.EntityNotFoundException;
import com.github.aint.jblog.service.exception.security.AccessException;

/**
 * @author Olexandr Tyshkovets
 */
public class NewsServiceImplTest {
    private static final Long NEWS_ID = 1L;
    private static final User NEWS_AUTHOR = new User("userName", "user@gmail.com", "password");
    private NewsService newsService;
    @Mock
    private NewsDao newsDao;

    @BeforeMethod
    public void beforeMethod() {
        initMocks(this);
        newsService = new NewsServiceImpl(newsDao);
    }

    /* ----- own methods ----- */

    @Test
    public void addNews() throws AccessException {
        final News news = getNews();
        NEWS_AUTHOR.setRole(Role.MODERATOR);
        newsService.addNews(news, NEWS_AUTHOR);

        assertNotNull(news.getCreationDate(), "news' creationDate is null");
        assertNotNull(news.getAuthor(), "news' author is null");

        verify(newsDao).save(news);
    }

    @Test(expectedExceptions = AccessException.class)
    public void addNewsWithAccessViolation() throws AccessException {
        final News news = getNews();
        NEWS_AUTHOR.setRole(Role.USER);
        newsService.addNews(news, NEWS_AUTHOR);
    }

    @Test
    public void getNewsCreatedSince() {
        final List<News> expected = new ArrayList<News>(Arrays.asList(getNews(), getNews(), getNews()));
        final Date today = new Date();
        when(newsDao.getNewsCreatedSince(today)).thenReturn(expected);

        assertEquals(newsService.getNewsCreatedSince(today), expected);

        verify(newsDao).getNewsCreatedSince(today);
    }

    @Test
    public void getAllPinnedNews() {
        final List<News> expected = new ArrayList<News>(Arrays.asList(getNews(), getNews(), getNews()));
        when(newsDao.getAllPinnedNews()).thenReturn(expected);

        assertEquals(newsService.getAllPinnedNews(), expected);
        verify(newsDao).getAllPinnedNews();
    }

    /* ----- common methods ----- */

    @Test
    public void get() throws EntityNotFoundException {
        final News expected = getNews();
        when(newsDao.get(NEWS_ID)).thenReturn(expected);

        assertEquals(newsService.get(NEWS_ID), expected);

        verify(newsDao).get(NEWS_ID);
    }

    @Test(expectedExceptions = EntityNotFoundException.class)
    public void getNotFound() throws EntityNotFoundException {
        when(newsDao.get(NEWS_ID)).thenReturn(null);

        newsService.get(NEWS_ID);
    }

    @Test
    public void getAll() {
        final List<News> expected = new ArrayList<News>(Arrays.asList(getNews(), getNews(), getNews()));
        when(newsDao.getAll()).thenReturn(expected);

        assertEquals(newsService.getAll(), expected);

        verify(newsDao).getAll();
    }

    @Test
    public void getAllOnPage() {
        final List<News> expected = new ArrayList<News>(Arrays.asList(getNews(), getNews()));
        when(newsDao.getAllOnPage(1, 5, true)).thenReturn(expected);

        assertEquals(newsService.getAllOnPage(1, 5, true), expected);

        verify(newsDao).getAllOnPage(1, 5, true);
    }

    @Test
    public void getCount() {
        final long expected = 13L;
        when(newsDao.getCount()).thenReturn(expected);

        assertEquals(newsService.getCount(), expected);

        verify(newsDao).getCount();
    }

    @Test
    public void isExist() {
        when(newsDao.get(NEWS_ID)).thenReturn(null);

        assertFalse(newsService.isExist(NEWS_ID));
    }

    private News getNews() {
        News news = new News("title", "body", false, NEWS_AUTHOR);
        news.setId(NEWS_ID);
        news.setCreationDate(new Date());
        return news;
    }

}
