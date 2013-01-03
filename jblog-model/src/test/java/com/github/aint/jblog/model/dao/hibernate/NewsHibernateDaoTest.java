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
package com.github.aint.jblog.model.dao.hibernate;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.github.aint.jblog.model.EntityFactory;
import com.github.aint.jblog.model.HibernateUtil;
import com.github.aint.jblog.model.dao.NewsDao;
import com.github.aint.jblog.model.entity.News;
import com.github.aint.jblog.model.entity.User;

/**
 * @author Olexandr Tyshkovets
 */
public class NewsHibernateDaoTest {
    private NewsDao newsDao;
    private Session session;

    @BeforeClass
    public void beforeClass() {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        newsDao = new NewsHibernateDao(sessionFactory);
        session = sessionFactory.getCurrentSession();
        session.beginTransaction();
    }

    @AfterMethod
    public void afterMethod() {
        session.createQuery("DELETE News").executeUpdate();
        session.createQuery("DELETE User").executeUpdate();
    }

    /* ----- own methods ----- */

    @Test
    public void getNewsCreatedSince() {
        final long today = System.currentTimeMillis();
        final long oneDay = 1000 * 60 * 60 * 24;
        final List<News> newsList = getNews(10);
        for (int i = 0; i < newsList.size(); i++) {
            News news = newsList.get(i);
            news.setCreationDate((i < 5) ? new Date(today - oneDay * 31) :
                    new Date(today - (long) (Math.random() * 10 * oneDay)));
            session.save(news);
        }

        final List<News> resultList = new ArrayList<News>();
        resultList.addAll(newsList.subList(5, 10));
        Collections.sort(resultList, new Comparator<News>() {
            @Override
            public int compare(News n1, News n2) {
                return n2.getCreationDate().compareTo(n1.getCreationDate());
            }
        });
        assertEquals(newsDao.getNewsCreatedSince(new Date(today - oneDay * 11)), resultList);
    }

    /* ----- common methods ----- */

    @Test
    public void create() {
        final News news = getNews(1).get(0);
        newsDao.save(news);
        session.evict(news);

        assertEquals(session.get(News.class, news.getId()), news);
    }

    @Test
    public void get() {
        final News news = getNews(1).get(0);
        session.save(news);

        assertEquals(newsDao.get(news.getId()), news);
    }

    @Test
    public void getNotFound() {
        assertNull(newsDao.get(-10000L));
    }

    @Test
    public void getAll() {
        final List<News> newsList = getNews(5);
        for (News news : newsList) {
            session.save(news);
        }

        assertEquals(newsDao.getAll(), newsList);
    }

    @Test
    public void getAllOnPage() {
        final List<News> newsList = getNews(5);
        for (News news : newsList) {
            session.save(news);
        }

        assertEquals(newsDao.getAllOnPage(1, 3, true), newsList.subList(0, 3));
    }

    @Test
    public void getCount() {
        final List<News> newsList = getNews(5);
        for (News news : newsList) {
            session.save(news);
        }

        assertEquals(newsDao.getCount(), Long.valueOf(newsList.size()));
    }

    @Test(enabled = false)
    public void deleteById() {
        final News news = getNews(1).get(0);
        session.save(news);
        session.evict(news);

        newsDao.delete(news.getId());
        assertNull(session.get(News.class, news.getId()));
    }

    @Test
    public void deleteByObject() {
        final News news = getNews(1).get(0);
        session.save(news);
        session.evict(news);

        newsDao.delete(news);
        assertNull(session.get(News.class, news.getId()));
    }

    public List<News> getNews(int count) {
        List<News> news = new ArrayList<News>();
        for (int i = 0; i < count; i++) {
            User newsAuthor = EntityFactory.getDefaultUser("newsAuthor" + i, "newsAuthor" + i + "@gmail.com");
            session.save(newsAuthor);

            news.add(EntityFactory.getDefaultNews(newsAuthor));
        }

        return news;
    }

}
