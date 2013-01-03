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
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.github.aint.jblog.model.EntityFactory;
import com.github.aint.jblog.model.HibernateUtil;
import com.github.aint.jblog.model.dao.ArticleDao;
import com.github.aint.jblog.model.entity.Article;
import com.github.aint.jblog.model.entity.User;

/**
 * @author Olexandr Tyshkovets
 */
public class ArticleHibernateDaoTest {
    private ArticleDao articleDao;
    private Session session;

    @BeforeClass
    public void beforeClass() {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        articleDao = new ArticleHibernateDao(sessionFactory);
        session = sessionFactory.getCurrentSession();
        session.beginTransaction();
    }

    @AfterMethod
    public void afterMethod() {
        session.createQuery("DELETE Article").executeUpdate();
        session.createQuery("DELETE User").executeUpdate();
    }

    /* ----- own methods ----- */

    @Test
    public void getMostPopularArticles() {
        final List<Article> articleList = getArticle(10);
        for (int i = 0; i < articleList.size(); i++) {
            Article article = articleList.get(i);
            article.setRating((i < 5) ? ((int) (Math.random() * 10) + 100) : ((int) (Math.random() * 10)));
            session.save(article);
        }

        assertEquals(articleDao.getMostPopularArticles(10, 3), articleList.subList(0, 3));
    }

    /* ----- common methods ----- */

    @Test
    public void create() {
        final Article article = getArticle(1).get(0);
        articleDao.save(article);
        session.evict(article);

        assertEquals(session.get(Article.class, article.getId()), article);
    }

    @Test
    public void get() {
        final Article article = getArticle(1).get(0);
        session.save(article);

        assertEquals(articleDao.get(article.getId()), article);
    }

    @Test
    public void getNotFound() {
        assertNull(articleDao.get(-10000L));
    }

    @Test
    public void getAll() {
        final List<Article> articleList = getArticle(5);
        for (Article article : articleList) {
            session.save(article);
        }

        assertEquals(articleDao.getAll(), articleList);
    }

    @Test
    public void getAllOnPage() {
        final List<Article> articleList = getArticle(5);
        for (Article article : articleList) {
            session.save(article);
        }

        assertEquals(articleDao.getAllOnPage(1, 3, true), articleList.subList(0, 3));
    }

    @Test
    public void getCount() {
        final List<Article> articleList = getArticle(5);
        for (Article article : articleList) {
            session.save(article);
        }

        assertEquals(articleDao.getCount(), Long.valueOf(articleList.size()));
    }

    @Test
    public void deleteById() {
        final Article article = getArticle(1).get(0);
        session.save(article);
        session.evict(article);

        articleDao.delete(article.getId());
        assertNull(session.get(Article.class, article.getId()));
    }

    @Test
    public void deleteByObject() {
        final Article article = getArticle(1).get(0);
        session.save(article);
        session.evict(article);

        articleDao.delete(article);
        assertNull(session.get(Article.class, article.getId()));
    }

    public List<Article> getArticle(int count) {
        List<Article> articles = new ArrayList<Article>();
        for (int i = 0; i < count; i++) {
            User articleAuthor = EntityFactory.getDefaultUser("articleAuthor" + i, "articleAuthor" + i + "@gmail.com");
            session.save(articleAuthor);

            articles.add(EntityFactory.getDefaultArticle(articleAuthor));
        }

        return articles;
    }

}
