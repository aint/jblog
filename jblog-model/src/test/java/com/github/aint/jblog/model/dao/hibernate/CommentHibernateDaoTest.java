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
import com.github.aint.jblog.model.dao.CommentDao;
import com.github.aint.jblog.model.entity.Article;
import com.github.aint.jblog.model.entity.Comment;
import com.github.aint.jblog.model.entity.User;

/**
 * @author Olexandr Tyshkovets
 */
public class CommentHibernateDaoTest {
    private CommentDao commentDao;
    private Session session;

    @BeforeClass
    public void beforeClass() {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        commentDao = new CommentHibernateDao(sessionFactory);
        session = sessionFactory.getCurrentSession();
        session.beginTransaction();
    }

    @AfterMethod
    public void afterMethod() {
        session.createQuery("DELETE Comment").executeUpdate();
        session.createQuery("DELETE Article").executeUpdate();
        session.createQuery("DELETE User").executeUpdate();
    }

    /* ----- common methods ----- */

    @Test
    public void create() {
        final Comment comment = getComment(1).get(0);
        commentDao.save(comment);
        session.evict(comment);

        assertEquals(session.get(Comment.class, comment.getId()), comment);
    }

    @Test
    public void get() {
        final Comment comment = getComment(1).get(0);
        session.save(comment);

        assertEquals(commentDao.get(comment.getId()), comment);
    }

    @Test
    public void getNotFound() {
        assertNull(commentDao.get(-10000L));
    }

    @Test
    public void getAll() {
        final List<Comment> commentList = getComment(5);
        for (Comment comment : commentList) {
            session.save(comment);
        }

        assertEquals(commentDao.getAll(), commentList);
    }

    @Test
    public void getAllOnPage() {
        final List<Comment> commentList = getComment(5);
        for (Comment comment : commentList) {
            session.save(comment);
        }

        assertEquals(commentDao.getAllOnPage(1, 3, true), commentList.subList(0, 3));
    }

    @Test
    public void getCount() {
        final List<Comment> commentList = getComment(5);
        for (Comment comment : commentList) {
            session.save(comment);
        }

        assertEquals(commentDao.getCount(), Long.valueOf(commentList.size()));
    }

    @Test
    public void deleteById() {
        final Comment comment = getComment(1).get(0);
        session.save(comment);
        session.evict(comment);

        commentDao.delete(comment.getId());
        assertNull(session.get(Comment.class, comment.getId()));
    }

    @Test
    public void deleteByObject() {
        final Comment comment = getComment(1).get(0);
        session.save(comment);
        session.evict(comment);

        commentDao.delete(comment);
        assertNull(session.get(Comment.class, comment.getId()));
    }

    private List<Comment> getComment(int count) {
        List<Comment> comments = new ArrayList<Comment>();
        for (int i = 0; i < count; i++) {
            User articleAuthor = EntityFactory.getDefaultUser("articleAuthor" + i, "articleAuthor" + i + "@gmail.com");
            session.save(articleAuthor);

            Article article = EntityFactory.getDefaultArticle(articleAuthor);
            session.save(article);

            User commentAuthor = EntityFactory.getDefaultUser("commentAuthor" + i, "commentAuthor" + i + "@gmail.com");
            session.save(commentAuthor);

            comments.add(EntityFactory.getDefaultComment(article, commentAuthor));
        }

        return comments;
    }

}
