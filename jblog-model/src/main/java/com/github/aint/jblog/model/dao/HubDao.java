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

import com.github.aint.jblog.model.dao.hibernate.HubHibernateDao;
import com.github.aint.jblog.model.entity.Hub;

/**
 * This interface represents persistence methods for {@link Hub} objects.
 * 
 * @author Olexandr Tyshkovets
 * @see GenericDao
 * @see HubHibernateDao
 */
public interface HubDao extends GenericDao<Hub> {
    /**
     * Returns a {@link Hub} by the given {@code hubName}.
     * 
     * @param hubName
     *            the name of the requested hub
     * @return a {@code Hub} or {@code null} if a hub with the given {@code hubName} not found
     */
    Hub getByHubName(String hubName);

    /**
     * Returns all public hubs.
     * 
     * @return all public hubs
     */
    List<Hub> getAllPublicHubs();

    /**
     * Returns all hubs of a {@link User} by its {@code username}.
     * 
     * @param username
     *            username to find a hubs for
     * @return all hubs of a {@link User}
     */
    List<Hub> getHubsOfUser(String username);

}
