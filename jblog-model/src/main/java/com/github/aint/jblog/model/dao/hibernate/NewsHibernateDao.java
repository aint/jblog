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

import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.github.aint.jblog.model.dao.NewsDao;
import com.github.aint.jblog.model.entity.News;

/**
 * This class is hibernate implementation of the {@code NewsDao} interface.
 * 
 * @author Olexandr Tyshkovets
 * @see NewsDao
 */
public class NewsHibernateDao implements NewsDao {
    private final SessionFactory sessionFactory;

    /**
     * Constructs a {@code NewsHibernateDao} with the given {@code sessionFactory}.
     * 
     * @param sessionFactory
     *            hibernate SessionFactory
     */
    public NewsHibernateDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<News> getNewsCreatedSince(Date createdSince) {
        return getSession()
                .getNamedQuery("News.getCreatedSince")
                .setDate(0, createdSince)
                .list();
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<News> getAllPinnedNews() {
        return getSession()
                .getNamedQuery("News.getAllPinned")
                .list();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void save(News entity) {
        getSession().save(entity);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public News get(Long id) {
        return (News) getSession().get(News.class, id);
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<News> getAll() {
        return getSession()
                .getNamedQuery("News.getAll")
                .list();
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<News> getAllOnPage(int pageNumber, int pageSize, boolean head) {
        return getSession()
                .getNamedQuery(head == true ? "News.getAllOnPageAsc" : "News.getAllOnPageDesc")
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
                .getNamedQuery("News.getCount")
                .iterate()
                .next();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(Long id) {
        getSession()
                .getNamedQuery("News.deleteById")
                .setLong(0, id)
                .executeUpdate();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(News entity) {
        getSession().delete(entity);
    }

    /**
     * @return the current hibernate session
     */
    private Session getSession() {
        return sessionFactory.getCurrentSession();
    }

}
