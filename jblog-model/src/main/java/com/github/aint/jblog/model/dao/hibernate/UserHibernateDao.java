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

import com.github.aint.jblog.model.dao.UserDao;
import com.github.aint.jblog.model.entity.User;

/**
 * This class is hibernate implementation of the {@code UserDao} interface.
 * 
 * @author Olexandr Tyshkovets
 * @see UserDao
 */
public class UserHibernateDao implements UserDao {
    private SessionFactory sessionFactory;

    /**
     * Constructs a {@code UserHibernateDao} with the given {@code sessionFactory}.
     * 
     * @param sessionFactory
     *            hibernate SessionFactory
     */
    public UserHibernateDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public User getByUserName(String userName) {
        return (User) getSession()
                .getNamedQuery("User.getByUserName")
                .setString(0, userName)
                .uniqueResult();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public User getByUuid(String uuid) {
        return (User) getSession()
                .getNamedQuery("User.getByUuid")
                .setString(0, uuid)
                .uniqueResult();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public User getByEmail(String email) {
        return (User) getSession()
                .getNamedQuery("User.getByEmail")
                .setString(0, email)
                .uniqueResult();
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<User> getNonActivatedUsers() {
        return getSession()
                .getNamedQuery("User.getNonActivatedUsers")
                .list();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void save(User entity) {
        getSession().save(entity);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public User get(Long id) {
        return (User) getSession().get(User.class, id);
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<User> getAll() {
        return getSession()
                .getNamedQuery("User.getAll")
                .list();
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<User> getAllOnPage(int pageNumber, int pageSize, boolean head) {
        return getSession()
                .getNamedQuery(head == true ? "User.getAllOnPageOrderByAsc" : "User.getAllOnPageOrderByDesc")
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
                .getNamedQuery("User.getCount")
                .iterate()
                .next();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(Long id) {
        getSession()
                .getNamedQuery("User.deleteById")
                .setLong(0, id)
                .executeUpdate();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(User entity) {
        getSession().delete(entity);
    }

    /**
     * @return the current hibernate session
     */
    private Session getSession() {
        return sessionFactory.getCurrentSession();
    }

}
