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
package com.github.aint.jblog.service.data;

import com.github.aint.jblog.model.entity.Hub;
import com.github.aint.jblog.model.entity.User;
import com.github.aint.jblog.service.data.impl.HubServiceImpl;
import com.github.aint.jblog.service.exception.data.DuplicateHubNameException;
import com.github.aint.jblog.service.exception.data.HubNotFoundException;

/**
 * This interface represents methods to operate with a {@link Hub}.
 * 
 * @author Olexandr Tyshkovets
 * @see GenericEntityService
 * @see HubServiceImpl
 */
public interface HubService extends GenericEntityService<Hub> {
    /**
     * Adds a new {@link Hub}.
     * 
     * @param hub
     *            a {@link Hub} which will be added
     * @param author
     *            the hub's author
     * @return an added {@link Hub}
     * @throws DuplicateHubNameException
     *             if the hub's name already exists
     * @see User
     */
    Hub add(Hub hub, User author) throws DuplicateHubNameException;

    /**
     * Returns a {@link Hub} by the specified {@code name}.
     * 
     * @param name
     *            the name of the requested hub
     * @return the hub with the specified name
     * @throws HubNotFoundException
     *             if the hub with the given {@code name} doesn't exist
     */
    Hub getByHubName(String name) throws HubNotFoundException;

}
