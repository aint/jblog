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
package com.github.aint.jblog.service.util.impl;

import java.util.Comparator;

import com.github.aint.jblog.model.entity.User;
import com.github.aint.jblog.service.util.EntityComparatorFactory;

/**
 * This class provides a {@link User}'s comparators which sort by time, id, name and rating.
 * 
 * @author Olexandr Tyshkovets
 * @see EntityComparatorFactory
 */
public class UserComparatorFactory implements EntityComparatorFactory<User> {
    /**
     * {@inheritDoc}
     */
    @Override
    public Comparator<User> getDateComparator() {
        Comparator<User> comparator = new Comparator<User>() {
            @Override
            public int compare(User u1, User u2) {
                return u1.getRegistrationDate().compareTo(u2.getRegistrationDate());
            }
        };
        return comparator;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Comparator<User> getIdComparator() {
        Comparator<User> comparator = new Comparator<User>() {
            @Override
            public int compare(User u1, User u2) {
                return u1.getId().compareTo(u2.getId());
            }
        };
        return comparator;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Comparator<User> getNameComparator() {
        Comparator<User> comparator = new Comparator<User>() {
            @Override
            public int compare(User u1, User u2) {
                return u1.getUserName().compareTo(u2.getUserName());
            }
        };
        return comparator;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Comparator<User> getRatingComparator() {
        Comparator<User> comparator = new Comparator<User>() {
            @Override
            public int compare(User u1, User u2) {
                return u1.getRating() - u2.getRating();
            }
        };
        return comparator;
    }

}
