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

import com.github.aint.jblog.model.entity.AnonymousMessage;
import com.github.aint.jblog.service.util.EntityComparatorFactory;

/**
 * This class provides {@link AnonymousMessage}'s comparators which sort by time and id.
 * 
 * @author Olexandr Tyshkovets
 * @see EntityComparatorFactory
 */
public class AnonymousMessageComparatorFactory implements EntityComparatorFactory<AnonymousMessage> {
    /**
     * {@inheritDoc}
     */
    @Override
    public Comparator<AnonymousMessage> getDateComparator() {
        Comparator<AnonymousMessage> comparator = new Comparator<AnonymousMessage>() {
            @Override
            public int compare(AnonymousMessage m1, AnonymousMessage m2) {
                return m1.getCreationDate().compareTo(m2.getCreationDate());
            }
        };
        return comparator;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Comparator<AnonymousMessage> getIdComparator() {
        Comparator<AnonymousMessage> comparator = new Comparator<AnonymousMessage>() {
            @Override
            public int compare(AnonymousMessage m1, AnonymousMessage m2) {
                return m1.getId().compareTo(m2.getId());
            }
        };
        return comparator;
    }

    /**
     * This method not implemented because an {@code AnonymousMessage} doesn't have the name.
     * 
     * @throws UnsupportedOperationException
     *             always
     */
    @Override
    public Comparator<AnonymousMessage> getNameComparator() {
        throw new UnsupportedOperationException("Anonymous message hasn't the name");
    }

    /**
     * This method not implemented because an {@code AnonymousMessage} doesn't have the rating.
     * 
     * @throws UnsupportedOperationException
     *             always
     */
    @Override
    public Comparator<AnonymousMessage> getRatingComparator() {
        throw new UnsupportedOperationException("Anonymous message hasn't the rating");
    }
}
