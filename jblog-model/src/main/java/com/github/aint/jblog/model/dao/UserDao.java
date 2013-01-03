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
package com.github.aint.jblog.model.dao;

import java.util.List;

import com.github.aint.jblog.model.dao.hibernate.UserHibernateDao;
import com.github.aint.jblog.model.entity.User;

/**
 * This interface represents persistence methods for {@link User} objects.
 * 
 * @author Olexandr Tyshkovets
 * @see GenericDao
 * @see UserHibernateDao
 */
public interface UserDao extends GenericDao<User> {
    /**
     * Returns a {@link User} by the given {@code userName}.
     * 
     * @param userName
     *            userName of the requested user
     * @return a {@code User} or {@code null} if a {@code User} with the given {@code userName} not found
     */
    User getByUserName(String userName);

    /**
     * Returns a {@link User} by the given {@code uuid}.
     * 
     * @param uuid
     *            {@link User}'s universally unique identifier
     * @return a {@code User} or {@code null} if a {@code User} with the given {@code uuid} not found
     * @see java.util.UUID
     */
    User getByUuid(String uuid);

    /**
     * Returns a {@link User} by the given {@code email}.
     * 
     * @param email
     *            email address of the requested user
     * @return a {@code User} or {@code null} if a {@code User} with the given {@code email} not found
     */
    User getByEmail(String email);

    /**
     * Returns all non activated users.
     * 
     * @return a list of non activated users
     * @see User
     */
    List<User> getNonActivatedUsers();

}
