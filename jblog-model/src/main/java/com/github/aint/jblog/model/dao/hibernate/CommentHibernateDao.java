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

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.github.aint.jblog.model.dao.CommentDao;
import com.github.aint.jblog.model.entity.Comment;

/**
 * This class is hibernate implementation of the {@code CommentDao} interface.
 * 
 * @author Olexandr Tyshkovets
 * @see CommentDao
 */
public class CommentHibernateDao implements CommentDao {
    private final SessionFactory sessionFactory;

    /**
     * Constructs a {@code CommentHibernateDao} with the given {@code sessionFactory}.
     * 
     * @param sessionFactory
     *            hibernate SessionFactory
     */
    public CommentHibernateDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void save(Comment entity) {
        getSession().save(entity);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Comment get(Long id) {
        return (Comment) getSession().get(Comment.class, id);
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<Comment> getAll() {
        return getSession()
                .getNamedQuery("Comment.getAll")
                .list();
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<Comment> getAllOnPage(int pageNumber, int pageSize, boolean head) {
        return getSession()
                .getNamedQuery(head == true ? "Comment.getAllOnPageAsc" : "Comment.getAllOnPageDesc")
                .setFirstResult((pageNumber - 1) * pageSize)
                .setMaxResults(pageSize)
                .list();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Long getCount() {
        return (Long) getSession()
                .getNamedQuery("Comment.getCount")
                .iterate()
                .next();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(Long id) {
        getSession()
                .getNamedQuery("Comment.deleteById")
                .setLong(0, id)
                .executeUpdate();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(Comment entity) {
        getSession().delete(entity);
    }

    /**
     * @return the current hibernate session
     */
    private Session getSession() {
        return sessionFactory.getCurrentSession();
    }

}
