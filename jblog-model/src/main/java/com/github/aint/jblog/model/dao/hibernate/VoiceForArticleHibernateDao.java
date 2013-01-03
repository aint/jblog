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

import com.github.aint.jblog.model.dao.VoiceForArticleDao;
import com.github.aint.jblog.model.entity.VoiceForArticle;

/**
 * This class is hibernate implementation of the {@code VoiceForArticleDao} interface.
 * 
 * @author Olexandr Tyshkovets
 * @see VoiceForArticleDao
 */
public class VoiceForArticleHibernateDao implements VoiceForArticleDao {
    private SessionFactory sessionFactory;

    /**
     * Constructs a {@code VoiceForArticleHibernateDao} with the given {@code sessionFactory}.
     * 
     * @param sessionFactory
     *            hibernate SessionFactory
     */
    public VoiceForArticleHibernateDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void save(VoiceForArticle entity) {
        getSession().save(entity);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(VoiceForArticle entity) {
        getSession().delete(entity);
    }

    @Override
    public void delete(Long id) {
        getSession()
                .createQuery("delete VoiceForArticle where id = ?")
                .setLong(0, id)
                .executeUpdate();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public VoiceForArticle get(Long id) {
        return (VoiceForArticle) getSession().get(VoiceForArticle.class, id);
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<VoiceForArticle> getAll() {
        return getSession().createQuery("from VoiceForArticle").list();
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<VoiceForArticle> getAllOnPage(int pageNumber, int pageSize, boolean head) {
        return getSession()
                .createQuery("from VoiceForArticle order by id " + (head == true ? "asc" : "desc"))
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
                .createQuery("select count(*) from VoiceForArticle")
                .iterate()
                .next();
    }

    /**
     * @return the current hibernate session
     */
    private Session getSession() {
        return sessionFactory.getCurrentSession();
    }

}
