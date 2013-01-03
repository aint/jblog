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
package com.github.aint.jblog.service.other;

import com.github.aint.jblog.service.exception.other.UserRankConfigurationException;
import com.github.aint.jblog.service.exception.other.UserRankNotFoundException;
import com.github.aint.jblog.service.other.impl.XmlUserRankConfiguration;

/**
 * This interface represents methods for a operations with a user's ranks.
 * 
 * @author Olexandr Tyshkovets
 * @see XmlUserRankConfiguration
 */
public interface UserRankConfiguration {
    /**
     * Returns the user's rank by the user's rating.
     * 
     * @param rating
     *            the user's rating
     * @return the user's rank
     * @throws UserRankConfigurationException
     *             if some configuration error has occurred
     * @throws UserRankNotFoundException
     *             if the user's rank was not found
     */
    String getRank(int rating) throws UserRankConfigurationException, UserRankNotFoundException;

    /**
     * Returns a minimum of the user's rating range by the given {@code rank}.
     * 
     * @param rank
     *            the user's rank
     * @return a minimum of the user's rating range
     * @throws IllegalArgumentException
     *             if {@code rank} is {@code null}
     * @throws UserRankConfigurationException
     *             if some configuration error has occurred
     */
    int getMinRange(String rank) throws UserRankConfigurationException;

    /**
     * Returns a maximum of the user's rating range by the given {@code rank}.
     * 
     * @param rank
     *            the user's rank
     * @return a maximum of the user's rating range
     * @throws IllegalArgumentException
     *             if {@code rank} is {@code null}
     * @throws UserRankConfigurationException
     *             if some configuration error has occurred
     */
    int getMaxRange(String rank) throws UserRankConfigurationException;

}
