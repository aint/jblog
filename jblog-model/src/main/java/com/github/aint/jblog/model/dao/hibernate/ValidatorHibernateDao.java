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

import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

import com.github.aint.jblog.model.dao.ValidatorDao;
import com.github.aint.jblog.model.entity.Entity;

/**
 * This class is hibernate implementation of the {@code ValidatorDao} interface.
 * 
 * @author Olexandr Tyshkovets
 * @see ValidatorDao
 */
public class ValidatorHibernateDao implements ValidatorDao {
    private SessionFactory sessionFactory;

    /**
     * Constructs a {@code ValidatorHibernateDao} with the given {@code sessionFactory}.
     * 
     * @param sessionFactory
     *            hibernate SessionFactory
     */
    public ValidatorHibernateDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T> boolean isExist(Class<? extends Entity> entity, String field, T value) {
        return !sessionFactory
                .getCurrentSession()
                .createCriteria(entity)
                .add(Restrictions.eq(field, value))
                .list()
                .isEmpty();
    }

}
